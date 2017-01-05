package de.kapsel.produkt.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;
import org.springframework.dao.DataAccessException;

import de.kapsel.global.DTItem;
import de.kapsel.global.beans.AbstractModulBean;
import de.kapsel.global.entities.AbstractKapselEntity;
import de.kapsel.produkt.entities.Arbeitsschritt;
import de.kapsel.produkt.entities.Bauteil;
import de.kapsel.produkt.entities.Produkt;
import de.kapsel.produkt.services.IProduktService;

@ManagedBean
@ViewScoped
public class ProduktBean extends AbstractModulBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private List<Produkt> produkte;
	private Produkt selectedProdukt;
	private Produkt newProdukt;
	private Bauteil selectedBauteil;
	private Arbeitsschritt selectedAschritt;
	private long materialId;
	private long werkzeugId;
	
	//No getters, for intern use only
	private HashSet<Bauteil> tempBtSet;
	private HashSet<Arbeitsschritt> tempAsSet;

	@ManagedProperty(value="#{produktService}")
	private IProduktService produktService;
	
	@ManagedProperty(value="#{werkzeugBean}")
	private WerkzeugBean werkzeugContainer;
	
	@ManagedProperty(value="#{materialBean}")
	private MaterialBean materialContainer;
	
	public ProduktBean(){
		//Cant call the Service at Bean creation time, because injection happens later so NullPointer would be thrown
	}
	
	//Prepare data for first display or update after insert/delete
	@PostConstruct
    public void init() {
		try{
			setProdukte(produktService.getProdukteWithChildren());
			setSelectedProdukt(getProdukte().get(0));
			setEmptyList(false);
			disableEditMode();
		}catch(DataAccessException e) {
			System.out.println(e.getStackTrace());
		}catch(IndexOutOfBoundsException e){
			System.out.println(e.getMessage() + ": keine Produkte vorhanden");
			setEmptyList(true);
		}
		//Initializing/Clearing newProdukt Dialog fields
		resetNewProdukt();
	}
	
	//newProdukt initialize/clear
	public void resetNewProdukt(){
		setNewProdukt(new Produkt());
		getNewProdukt().setBauteile(new HashSet<Bauteil>());
		getNewProdukt().setAschritte(new HashSet<Arbeitsschritt>());
		setMaterialId(0);
		setWerkzeugId(0);
	}

	//region Getters/Setters
	public Produkt getNewProdukt() {
		return newProdukt;
	}

	public void setNewProdukt(Produkt newProdukt) {
		this.newProdukt = newProdukt;
	}
	
	//Container for SingleSelectTable Items
	public List<Produkt> getProdukte() {
		return produkte;
	}

	public void setProdukte(List<Produkt> produkte) {
		this.produkte = produkte;
	}
	
	//SingleSelection Item 
	public Produkt getSelectedProdukt() {
		return selectedProdukt;
	}

	public void setSelectedProdukt(Produkt selectedProdukt) {
		this.selectedProdukt = selectedProdukt;
	}
	
	//Selection Bauteil
	public Bauteil getSelectedBauteil() {
		return selectedBauteil;
	}

	public void setSelectedBauteil(Bauteil selectedBauteil) {
		this.selectedBauteil = selectedBauteil;
	}
	
	//Selection Arbeitsschritt
	public Arbeitsschritt getSelectedAschritt() {
		return selectedAschritt;
	}

	public void setSelectedAschritt(Arbeitsschritt selectedAschritt) {
		this.selectedAschritt = selectedAschritt;
	}
	
	public MaterialBean getMaterialContainer() {
		return materialContainer;
	}

	public void setMaterialContainer(MaterialBean materialContainer) {
		this.materialContainer = materialContainer;
	}

	public WerkzeugBean getWerkzeugContainer() {
		return werkzeugContainer;
	}

	public void setWerkzeugContainer(WerkzeugBean werkzeugContainer) {
		this.werkzeugContainer = werkzeugContainer;
	}

	//Getter and Setter Service
	public IProduktService getProduktService() {
		return produktService;
	}

	public void setProduktService(IProduktService produktService) {
		this.produktService = produktService;
	}
	
	public long getMaterialId() {
		return materialId;
	}

	public void setMaterialId(long materialId) {
		this.materialId = materialId;
	}
	
	public long getWerkzeugId() {
		return werkzeugId;
	}

	public void setWerkzeugId(long werkzeugId) {
		this.werkzeugId = werkzeugId;
	}
	
	//endregion Getters/Setters
	

	//Load data of specific Item into details-table; not called on page load -> additional load in init()
	public void loadProdukt(SelectEvent event) {
		setSelectedProdukt((Produkt) event.getObject());
    }
	
	//Find Produkt in the list, alternative to query by id since all produkts are here anyway
	public Produkt findProdukt(long id){
		for(Produkt p:getProdukte()){
			if(p.getId()==id){
				return p;
			}
		}
		return null;
	}
		
	//Basic strategy for creating new ProduktNr, get highest existing and icrement it by 1
	public long createPnr(){
		long pnr=0;
		long tempPnr;
		try{
			pnr = getProdukte().get(0).getPnr();
			for(Produkt p : getProdukte()){
				tempPnr = p.getPnr();
				if(tempPnr>pnr){
					pnr=tempPnr;
				}
			}
		}catch(IndexOutOfBoundsException e){
			System.out.println(e.getMessage() + ": keine Produkte vorhanden");
		}
		return pnr + 1;
	}
	
	public void calculatePrice(){
		double price=0;
		try{
			if(!getSelectedProdukt().getAschritte().isEmpty()){
				//Work time[min] *( (tool [cost/h] + worker [cost/h])/ 60[h->min])
				for(Arbeitsschritt a:getSelectedProdukt().getAschritte()){
					price+=(a.getZeit()*a.getWerkzeug().getStundensatz()/60);
				}
			}
			//Get Unit of Accounting for each Bauteil-Material and multiply it by the amount and price 
			if(!getSelectedProdukt().getBauteile().isEmpty()){
				for(Bauteil b:getSelectedProdukt().getBauteile()){
					price+=b.getMenge()*getUoA(b)*b.getMaterial().getPreis();
				}
			}
		}catch (DataAccessException e) {
			e.printStackTrace();
		}
		getSelectedProdukt().setPreis(price);
	}
	
	//Get Unit of Accounting for every Material
	private double getUoA(Bauteil b){
		switch(b.getMaterial().getEinheit()){
			case kg: return 1;
			case l: return 1;
			case m2: return b.getLaenge()*b.getBreite()/(10E6);
			case m3: return b.getLaenge()*b.getBreite()*b.getDicke()/(10E9);
			case pcs: return 1;
			default: return 0;
		}
	}
	
	public void calculateTime(){
		int time=0;
		try{
			if(!getSelectedProdukt().getAschritte().isEmpty()){
				for(Arbeitsschritt a:getSelectedProdukt().getAschritte()){
					time+=a.getZeit();
				}
			}
		}catch (DataAccessException e) {
			e.printStackTrace();
		}
		getSelectedProdukt().setZeit(time);
	}


	public void addProdukt(){
		try {
			//Implement logic for creating new PNR and also put it
			getNewProdukt().setPnr(createPnr());
			getNewProdukt().setbKey(AbstractKapselEntity.generateBKey());
			getProduktService().addProdukt(getNewProdukt());
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		init();
	}
	
	public void updateProdukt(){
		getProduktService().updateProdukt(getSelectedProdukt());
	}

	public void deleteProdukt(){
		getProduktService().deleteProdukt(getSelectedProdukt());
		init();
	}
	
	
	//region BAUTEIL SECTION-----------------------------------------------------//
	
	//Set to List converter -> for displaying in PF DataTable
	public ArrayList<Bauteil> btToList(){
		ArrayList<Bauteil> sortedList= new ArrayList<Bauteil>(getSelectedProdukt().getBauteile());
		Collections.sort(sortedList);
		return sortedList;
	}
	
	//Add 1 Bauteil with default Values to Stueckliste-DT of NewProdukt
	public void addBauteil(){
		Bauteil b = new Bauteil();
		b.setPosition(getSelectedProdukt().getBauteile().size()+1);
		b.setbKey(AbstractKapselEntity.generateBKey());
		getSelectedProdukt().getBauteile().add(b);
		tempBtSet.add(b);
		setMaterialId(0); 
	}
	
	//Material change listener
	public void onMaterialChange(Bauteil b){
		b.setMaterial(getMaterialContainer().findMaterial(getMaterialId()));
	}
	
	public void deleteBauteil(){
		updateItemPosition(getSelectedBauteil().getPosition(), new ArrayList<DTItem>(getSelectedProdukt().getBauteile()));
		getSelectedProdukt().getBauteile().remove(getSelectedBauteil());
	}
	
	//endregion

	//region ARBEITSSCHRITT SECTION-----------------------------------------------------//
	
	//Set to List converter -> for displaying in PF DataTable
	public ArrayList<Arbeitsschritt> asToList(){
		ArrayList<Arbeitsschritt> sortedList= new ArrayList<Arbeitsschritt>(getSelectedProdukt().getAschritte());
		Collections.sort(sortedList);
		return sortedList;
	}
	
	public void addArbeitsschritt(){
		Arbeitsschritt a = new Arbeitsschritt();
		a.setPosition(getSelectedProdukt().getAschritte().size()+1);
		a.setbKey(AbstractKapselEntity.generateBKey());
		getSelectedProdukt().getAschritte().add(a);
		tempAsSet.add(a);
		setWerkzeugId(0);
	}
	
	//Werkzeug change listener
	public void onWerkzeugChange(Arbeitsschritt as){
		as.setWerkzeug(getWerkzeugContainer().findWerkzeug(getWerkzeugId()));
	}
	
	public void deleteArbeitsschritt(){
		updateItemPosition(getSelectedAschritt().getPosition(), new ArrayList<DTItem>(getSelectedProdukt().getAschritte()));
		getSelectedProdukt().getAschritte().remove(getSelectedAschritt());
	}
	
	//endregion
	
	//Only 1 Position can be deleted at the same time, so just -1 everything that was higher than deleted item
	private void updateItemPosition(int delPos, ArrayList<DTItem> items){
		for(DTItem t:items){
			if(t.getPosition()>delPos){
				t.setPosition(t.getPosition()-1);
			}
		}
	}
	
	
	//region editMode
	public void enableEditMode(){
		super.enableEditMode();
		tempBtSet = new HashSet<Bauteil>();
		tempAsSet = new HashSet<Arbeitsschritt>();
	}

	@Override
	public void onEditComplete() {
		updateProdukt();
		disableEditMode();
	}

	@Override
	public void cancelEditMode() {
		//Remove Arbeitsschritte if any were added
		if(tempAsSet!=null && !tempAsSet.isEmpty()){
			getSelectedProdukt().getAschritte().removeAll(tempAsSet);
		}
		//Remove Bauteil if any were added
		if(tempBtSet!=null && !tempBtSet.isEmpty()){
			getSelectedProdukt().getBauteile().removeAll(tempBtSet);
		}
		disableEditMode();
	}
	
	//endregion
	
}
