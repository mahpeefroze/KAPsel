package de.kapsel.produkt;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import de.kapsel.global.IKapselCalculator;
import de.kapsel.global.beans.UtilsBean;
import de.kapsel.produkt.entities.Arbeitsschritt;
import de.kapsel.produkt.entities.Bauteil;
import de.kapsel.produkt.entities.Produkt;

@ManagedBean
@ViewScoped
public class BasicProduktCalculator implements Serializable, IKapselCalculator<Produkt>{
	
	
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{utilsBean}")
	private UtilsBean utilsContainer;
	
	public UtilsBean getUtilsContainer() {
		return utilsContainer;
	}

	public void setUtilsContainer(UtilsBean utilsContainer) {
		this.utilsContainer = utilsContainer;
	}
	
	public BasicProduktCalculator(){
		
	}

	public int calculateTime(Produkt p){
		int time=0;
		if(p.getAschritte()!=null && !p.getAschritte().isEmpty()){
			for(Arbeitsschritt a:p.getAschritte()){
				time+=a.getZeit();
			}
		}
		return time;
	}
	
	public double calculateNettoPrice(Produkt p){
		double price=0;
		
		double mk=0;
		double lohne=0;
		double fal=getUtilsContainer().getUtilsMap().get("FAL").getValue();
		double fgk=getUtilsContainer().getUtilsMap().get("FGK").getValue();
		if(p.getAschritte()!=null && !p.getAschritte().isEmpty()){
			//Maschinenkosten(Maschinenstundensatz)/Aschritt + Facharbeitlohn/Aschritt 
			for(Arbeitsschritt a:p.getAschritte()){
				lohne+=a.getZeit()*fal/60;
				mk+=(a.getZeit()*(a.getWerkzeug()!=null ? a.getWerkzeug().getStundensatz() : 0)/60);
			}
			//Sum all and calculate Fertigungsgemeinkosten based on Fertigungslohn 
			price=price + mk + lohne*(1+fgk/100);
		}
		double mek=0;
		double mgk=getUtilsContainer().getUtilsMap().get("MGK").getValue();
		if(p.getBauteile()!=null && !p.getBauteile().isEmpty()){
			for(Bauteil b:p.getBauteile()){
				mek+=b.getMenge()*getUoA(b)*
						(b.getMaterial()!=null ? b.getMaterial().getPreis() : 0)*
						(b.getMaterial()!=null ? (1+b.getMaterial().getVerschnitt()/100) : 1);
			}
			price+= mek*(1+mgk/100);
		}
		//HERSTELLKOSTEN
		
		price*= (1+getUtilsContainer().getUtilsMap().get("VWGK").getValue()/100);
		price*= (1+getUtilsContainer().getUtilsMap().get("VTGK").getValue()/100);
		//SELBSTKOSTEN
		
		price*= (1+getUtilsContainer().getUtilsMap().get("WG").getValue()/100);
		price*= (1+getUtilsContainer().getUtilsMap().get("Sk").getValue()/100);
		price*= (1+getUtilsContainer().getUtilsMap().get("MRab").getValue()/100);
		
		//NETTOPREIS
		
		return price;
	}
	
	//Get Unit of Accounting for every Material
	private double getUoA(Bauteil b){
		switch(b.getMaterial().getEinheit()){
			case m2: return b.getLaenge()*b.getBreite()/(1E6);
			case m3: return b.getLaenge()*b.getBreite()*b.getDicke()/(1E9);
			default: return 1;
		}
	}

	@Override
	public double calculateBruttoPrice(Produkt p) {
		double price=p.getPreis();
		price*= (1+getUtilsContainer().getUtilsMap().get("USt").getValue()/100);
		return price;
	}
	
	

}
