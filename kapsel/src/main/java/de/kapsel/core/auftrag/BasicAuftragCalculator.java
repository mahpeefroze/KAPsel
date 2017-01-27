package de.kapsel.core.auftrag;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import de.kapsel.core.auftrag.entities.Auftrag;
import de.kapsel.core.auftrag.entities.ProduktWrapper;
import de.kapsel.core.util.ETypes;
import de.kapsel.core.util.IKapselCalculator;
import de.kapsel.core.util.beans.UtilsBean;

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
				time+=pw.getProdukt().getZeit()*pw.getStueckzahl();
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
		double price=calculateNettoPrice(a);
		price*= (1+getUtilsContainer().getUtilsMap().get("USt").getValue()/100);
		return price;
	}

	@Override
	public double calculateAfterDiscount(Auftrag a) {
		double rabatt = a.getKunde().getRabatt();
		double price = calculateBruttoPrice(a);
		if(a.getKunde().getGruppe()!=null && a.getKunde().getGruppe().getRabatt()>rabatt){
			rabatt=a.getKunde().getGruppe().getRabatt();
		}
		if(getUtilsContainer().getUtilsMap().get("RabAlleS").getValue()==1 &&
				getUtilsContainer().getUtilsMap().get("RabAlle").getValue()>rabatt){
			rabatt=getUtilsContainer().getUtilsMap().get("RabAlle").getValue();
		}
		if(a.getKunde().getTyp()==ETypes.KundeT.Öffentlich &&
				getUtilsContainer().getUtilsMap().get("RabOeffS").getValue()==1 &&
				getUtilsContainer().getUtilsMap().get("RabOeff").getValue()>rabatt){
			rabatt=getUtilsContainer().getUtilsMap().get("RabOeff").getValue();
		}else if(a.getKunde().getTyp()==ETypes.KundeT.Firma &&
				getUtilsContainer().getUtilsMap().get("RabFiS").getValue()==1 &&
				getUtilsContainer().getUtilsMap().get("RabFi").getValue()>rabatt){
			rabatt=getUtilsContainer().getUtilsMap().get("RabFi").getValue();
		}else if(a.getKunde().getTyp()==ETypes.KundeT.Privat &&
				getUtilsContainer().getUtilsMap().get("RabPrivS").getValue()==1 &&
				getUtilsContainer().getUtilsMap().get("RabPriv").getValue()>rabatt){
			rabatt=getUtilsContainer().getUtilsMap().get("RabPriv").getValue();
		}
		price=price * ((100-rabatt)/100);
		return price;
	}
	
	

}
