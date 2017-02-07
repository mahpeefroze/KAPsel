package de.kapsel.core.produkt.beans;

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

import de.kapsel.core.produkt.entities.Arbeitsschritt;
import de.kapsel.core.produkt.entities.Bauteil;
import de.kapsel.core.produkt.entities.Produkt;
import de.kapsel.core.produkt.services.IProduktService;
import de.kapsel.core.util.DTItem;
import de.kapsel.core.util.IKapselCalculator;
import de.kapsel.core.util.beans.AbstractModulBean;
import de.kapsel.core.util.beans.UtilsBean;
import de.kapsel.core.util.entities.AbstractKapselEntity;

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
	
	@ManagedProperty(value="#{produktService}")
	private IProduktService produktService;
	
	@ManagedProperty(value="#{werkzeugBean}")
	private WerkzeugBean werkzeugContainer;
	
	@ManagedProperty(value="#{materialBean}")
	private MaterialBean materialContainer;
	
	@ManagedProperty(value="#{utilsBean}")
	private UtilsBean utilsContainer;
	
	@ManagedProperty(value="#{basicProduktCalculator}")
	private IKapselCalculator<Produkt> produktCalc;
	
	public ProduktBean(){}
	
	@PostConstruct
    public void myInit() {
		try{
			setProdukte(getProduktService().getProdukteWithChildren());
			Collections.sort(getProdukte());
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
	

	//Load data of specific Item into details-table; not called on page load -> additional load in init()
	public void loadProdukt(SelectEvent event) {
		setSelectedProdukt((Produkt) event.getObject());
    }
	
	public void loadPassedProdukt(){
		for(Produkt p:getProdukte()){
			if(p.getId()==getPassedID()){
				setSelectedProdukt(p);
			}
		}
	}
	
	public void calculateNetto(){
		getSelectedProdukt().setPreis(getProduktCalc().calculateNettoPrice(getSelectedProdukt()));
	}
	
	public void calculateTime(){
		getSelectedProdukt().setZeit(getProduktCalc().calculateTime(getSelectedProdukt()));
	}


	public void addProdukt(){
		try {
			//Implement logic for creating new PNR and also put it
			getNewProdukt().setPnr(getUtilsContainer().getNextMax("PNR"));
			getNewProdukt().setbKey(AbstractKapselEntity.generateBKey());
			getProduktService().addProdukt(getNewProdukt());
			getUtilsContainer().updateNrStorage();
		} catch (Exception e) {
			getUtilsContainer().rollbackLast("PNR");
			e.printStackTrace();
		}
		myInit();
	}
	
	public void updateProdukt(){
		getProduktService().updateProdukt(getSelectedProdukt());
	}

	public void deleteProdukt(){
		getProduktService().deleteProdukt(getSelectedProdukt());
		myInit();
	}
	
	
	//region BAUTEIL SECTION-----------------------------------------------------//
	
	//Set to List converter -> for displaying in PF DataTable
	public ArrayList<Bauteil> btToList(){
		if(getSelectedProdukt()==null || getSelectedProdukt().getBauteile()==null){
			return null;
		}
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
		setMaterialId(0); 
	}
	
	//Material change listener
	public void onMaterialChange(Bauteil b){
		b.setMaterial(getMaterialContainer().findMaterial(getMaterialId()));
	}
	
	public void deleteBauteil(){
		if(getSelectedBauteil()!=null){
			updateItemPosition(getSelectedBauteil().getPosition(), new ArrayList<DTItem>(getSelectedProdukt().getBauteile()));
			getSelectedProdukt().getBauteile().remove(getSelectedBauteil());
		}
	}
	
	//endregion

	//region ARBEITSSCHRITT SECTION-----------------------------------------------------//
	
	//Set to List converter -> for displaying in PF DataTable
	public ArrayList<Arbeitsschritt> asToList(){
		if(getSelectedProdukt()==null || getSelectedProdukt().getAschritte()==null){
			return null;
		}
		ArrayList<Arbeitsschritt> sortedList= new ArrayList<Arbeitsschritt>(getSelectedProdukt().getAschritte());
		Collections.sort(sortedList);
		return sortedList;
	}
	
	public void addArbeitsschritt(){
		Arbeitsschritt a = new Arbeitsschritt();
		a.setPosition(getSelectedProdukt().getAschritte().size()+1);
		a.setbKey(AbstractKapselEntity.generateBKey());
		getSelectedProdukt().getAschritte().add(a);
		setWerkzeugId(0);
	}
	
	//Werkzeug change listener
	public void onWerkzeugChange(Arbeitsschritt as){
		as.setWerkzeug(getWerkzeugContainer().findWerkzeug(getWerkzeugId()));
	}
	
	public void deleteArbeitsschritt(){
		if(getSelectedAschritt()!=null){
			updateItemPosition(getSelectedAschritt().getPosition(), new ArrayList<DTItem>(getSelectedProdukt().getAschritte()));
			getSelectedProdukt().getAschritte().remove(getSelectedAschritt());
		}
	}
	
	//endregion
	
	
	//region editMode
	public void enableEditMode(){
		super.enableEditMode();
	}

	@Override
	public void onEditComplete() {
		updateProdukt();
		disableEditMode();
	}

	@Override
	public void cancelEditMode() {
		Produkt orig = getProduktService().getProduktById(getSelectedProdukt().getId());
		getProdukte().set(getProdukte().indexOf(getSelectedProdukt()), orig);
		setSelectedProdukt(orig);
		disableEditMode();
	}
	
	//endregion
	

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

	public UtilsBean getUtilsContainer() {
		return utilsContainer;
	}

	public void setUtilsContainer(UtilsBean utilsContainer) {
		this.utilsContainer = utilsContainer;
	}

	public IKapselCalculator<Produkt> getProduktCalc() {
		return produktCalc;
	}

	
	public void setProduktCalc(IKapselCalculator<Produkt> produktCalc) {
		this.produktCalc = produktCalc;
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

}
