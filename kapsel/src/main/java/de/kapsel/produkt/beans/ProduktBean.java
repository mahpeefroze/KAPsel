package de.kapsel.produkt.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.springframework.dao.DataAccessException;

import de.kapsel.global.DTItem;
import de.kapsel.global.beans.AbstractModulBean;
import de.kapsel.produkt.entities.Arbeitsschritt;
import de.kapsel.produkt.entities.Bauteil;
import de.kapsel.produkt.entities.Material;
import de.kapsel.produkt.entities.Produkt;
import de.kapsel.produkt.entities.Werkzeug;
import de.kapsel.produkt.services.IArbeitsschrittService;
import de.kapsel.produkt.services.IBauteilService;
import de.kapsel.produkt.services.IMaterialService;
import de.kapsel.produkt.services.IProduktService;
import de.kapsel.produkt.services.IWerkzeugService;

@ManagedBean
@ViewScoped
public class ProduktBean extends AbstractModulBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private List<Produkt> produkte;
	private Produkt selectedProdukt;
	private Produkt newProdukt;
	private Bauteil selectedBauteil;
	private Arbeitsschritt selectedAschritt;
	private boolean stuecklisteCB;
	private long materialId;
	private long werkzeugId;
	private boolean emptyList;

	@ManagedProperty(value="#{produktService}")
	private IProduktService produktService;
	
	@ManagedProperty(value="#{materialService}")
	private IMaterialService materialService;
	
	@ManagedProperty(value="#{bauteilService}")
	private IBauteilService bauteilService;
	
	@ManagedProperty(value="#{arbeitsschrittService}")
	private IArbeitsschrittService arbeitsschrittService;
	
	@ManagedProperty(value="#{werkzeugService}")
	private IWerkzeugService werkzeugService;
	
	public ProduktBean(){
		//Cant call the Service at Bean creation time, because injection happens later so NullPointer would be thrown
	}
	
	//Prepare data for first display or update after insert/delete
	@PostConstruct
    public void init() {
		try{
			setProdukte(produktService.getProdukte());
			setSelectedProdukt(getProdukte().get(0));
			setEmptyList(false);
		}catch(DataAccessException e) {
			System.out.println(e.getStackTrace());
		}catch(IndexOutOfBoundsException e){
			System.out.println(e.getMessage() + ": keine Einträge vorhanden");
			setEmptyList(true);
		}
		//Clearing newProdukt Dialog fields
		resetNewProdukt();
	}
	
	//newProdukt
	public void resetNewProdukt(){
		setNewProdukt(new Produkt());
		getNewProdukt().setBauteile(new ArrayList<Bauteil>());
		getNewProdukt().setAschritte(new ArrayList<Arbeitsschritt>());
		setMaterialId(0);
		setWerkzeugId(0);
		setStuecklisteCB(false);
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
	
	//Show/hide flag for Stueckliste in newProduktDlg
	public boolean isStuecklisteCB() {
		return stuecklisteCB;
	}

	public void setStuecklisteCB(boolean stuecklisteCB) {
		this.stuecklisteCB = stuecklisteCB;
	}

	//Getter and Setter Service
	public IProduktService getProduktService() {
		return produktService;
	}

	public void setProduktService(IProduktService produktService) {
		this.produktService = produktService;
	}
	
	public IMaterialService getMaterialService() {
		return materialService;
	}

	public void setMaterialService(IMaterialService materialService) {
		this.materialService = materialService;
	}
	
	public IBauteilService getBauteilService() {
		return bauteilService;
	}

	public void setBauteilService(IBauteilService bauteilService) {
		this.bauteilService = bauteilService;
	}
	
	public IArbeitsschrittService getArbeitsschrittService() {
		return arbeitsschrittService;
	}

	public void setArbeitsschrittService(IArbeitsschrittService arbeitsschrittService) {
		this.arbeitsschrittService = arbeitsschrittService;
	}

	public IWerkzeugService getWerkzeugService() {
		return werkzeugService;
	}

	public void setWerkzeugService(IWerkzeugService werkzeugService) {
		this.werkzeugService = werkzeugService;
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
	
	public boolean isEmptyList() {
		return emptyList;
	}

	public void setEmptyList(boolean emptyList) {
		this.emptyList = emptyList;
	}
	
	//endregion Getters/Setters
	

	//Load data of specific Item into details-table; not called on page load -> additional load in init()
	public void loadProdukt(SelectEvent event) {
		setSelectedProdukt((Produkt) event.getObject());
    }
		
		//Basic strategy for creating new ProduktNr, get highest existing and icrement it by 1
	public long createPnr(){
		long pnr=0;
		try{
			pnr = getProdukte().get(0).getPnr();
			for(Produkt p : getProdukte()){
				long tempPnr = p.getPnr();
				if(tempPnr>pnr){
					pnr=tempPnr;
				}
			}
		}catch(IndexOutOfBoundsException e){
			System.out.println(e.getMessage() + ": keine Einträge vorhanden");
		}
		return pnr + 1;
	}


	public void addProdukt(){
		try {
			//Implement logic for creating new PNR and also put it
			getNewProdukt().setPnr(createPnr());
			//If stueckliste checkbox unchecked
			if(!stuecklisteCB){
				getNewProdukt().setBauteile(null);
			}
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
	
	
	//--------------------------------------------BAUTEIL SECTION-----------------------------------------------------//
	
	
	//Add 1 Bauteil with default Values to Stueckliste-DT of NewProdukt
	public void addBauteil(ActionEvent actionEvent){
		Bauteil b = new Bauteil();
		//Splitting full clientID name
		String[] source = actionEvent.getComponent().getClientId().split(":");
		//Differentiating between Bauteil Add in View (selectedProdukt) and in Dialog (newProdukt)
		if(source[source.length-1].equals("btAddView")){
			b.setPosition(getSelectedProdukt().getBauteile().size()+1); 
			getSelectedProdukt().getBauteile().add(b);
		}else{
			b.setPosition(getNewProdukt().getBauteile().size()+1); 
			getNewProdukt().getBauteile().add(b);
		}
		//Reset SelectOneMenu's starting value
		setMaterialId(0); 
	}
	
	
	
	//Update Bauteil Values in produktNew Dialog
	public void onBauteileNew(CellEditEvent event){
        updateBauteile(getNewProdukt(), event);
	}
	
	//Update Bauteil Values in Details View + DB
	public void onBauteileEdit(CellEditEvent event){
        updateBauteile(getSelectedProdukt(), event);
        //Can only updated existing DB entries
        updateProdukt();
	}
	
	//Update Bauteil Values in Model
	private void updateBauteile(Produkt p, CellEditEvent event){
        String colName = event.getColumn().getHeaderText();
        if(colName.equals("Werkstoff")){
        	int itemPosition = event.getRowIndex();
            List<Bauteil> s = p.getBauteile();
			Material material = getMaterialService().getMaterialById(getMaterialId());
	        //Iterate through all Bauteile of 1 Produkt to find the one changed
	        for(Bauteil b : s){
	        	//On mismatch go next
	        	if(b.getPosition()!=(itemPosition+1)) continue;
	        	//Pass found Bauteil b
	        	b.setMaterial(material);
	        }
        }
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void deleteBauteilView(){
		try{
			//updateBauteilPosition(getSelectedBauteil().getPosition(), getSelectedProdukt());
			updateItemPosition(getSelectedBauteil().getPosition(), new ArrayList(getSelectedProdukt().getBauteile()));
			getSelectedProdukt().getBauteile().remove(getSelectedBauteil());
			updateProdukt();
			//Cascade somehow doesn't remove items from bauteile, need manual remove or else lots of dead records
			getBauteilService().deleteBauteil(getSelectedBauteil());
		}catch(java.lang.IllegalArgumentException e){
			System.out.println("selectedBauteil is empty");
		}catch(java.lang.NullPointerException e){
			System.out.println("selectedBauteil is empty Nullpointer");
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void deleteItemView(ActionEvent actionEvent){
		try{
			//Splitting full clientID name
			String[] source = actionEvent.getComponent().getClientId().split(":");
			//Differentiating between Bauteil Add in View (selectedProdukt) and in Dialog (newProdukt)
			if(source[source.length-1].equals("btDelView")){
				updateItemPosition(getSelectedBauteil().getPosition(), new ArrayList(getSelectedProdukt().getBauteile()));
				getSelectedProdukt().getBauteile().remove(getSelectedBauteil());
				//Cascade somehow doesn't remove items from bauteile, need manual remove or else lots of dead records
				//getBauteilService().deleteBauteil(getSelectedBauteil());
			}else if(source[source.length-1].equals("asDelView")){
				updateItemPosition(getSelectedAschritt().getPosition(), new ArrayList(getSelectedProdukt().getAschritte()));
				getSelectedProdukt().getAschritte().remove(getSelectedAschritt());
				//getArbeitsschrittService().deleteArbeitsschritt(getSelectedAschritt());
			}
			updateProdukt();
		}catch(java.lang.IllegalArgumentException e){
			System.out.println("selectedBauteil is empty");
		}catch(java.lang.NullPointerException e){
			System.out.println("selectedBauteil is empty Nullpointer");
		}
	}
	
	@SuppressWarnings({ "unchecked" , "rawtypes"})
	public void deleteBauteilDlg(){
		try{
			updateItemPosition(getSelectedBauteil().getPosition(), new ArrayList(getNewProdukt().getBauteile()));
			getNewProdukt().getBauteile().remove(getSelectedBauteil());
		}catch(java.lang.IllegalArgumentException e){
			System.out.println("selectedBauteil is empty");
		}catch(java.lang.NullPointerException e){
			System.out.println("selectedBauteil is empty Nullpointer");
		}
	}
	

	//--------------------------------------------ARBEITSSCHRITT SECTION-----------------------------------------------------//
	
	public void addArbeitsschritt(ActionEvent actionEvent){
		Arbeitsschritt a = new Arbeitsschritt();
		//Splitting full clientID name
		String[] source = actionEvent.getComponent().getClientId().split(":");
		//Differentiating between adding Arbeitsschritt in View (selectedProdukt) and in Dialog (newProdukt)
		if(source[source.length-1].equals("asAddView")){
			a.setPosition(getSelectedProdukt().getAschritte().size()+1); 
			getSelectedProdukt().getAschritte().add(a);
		}else{
			a.setPosition(getNewProdukt().getAschritte().size()+1); 
			getNewProdukt().getAschritte().add(a);
		}
		//Reset SelectOneMenu's starting value
		setWerkzeugId(0); 
		
	}
	
	//Update Bauteil Values in Details View + DB
	public void onArbeitsschritteEdit(CellEditEvent event){
        updateArbeitsschritte(getSelectedProdukt(), event);
        //Can only updated existing DB entries
        updateProdukt();
	}
	
	//Update Bauteil Values in Model
	private void updateArbeitsschritte(Produkt p, CellEditEvent event){
        String colName = event.getColumn().getHeaderText();
        if(colName.equals("Werkzeug")){
        	int itemPosition = event.getRowIndex();
            List<Arbeitsschritt> s = p.getAschritte();
			Werkzeug werkzeug = getWerkzeugService().getWerkzeugById(getWerkzeugId());
	        //Iterate through all Arbeitsschritte of 1 Produkt to find the one changed
	        for(Arbeitsschritt a : s){
	        	//On mismatch go next
	        	if(a.getPosition()!=(itemPosition+1)) continue;
	        	//Pass found Bauteil b
	        	a.setWerkzeug(werkzeug);
	        }
        }
	}
	
	private void updateItemPosition(int delPos, ArrayList<DTItem> items){
		//if(o.getClass().equals(Bauteil.class)){}
		for(DTItem t:items){
			if(t.getPosition()>delPos){
				t.setPosition(t.getPosition()-1);
			}
		}
	}
}
