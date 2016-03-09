package de.kapsel.auftrag.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;
import org.springframework.dao.DataAccessException;

import de.kapsel.auftrag.Auftrag;
import de.kapsel.auftrag.services.IAuftragService;
import de.kapsel.kunde.Kunde;

@ManagedBean
@ViewScoped
public class AuftragBean implements Serializable{


	private static final long serialVersionUID = 1L;
	private List<Auftrag> auftraege;
	private Auftrag selectedAuftrag;
	private Auftrag newAuftrag;

	@ManagedProperty(value="#{auftragService}")
	private IAuftragService auftragService;


	//Gather Items to fill the table
	public AuftragBean(){
		//Cant call the Service at Bean creation time, because injection happens later so NullPointer would be thrown
		//setAuftraege(auftragService.getAuftraege());  -> Moved to postconstruct init()
		this.newAuftrag = new Auftrag();
	}

	@PostConstruct
    public void init() {
		setAuftraege(auftragService.getAuftraege());
		setSelectedAuftrag(getAuftraege().get(0));
	}

	//Container for SingleSelectTable Items
	public List<Auftrag> getAuftraege() {
		return auftraege;
	}

	public void setAuftraege(List<Auftrag> auftraege) {
		this.auftraege = auftraege;
	}

	//Add New Item
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

	//Load data of specific Item into details-table; not called on page load -> additional load in init()
	public void loadAuftrag(SelectEvent event) {
		setSelectedAuftrag((Auftrag) event.getObject());

    }
	
	//Basic strategy for creating new AuftragNr, get highest existing and icrement it by 1
		public long createAnr(){
			long anr = getAuftraege().get(0).getAnr();
			for(Auftrag a : getAuftraege()){
				long tempAnr = a.getAnr();
				if(tempAnr>anr){
					anr=tempAnr;
				}
			}
			return anr + 1;
		}


	public void addAuftrag(){

		try {

			//Implement logic for creating new ANR and also put it
			this.newAuftrag.setAnr(createAnr());
			this.newAuftrag.setKunde(null);
			getAuftragService().addAuftrag(this.newAuftrag);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}


	}

	public void updateAuftrag(){
		getAuftragService().updateAuftrag(this.selectedAuftrag);
	}

	public void deleteAuftrag(){
		getAuftragService().deleteAuftrag(this.selectedAuftrag);
		init();
	}



}
