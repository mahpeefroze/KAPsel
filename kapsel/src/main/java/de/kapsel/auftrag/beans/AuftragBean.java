package de.kapsel.auftrag.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;
import org.springframework.dao.DataAccessException;

import de.kapsel.auftrag.entities.Auftrag;
import de.kapsel.auftrag.entities.ProduktWrapper;
import de.kapsel.auftrag.services.IAuftragService;
import de.kapsel.auftrag.services.IProduktWrapperService;
import de.kapsel.global.ETypes;
import de.kapsel.global.beans.AbstractModulBean;
import de.kapsel.global.entities.AbstractKapselEntity;
import de.kapsel.kunde.entities.Kunde;
import de.kapsel.kunde.services.IKundeService;
import de.kapsel.produkt.services.IProduktService;

@ManagedBean
@ViewScoped
public class AuftragBean extends AbstractModulBean implements Serializable{


	private static final long serialVersionUID = 1L;
	private List<Auftrag> auftraege;
	private Auftrag selectedAuftrag;
	private Auftrag newAuftrag;
	private ProduktWrapper selectedProduktWrapper;
	
	private HashSet<ProduktWrapper> tempPwList;

	@ManagedProperty(value="#{auftragService}")
	private IAuftragService auftragService;
	
	@ManagedProperty(value="#{produktService}")
	private IProduktService produktService;
	
	@ManagedProperty(value="#{produktWrapperService}")
	private IProduktWrapperService produktWrapperService;
	
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
			setAuftraege(auftragService.getAuftraegeWithChildren());
			setSelectedAuftrag(getAuftraege().get(0));
			setEmptyList(false);
			setEditMode(false);
		}catch(DataAccessException e) {
			System.out.println(e.getStackTrace());
		}catch(NullPointerException e) {
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
		getNewAuftrag().setProdukte(new HashSet<ProduktWrapper>());
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
	
	public IProduktWrapperService getProduktWrapperService() {
		return produktWrapperService;
	}

	public void setProduktWrapperService(IProduktWrapperService produktWrapperService) {
		this.produktWrapperService = produktWrapperService;
	}

	public IKundeService getKundeService() {
		return kundeService;
	}

	public void setKundeService(IKundeService kundeService) {
		this.kundeService = kundeService;
	}
	
	public ProduktWrapper getSelectedProduktWrapper() {
		return selectedProduktWrapper;
	}

	public void setSelectedProduktWrapper(ProduktWrapper selectedProduktWrapper) {
		this.selectedProduktWrapper = selectedProduktWrapper;
	}
	
	
	//endregion Getter/Setter

	

	//Listener for Selection in auftragDT in Nav Panel
	public void loadAuftrag(SelectEvent event) {
		setSelectedAuftrag((Auftrag) event.getObject());
    }
	
	public void loadPassedAuftrag(){
		for(Auftrag a:getAuftraege()){
			if(a.getId()==getPassedID()){
				setSelectedAuftrag(a);
			}
		}
	}
	
	public String redirectToKunde(long id){
		//pK is the name of variable in viewParam [kunde]
		return "kunde.xhtml?faces-redirect=true&pK="+id;
	}
	
	//Basic strategy for creating new AuftragNr, get highest existing and icrement it by 1
	public long createAnr(){
		// TODO proper logic implementation needed
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
	
			getNewAuftrag().setAnr(createAnr());
			getNewAuftrag().setStatus(ETypes.AuftragS.Offen);
			getNewAuftrag().setStartdatum(new Date());
			getNewAuftrag().getKunde().getAuftraege().add(getNewAuftrag());
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
	
	public void addProduktWrapper(){
		ProduktWrapper pw = new ProduktWrapper();
		pw.setPosition(getSelectedAuftrag().getProdukte().size()+1);
		pw.setbKey(AbstractKapselEntity.generateBKey());
		getSelectedAuftrag().getProdukte().add(pw);
		tempPwList.add(pw);
	}
	
	public void deleteProduktWrapper(){
		getSelectedAuftrag().getProdukte().remove(getSelectedProduktWrapper());
		updateAuftrag();
	}

	
	public ArrayList<ProduktWrapper> pwToList(){
		ArrayList<ProduktWrapper> sortedList= new ArrayList<ProduktWrapper>(getSelectedAuftrag().getProdukte());
		Collections.sort(sortedList);
		return sortedList; 
	}
	
	@Override
	public void enableEditMode() {
		super.enableEditMode();
		tempPwList = new HashSet<ProduktWrapper>();
	}
	
	@Override
	public void onEditComplete() {
		updateAuftrag();
		disableEditMode();
	}

	@Override
	public void cancelEditMode() {
		if(tempPwList!=null && !tempPwList.isEmpty()){
			getSelectedAuftrag().getProdukte().removeAll(tempPwList);
		}
		disableEditMode();
	}

}
