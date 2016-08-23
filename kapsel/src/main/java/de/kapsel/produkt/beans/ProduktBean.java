package de.kapsel.produkt.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.springframework.dao.DataAccessException;

import de.kapsel.produkt.Bauteil;
import de.kapsel.produkt.Material;
import de.kapsel.produkt.Produkt;
import de.kapsel.produkt.services.IMaterialService;
import de.kapsel.produkt.services.IProduktService;

@ManagedBean
@ViewScoped
public class ProduktBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private List<Produkt> produkte;
	private Produkt selectedProdukt;
	private Produkt newProdukt;
	private boolean stuecklisteCB;
	private long materialId;

	@ManagedProperty(value="#{produktService}")
	private IProduktService produktService;
	
	@ManagedProperty(value="#{materialService}")
	private IMaterialService materialService;

	//Gather Items to fill the table
	public ProduktBean(){
		//Cant call the Service at Bean creation time, because injection happens later so NullPointer would be thrown
		this.newProdukt = new Produkt();
	}

	@PostConstruct
    public void init() {
		try{
			setProdukte(produktService.getProdukte());
			setSelectedProdukt(getProdukte().get(0));
			//Clearing newProudukt Dialog fields
			this.newProdukt = new Produkt();
			stuecklisteCB=false;
		}catch(DataAccessException e) {
			System.out.println(e.getStackTrace());
		}catch(IndexOutOfBoundsException e){
			System.out.println(e.getMessage() + ": keine Einträge vorhanden");
		}
		
		//
		List<Bauteil> bSet = new ArrayList<Bauteil>();
		getNewProdukt().setBauteile(bSet);
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

	public Produkt getNewProdukt() {
		return newProdukt;
	}

	public void setNewProdukt(Produkt newProdukt) {
		this.newProdukt = newProdukt;
	}
	
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


	public long getMaterialId() {
		return materialId;
	}

	public void setMaterialId(long materialId) {
		this.materialId = materialId;
	}

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
			this.newProdukt.setPnr(createPnr());
			//If stueckliste checkbox unchecked
			if(!stuecklisteCB){
				getNewProdukt().setBauteile(null);
			}
			getProduktService().addProdukt(this.newProdukt);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		init();

	}
	
	//Add 1 Bauteil with default Values to Stueckliste-DT of NewProdukt
	public void addBauteil(ActionEvent actionEvent){
		Bauteil b = new Bauteil();
		b.setMaterial(getMaterialService().getMaterialById(99));
		b.setPosition(getNewProdukt().getBauteile().size()+1); 
		getNewProdukt().getBauteile().add(b);
	}
	

	
	//Update Bauteil Values in produktNew Dialog
	public void onDialogEdit(CellEditEvent event){
        updateBauteile(getNewProdukt(), event, true); //RowKey will be 0 for not yet created objects :C
	}
	
	//Update Bauteil Values in Details View + DB
	public void onSelectedCellEdit(CellEditEvent event){
        updateBauteile(getSelectedProdukt(), event, false);
        //Stays in parent Event Call
        updateProdukt();
	}
	
	//Update Bauteil Values in Model
	private void updateBauteile(Produkt p, CellEditEvent event, boolean onCreate){
		String newValue = event.getNewValue().toString();
        int absPosition = Integer.parseInt(event.getRowKey());
        List<Bauteil> s = p.getBauteile();
        //Iterate through all Bauteile of 1 Produkt to find the one changed | position corresponding to rowKey - PF DataTable property
        for(Bauteil b : s){
        	//RowKey corresponds with TableID
        	if(!onCreate){
        		if(b.getId()!=absPosition) continue;
        	}else{
        		if(b.getPosition()!=absPosition) continue;
        	}
        	
        	updateBauteilValue(b, event.getColumn().getHeaderText(), newValue);
        }
	}
	
	//Edit selected value of 1 Bauteil - no index is given, so differentiation must be made - REVISION NEEDED, ENUM SWITCH MIGHT BE BETTER
	private void updateBauteilValue(Bauteil b, String colName, String value){
		if(colName.equals("Stck.")){
			b.setMenge(Integer.parseInt(value));
			return;
		}
		if(colName.equals("Bezeichnung")){
			b.setName(value);
			return;
		}
		if(colName.equals("Länge")){
			b.setLaenge(Double.parseDouble(value));
			return;
		}
		if(colName.equals("Breite")){
			b.setBreite(Double.parseDouble(value));
			return;
		}
		if(colName.equals("Dicke")){
			b.setDicke(Double.parseDouble(value));
			return;
		}
		if(colName.equals("Bemerkung")){
			b.setBemerkung(value);
			return;
		}
		if(colName.equals("Werkstoff")){
			Material material = getMaterialService().getMaterialById(getMaterialId());
			b.setMaterial(material);
			return;
		}
	}
	
	

	public void updateProdukt(){
		getProduktService().updateProdukt(this.selectedProdukt);
	}

	public void deleteProdukt(){
		getProduktService().deleteProdukt(this.selectedProdukt);
		init();
	}
	
	
	
}
