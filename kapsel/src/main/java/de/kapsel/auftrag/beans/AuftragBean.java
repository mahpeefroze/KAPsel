package de.kapsel.auftrag.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;
import org.springframework.dao.DataAccessException;

import de.kapsel.auftrag.entities.Auftrag;
import de.kapsel.auftrag.services.IAuftragService;
import de.kapsel.global.ETypes;
import de.kapsel.global.beans.AbstractModulBean;
import de.kapsel.kunde.entities.Kunde;
import de.kapsel.kunde.services.IKundeService;
import de.kapsel.produkt.entities.Produkt;
import de.kapsel.produkt.services.IProduktService;

@ManagedBean
@ViewScoped
public class AuftragBean extends AbstractModulBean implements Serializable{


	private static final long serialVersionUID = 1L;
	private List<Auftrag> auftraege;
	private Auftrag selectedAuftrag;
	private Auftrag newAuftrag;

	@ManagedProperty(value="#{auftragService}")
	private IAuftragService auftragService;
	
	@ManagedProperty(value="#{produktService}")
	private IProduktService produktService;
	
	@ManagedProperty(value="#{kundeService}")
	private IKundeService kundeService;

	//Gather Items to fill the table
	public AuftragBean(){
		//Cant call the Service at Bean creation time, because injection happens later so NullPointer would be thrown
		//setAuftraege(auftragService.getAuftraege());  -> Moved to postconstruct init()
		this.newAuftrag = new Auftrag();
	}

	@PostConstruct
    public void init() {
		try{
			setAuftraege(auftragService.getAuftraege());
			setSelectedAuftrag(getAuftraege().get(0));
			setEmptyList(false);
		}catch(DataAccessException e) {
			System.out.println(e.getStackTrace());
		}catch(IndexOutOfBoundsException e){
			System.out.println(e.getMessage() + ": keine Einträge vorhanden");
			setEmptyList(true);
		}
		//Clearing newAuftrag Dialog fields after insert
		resetNewAuftrag();
	}
	
	//newAuftrag
	public void resetNewAuftrag(){
		setNewAuftrag(new Auftrag());
		getNewAuftrag().setProdukte(new ArrayList<Produkt>());
		getNewAuftrag().setKunde(new Kunde());
	}
	
	//region Getter/Setter
	
	//Container for SingleSelectTable Items
	public List<Auftrag> getAuftraege() {
		return auftraege;
	}

	public void setAuftraege(List<Auftrag> auftraege) {
		this.auftraege = auftraege;
	}

	
	public Auftrag getNewAuftrag() {
		return newAuftrag;
	}

	public void setNewAuftrag(Auftrag newAuftrag) {
		this.newAuftrag = newAuftrag;
	}

	//SingleSelection Item
	public Auftrag getSelectedAuftrag() {
		return selectedAuftrag;
	}

	public void setSelectedAuftrag(Auftrag selectedAuftrag) {
		this.selectedAuftrag = selectedAuftrag;
	}

	//Getter and Setter for the Service
	public IAuftragService getAuftragService() {
		return auftragService;
	}

	public void setAuftragService(IAuftragService auftragService) {
		this.auftragService = auftragService;
	}
	
	public IProduktService getProduktService() {
		return produktService;
	}

	public void setProduktService(IProduktService produktService) {
		this.produktService = produktService;
	}

	public IKundeService getKundeService() {
		return kundeService;
	}

	public void setKundeService(IKundeService kundeService) {
		this.kundeService = kundeService;
	}
	
	//endregion Getter/Setter

	//Load data of specific Item into details-table; not called on page load -> additional load in init()
	public void loadAuftrag(SelectEvent event) {
		setSelectedAuftrag((Auftrag) event.getObject());

    }
	
	//Basic strategy for creating new AuftragNr, get highest existing and icrement it by 1
	public long createAnr(){
		long anr=0;
		if(!isEmptyList()){
			anr = getAuftraege().get(0).getAnr();
			for(Auftrag a : getAuftraege()){
				long tempAnr = a.getAnr();
				if(tempAnr>anr){
					anr=tempAnr;
				}
			}
		}
		return anr + 1;
	}


	public void addAuftrag(){
		try {
	
			//Implement logic for creating new ANR and also put it
			getNewAuftrag().setAnr(createAnr());
			//getNewAuftrag().setKunde(getKundeService().getKundeById(1));
			getNewAuftrag().setStatus(ETypes.AuftragS.offen);
			getNewAuftrag().setStartdatum(new Date());
			getAuftragService().addAuftrag(getNewAuftrag());
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		init();
	}

	public void updateAuftrag(){
		getAuftragService().updateAuftrag(this.selectedAuftrag);
	}

	public void deleteAuftrag(){
		getAuftragService().deleteAuftrag(this.selectedAuftrag);
		init();
	}

	public void clearKundeSelection(){
		getNewAuftrag().setKunde(new Kunde());
	}

}
