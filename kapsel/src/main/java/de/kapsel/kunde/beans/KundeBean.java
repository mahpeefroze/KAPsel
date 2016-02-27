package de.kapsel.kunde.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;
import org.springframework.dao.DataAccessException;

import de.kapsel.global.Adresse;
import de.kapsel.global.services.IAdresseService;
import de.kapsel.kunde.KGruppe;
import de.kapsel.kunde.Kunde;
import de.kapsel.kunde.services.IKGruppeService;
import de.kapsel.kunde.services.IKundeService;

@ManagedBean
@ViewScoped
public class KundeBean implements Serializable{


	private static final long serialVersionUID = 1L;
	private List<Kunde> kunden;
	private Kunde selectedKunde;
	private Kunde newKunde;
	private long kGruppeId;
	
	
	private String newName;
	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	
	
	
	@ManagedProperty(value="#{kundeService}")
	private IKundeService kundeService;
	
	@ManagedProperty(value="#{kgruppeService}")
	private IKGruppeService kGruppeService;
	
	@ManagedProperty(value="#{adresseService}")
	private IAdresseService adresseService;
	
	//Gather Items to fill the table
	public KundeBean(){
		//Cant call the Service at Bean creation time, because injection happens later so NullPointer would be thrown
		//setKunden(kundeService.getKunden()); -> Moved to postconstruct init()
		this.newKunde = new Kunde();
	}

	@PostConstruct
    public void init() {
		setKunden(kundeService.getKunden());
		setSelectedKunde(getKunden().get(0));
		Adresse adresse = new Adresse();
		newKunde.setAdresse(adresse);
	}

	
	//Getter and Setter for the Fields
	//Container for SingleSelectTable Items
	public List<Kunde> getKunden() {
		return kunden;
	}

	public void setKunden(List<Kunde> kunden) {
		this.kunden = kunden;
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
	
	public IAdresseService getAdresseService() {
		return adresseService;
	}

	public void setAdresseService(IAdresseService adresseService) {
		this.adresseService = adresseService;
	}

	//Load data of specific Item into details-table; not called on page load -> additional load in init()
	public void loadKunde(SelectEvent event) {
		setSelectedKunde((Kunde) event.getObject());
    }
	
	//Basic strategy for creating new KundenNr, get highest existing and icrement it by 1
	public long createKnr(){
		long knr = getKunden().get(0).getKnr();
		for(Kunde k : getKunden()){
			if(k.getKnr()>knr){
				knr=k.getKnr();
			}
		}
		return knr + 1;
	}
	
	public void addKunde(){

		try {
			this.newKunde.setKnr(createKnr());
			KGruppe kGruppe = getkGruppeService().getKGruppeById(kGruppeId);
			this.newKunde.setGruppe(kGruppe);
			getKundeService().addKunde(this.newKunde);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
		init();

	}

	public void updateKunde(){
		getKundeService().updateKunde(this.selectedKunde);
	}

	public void deleteKunde(){
		getKundeService().deleteKunde(this.selectedKunde);
		init();
	}



}
