package de.kapsel.kunde.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;
import org.springframework.dao.DataAccessException;

import de.kapsel.auftrag.entities.Auftrag;
import de.kapsel.global.beans.AbstractModulBean;
import de.kapsel.global.entities.AbstractKapselEntity;
import de.kapsel.global.entities.Adresse;
import de.kapsel.kunde.entities.Kunde;
import de.kapsel.kunde.services.IKGruppeService;
import de.kapsel.kunde.services.IKundeService;

@ManagedBean
@ViewScoped
public class KundeBean extends AbstractModulBean implements Serializable{


	private static final long serialVersionUID = 1L;
	private List<Kunde> kunden;
	private Kunde selectedKunde;
	private Kunde newKunde;
	private long kGruppeId;
	
	@ManagedProperty(value="#{kundeService}")
	private IKundeService kundeService;
	
	@ManagedProperty(value="#{kgruppeService}")
	private IKGruppeService kGruppeService;
	
		
	public KundeBean(){}

	@PostConstruct
    public void init() {
		try{
			setKunden(getKundeService().getKundenWithChildren());
			setSelectedKunde(getKunden().get(0));
			setEmptyList(false);
			disableEditMode();
		}catch (DataAccessException e){
			System.out.println(e.getStackTrace());
		}catch(IndexOutOfBoundsException e){
			System.out.println(e.getMessage() + ": keine Einträge vorhanden");
			setEmptyList(true);
		}
		resetNewKunde();
	}
	
	//newKunde
	public void resetNewKunde(){
		setNewKunde(new Kunde());
		getNewKunde().setAdresse(new Adresse());
		setkGruppeId(0);
	}

	
	//region Getter/Setter
	//Container for SingleSelectTable Items
	public List<Kunde> getKunden() {
		return kunden;
	}

	public void setKunden(List<Kunde> kunden) {
		this.kunden = kunden;
	}
	
	//Tried to lazy load Kunden for miniKundenListe -> multiple selects fired -> need to investigate
	public List<Kunde> getLazyKunden(){
		return getKundeService().getKunden();
	}
	
	//SingleSelection Item
	public Kunde getSelectedKunde() {
		return selectedKunde;
	}

	public void setSelectedKunde(Kunde selectedKunde) {
		this.selectedKunde = selectedKunde;
	}

	//Add New Item
	public Kunde getNewKunde() {
		return newKunde;
	}

	public void setNewKunde(Kunde newKunde) {
		this.newKunde = newKunde;
	}
	
	//Find kGruppe over ID
	public long getkGruppeId() {
		return kGruppeId;
	}

	public void setkGruppeId(long kGruppeId) {
		this.kGruppeId = kGruppeId;
	}

	//Getter and Setter for the Services
	public IKundeService getKundeService() {
		return kundeService;
	}

	public void setKundeService(IKundeService kundeService) {
		this.kundeService = kundeService;
	}
	
	public IKGruppeService getkGruppeService() {
		return kGruppeService;
	}

	public void setkGruppeService(IKGruppeService kGruppeService) {
		this.kGruppeService = kGruppeService;
	}
	
	
	//endregion Getter/Setter

	//Select Kunde passed as Attribute from another View
	public void loadPassedKunde(){
		long id=getPassedID();
		for(Kunde k:getKunden()){
			if(k.getId()==id){
				setSelectedKunde(k);
			}
		}
	}
	
	//Method for passing Auftrag id to AuftragView
	public String redirectToAuftrag(long id){
		return "auftrag.xhtml?faces-redirect=true&pA="+id;
	}

	//Listener for Selection in kundeDT in Nav Panel
	public void loadKunde(SelectEvent event) {
		setSelectedKunde((Kunde) event.getObject());
    }

	
	//Basic strategy for creating new KundenNr, get highest existing and increment it by 1
	public long createKnr(){
		long knr = 0;
		for(Kunde k : getKunden()){
			if(k.getKnr()>knr){
				knr=k.getKnr();
			}
		}
		return knr + 1;
	}
	
	public void addKunde(){
		try {
			getNewKunde().setKnr(createKnr());
			getNewKunde().setGruppe(getkGruppeService().getKGruppeById(getkGruppeId()));
			getNewKunde().setbKey(AbstractKapselEntity.generateBKey());
			getKundeService().addKunde(getNewKunde());
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
		init();

	}

	public void updateKunde(){
		getKundeService().updateKunde(getSelectedKunde());
	}
	

	public void deleteKunde(){
		getKundeService().deleteKunde(getSelectedKunde());
		init();
	}
	
	public ArrayList<Auftrag> atToList(){
		ArrayList<Auftrag> sortedList= new ArrayList<Auftrag>(getSelectedKunde().getAuftraege());
		Collections.sort(sortedList);
		return sortedList; 
	}

	@Override
	public void onEditComplete() {
		//Update kGruppe only if something new was selected, 0 is the index of itemLabel
		if(getkGruppeId()>0){
			getSelectedKunde().setGruppe(getkGruppeService().getKGruppeById(getkGruppeId()));
		}
		updateKunde();
		disableEditMode();
	}

	@Override
	public void cancelEditMode() {
		disableEditMode();
	}

	
}
