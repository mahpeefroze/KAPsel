package de.kapsel.auftrag;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import de.kapsel.auftrag.entities.Auftrag;
import de.kapsel.auftrag.entities.ProduktWrapper;
import de.kapsel.global.IKapselCalculator;
import de.kapsel.global.beans.UtilsBean;

@ManagedBean
@ViewScoped
public class BasicAuftragCalculator implements Serializable, IKapselCalculator<Auftrag>{
	
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{utilsBean}")
	private UtilsBean utilsContainer;
	
	public UtilsBean getUtilsContainer() {
		return utilsContainer;
	}

	public void setUtilsContainer(UtilsBean utilsContainer) {
		this.utilsContainer = utilsContainer;
	}

	public int calculateTime(Auftrag a) {
		int time=0;
		if(a.getProdukte()!=null && !a.getProdukte().isEmpty()){
			for(ProduktWrapper pw:a.getProdukte()){
				time+=pw.getProdukt().getZeit();
			}
		}
		return time;
	}

	public double calculateNettoPrice(Auftrag a) {
		double price=0;
		if(a.getProdukte()!=null && !a.getProdukte().isEmpty()){
			for(ProduktWrapper pw:a.getProdukte()){
				price+=pw.getProdukt().getPreis()*pw.getStueckzahl();
			}
		}
		return price;
	}

	public double calculateBruttoPrice(Auftrag a) {
		double price=a.getPreis();
		price*= (1+getUtilsContainer().getUtilsMap().get("USt").getValue());
		return price;
	}
	
	

}
