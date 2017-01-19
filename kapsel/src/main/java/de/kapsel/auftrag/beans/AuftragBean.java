package de.kapsel.auftrag.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;
import org.springframework.dao.DataAccessException;

import de.kapsel.auftrag.entities.Auftrag;
import de.kapsel.auftrag.entities.ProduktWrapper;
import de.kapsel.auftrag.services.IAuftragService;
import de.kapsel.auftrag.services.IProduktWrapperService;
import de.kapsel.global.ETypes;
import de.kapsel.global.beans.AbstractModulBean;
import de.kapsel.global.beans.UtilsBean;
import de.kapsel.global.entities.AbstractKapselEntity;
import de.kapsel.kunde.entities.Kunde;
import de.kapsel.produkt.entities.Produkt;
import de.kapsel.produkt.services.IProduktService;

@ManagedBean
@ViewScoped
public class AuftragBean extends AbstractModulBean implements Serializable{


	private static final long serialVersionUID = 1L;
	private List<Auftrag> auftraege;
	private Auftrag selectedAuftrag;
	private Auftrag newAuftrag;
	private ProduktWrapper selectedProduktWrapper;
	private Produkt newProdukt;
	private long selectedTemplateId;
	private List<Produkt> templates;
	private DualListModel<String> stdProdukte;
	
	private boolean newProdBool;
	private boolean templProdBool;
	private boolean selProdBool;
	
	private HashSet<ProduktWrapper> tempPwList;
	private HashSet<ProduktWrapper>	tempDelPwList;
	private HashMap<String, Produkt> produktMap;
	

	@ManagedProperty(value="#{auftragService}")
	private IAuftragService auftragService;
	
	@ManagedProperty(value="#{produktService}")
	private IProduktService produktService;
	
	@ManagedProperty(value="#{produktWrapperService}")
	private IProduktWrapperService produktWrapperService;
	
	@ManagedProperty(value="#{utilsBean}")
	private UtilsBean utilsContainer;
	
	
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
			System.out.println(e.getMessage() + ": keine Aufträge vorhanden");
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
	
	public UtilsBean getUtilsContainer() {
		return utilsContainer;
	}

	public void setUtilsContainer(UtilsBean utilsContainer) {
		this.utilsContainer = utilsContainer;
	}

	public ProduktWrapper getSelectedProduktWrapper() {
		return selectedProduktWrapper;
	}

	public void setSelectedProduktWrapper(ProduktWrapper selectedProduktWrapper) {
		this.selectedProduktWrapper = selectedProduktWrapper;
	}
	
	public Produkt getNewProdukt() {
		return newProdukt;
	}

	public void setNewProdukt(Produkt newProdukt) {
		this.newProdukt = newProdukt;
	}
	
	public long getSelectedTemplateId() {
		return selectedTemplateId;
	}

	public void setSelectedTemplateId(long selectedTemplateId) {
		this.selectedTemplateId = selectedTemplateId;
	}

	public List<Produkt> getTemplates() {
		return templates;
	}

	public void setTemplates(List<Produkt> templates) {
		this.templates = templates;
	}
	
	public DualListModel<String> getStdProdukte() {
		return stdProdukte;
	}

	public void setStdProdukte(DualListModel<String> stdProdukte) {
		this.stdProdukte = stdProdukte;
	}
	
	public boolean isNewProdBool() {
		return newProdBool;
	}

	public void setNewProdBool(boolean newProdBool) {
		this.newProdBool = newProdBool;
	}

	public boolean isTempProdBool() {
		return templProdBool;
	}

	public void setTempProdBool(boolean tempProdBool) {
		this.templProdBool = tempProdBool;
	}

	public boolean isSelProdBool() {
		return selProdBool;
	}

	public void setSelProdBool(boolean selProdBool) {
		this.selProdBool = selProdBool;
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
	
	public String redirectToProdukt(long id){
		return "produkt.xhtml?faces-redirect=true&pP="+id;
	}
	
	public void addAuftrag(){
		try {
	
			getNewAuftrag().setAnr(getUtilsContainer().getNextMax("ANR"));
			getNewAuftrag().setStatus(ETypes.AuftragS.Offen);
			getNewAuftrag().setStartdatum(new Date());
			getNewAuftrag().getKunde().getAuftraege().add(getNewAuftrag());
			getAuftragService().addAuftrag(getNewAuftrag());
		} catch (Exception e) {
			getUtilsContainer().rollbackLast("ANR");
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
	
	//region PRODUKT ADD/DELETE + DISPLAY
	
	public void addProduktNew(){
		if(getNewProdukt()!=null){
			ProduktWrapper pw = createProduktWrapper();
			getNewProdukt().setbKey(AbstractKapselEntity.generateBKey());
			pw.setProdukt(getNewProdukt());
		}
	}
	
	public void addProduktFromTemplate(){
		if(getSelectedTemplateId()!=0){
			ProduktWrapper pw = createProduktWrapper();
			pw.setProdukt(new Produkt().createFromTemplate(getProduktService().getProduktById((getSelectedTemplateId()))));
			pw.setName(pw.getProdukt().getName()+"Wrapper");
		}
	}
	
	public void addProduktFromSelection(){
		boolean contained=false;
		boolean firstRun=false;
		ProduktWrapper pw;
		Produkt p;
		if(tempPwList.isEmpty() && getSelectedAuftrag().getProdukte().isEmpty()){
			firstRun=true;
		}
		for(String prodName:getStdProdukte().getTarget()){
			p=produktMap.get(prodName);
			if(!firstRun){
				contained=false;
				if(!getSelectedAuftrag().getProdukte().isEmpty()){
					for(ProduktWrapper wrapper: getSelectedAuftrag().getProdukte()){
						if(wrapper.getProdukt().equals(p)){
							wrapper.setStueckzahl(wrapper.getStueckzahl()+1);
							contained=true;
							break;
						}
					}
				}
				if(!tempPwList.isEmpty() && !contained && !getSelectedAuftrag().getProdukte().containsAll(tempPwList)){
					for(ProduktWrapper wrapper:tempPwList){
						if(wrapper.getProdukt().equals(p)){
							wrapper.setStueckzahl(wrapper.getStueckzahl()+1);
							contained=true;
							break;
						}
					}
				}
			}
			
			if(!contained){
				pw = createProduktWrapper();
				pw.setProdukt(p);
				pw.setName(pw.getProdukt().getName()+"Wrapper");
			}
		}
	}
	
	public void addProduktDisplayChange(int source){
		newProdBool=false;
		templProdBool=false;
		selProdBool=false;
		switch(source){
		case 0: newProdBool=true;
				newProdukt = new Produkt();
				break;
		case 1: templProdBool=true;
				prepareProduktTemplates();
				break;
		case 2: selProdBool=true;
				prepareProduktSelection();
				break;
		}
	}
	
	private void prepareProduktTemplates(){
		if(getTemplates()==null){
			setTemplates(getProduktService().getTemplates());
		}
	}
	
	private void prepareProduktSelection(){
		if(getStdProdukte()==null){
			ArrayList<String> prodNames = new ArrayList<>();
			produktMap = new HashMap<>();
			for(Produkt p:getProduktService().getProdukte()){
				produktMap.put(p.getName(), p);
				prodNames.add(p.getName());
			}
			setStdProdukte(new DualListModel<>(prodNames, new ArrayList<String>()));
		}
	}
	
	private ProduktWrapper createProduktWrapper(){
		ProduktWrapper pw = new ProduktWrapper();
		pw.setPosition(getSelectedAuftrag().getProdukte().size()+1);
		pw.setbKey(AbstractKapselEntity.generateBKey());
		pw.setStueckzahl(1);
		getSelectedAuftrag().getProdukte().add(pw);
		tempPwList.add(pw);
		return pw;
	}
	
	public void deleteProduktWrapper(){
		if(getSelectedProduktWrapper()!=null){
			tempDelPwList.add(getSelectedProduktWrapper());
			updatePWPosition(getSelectedProduktWrapper().getPosition());
			getSelectedAuftrag().getProdukte().remove(getSelectedProduktWrapper());
		}
	}
	
	private void updatePWPosition(int delPos){
		for(ProduktWrapper pw:getSelectedAuftrag().getProdukte()){
			if(pw.getPosition()>delPos){
				pw.setPosition(pw.getPosition()-1);
			}
		}
	}

	
	public ArrayList<ProduktWrapper> pwToList(){
		if(getSelectedAuftrag()==null || getSelectedAuftrag().getProdukte()==null){
			return null;
		}
		ArrayList<ProduktWrapper> sortedList= new ArrayList<ProduktWrapper>(getSelectedAuftrag().getProdukte());
		Collections.sort(sortedList);
		return sortedList; 
	}
	
	//endregion
	
	@Override
	public void enableEditMode() {
		super.enableEditMode();
		tempPwList = new HashSet<ProduktWrapper>();
		tempDelPwList = new HashSet<ProduktWrapper>();
		setSelectedTemplateId(0);
	}
	
	@Override
	public void onEditComplete() {
		if(tempPwList!=null && !tempPwList.isEmpty()){
			Produkt p;
			for(ProduktWrapper pw:tempPwList){
				p=pw.getProdukt();
				//Nur für die neuerstellte Produkte (new oder fromTemplate)
				if(p.getPnr()==0){
					p.setPnr(getUtilsContainer().getNextMax("PNR"));
					p.setName("Produkt_" + p.getPnr());
				}
			}
		}
		updateAuftrag();
		getUtilsContainer().updateNrStorage();
		disableEditMode();
	}

	@Override
	public void cancelEditMode() {
		
//		if(tempPwList!=null && !tempPwList.isEmpty()){
//			getSelectedAuftrag().getProdukte().removeAll(tempPwList);
//		}
//		if(tempDelPwList!=null && !tempDelPwList.isEmpty()){
//			getSelectedAuftrag().getProdukte().addAll(tempDelPwList);
//		}
		Auftrag orig = getAuftragService().getAuftragById(getSelectedAuftrag().getId());
		int i=getAuftraege().indexOf(getSelectedAuftrag());
		getAuftraege().set(i, orig);
		setSelectedAuftrag(orig);
		disableEditMode();
	}

}
