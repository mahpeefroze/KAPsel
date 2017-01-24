package de.kapsel.auftrag.beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.UploadedFile;
import org.springframework.dao.DataAccessException;

import de.kapsel.auftrag.entities.Auftrag;
import de.kapsel.auftrag.entities.KapselDocument;
import de.kapsel.auftrag.entities.ProduktWrapper;
import de.kapsel.auftrag.services.IAuftragService;
import de.kapsel.auftrag.services.IKapselDocumentService;
import de.kapsel.auftrag.services.IProduktWrapperService;
import de.kapsel.global.DTItem;
import de.kapsel.global.ETypes;
import de.kapsel.global.IKapselCalculator;
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
	private KapselDocument selectedDokument;
	private KapselDocument newDokument;
	private long selectedTemplateId;
	private List<Produkt> templates;
	private DualListModel<String> stdProdukte;
	private UploadedFile uploadedDokument;
	
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
	
	@ManagedProperty(value="#{kapselDocumentService}")
	private IKapselDocumentService kapselDocumentService;
	
	@ManagedProperty(value="#{utilsBean}")
	private UtilsBean utilsContainer;
	
	@ManagedProperty(value="#{basicAuftragCalculator}")
	private IKapselCalculator<Auftrag> auftragCalc;
	
	//Gather Items to fill the table
	public AuftragBean(){
		//Cant call the Service at Bean creation time, because injection happens later so NullPointer would be thrown
		//setAuftraege(auftragService.getAuftraege());  -> Moved to postconstruct init()
		this.newAuftrag = new Auftrag();
	}

	@PostConstruct
    public void myInit() {
		try{
			setAuftraege(auftragService.getAuftraegeWithChildren());
			setSelectedAuftrag(getAuftraege().get(0));
			setNewDokument(new KapselDocument());
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
		getNewAuftrag().setDokumente(new HashSet<KapselDocument>());
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
	
	public IKapselDocumentService getKapselDocumentService() {
		return kapselDocumentService;
	}

	public void setKapselDocumentService(IKapselDocumentService kapselDocumentService) {
		this.kapselDocumentService = kapselDocumentService;
	}

	public UtilsBean getUtilsContainer() {
		return utilsContainer;
	}

	public void setUtilsContainer(UtilsBean utilsContainer) {
		this.utilsContainer = utilsContainer;
	}
	
	public IKapselCalculator<Auftrag> getAuftragCalc() {
		return auftragCalc;
	}

	public void setAuftragCalc(IKapselCalculator<Auftrag> auftragCalc) {
		this.auftragCalc = auftragCalc;
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
	
	public KapselDocument getSelectedDokument() {
		return selectedDokument;
	}

	public void setSelectedDokument(KapselDocument selectedDokument) {
		this.selectedDokument = selectedDokument;
	}

	public KapselDocument getNewDokument() {
		return newDokument;
	}

	public void setNewDokument(KapselDocument newDokument) {
		this.newDokument = newDokument;
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

	public UploadedFile getUploadedDokument() {
		return uploadedDokument;
	}

	public void setUploadedDokument(UploadedFile uploadedDokument) {
		this.uploadedDokument = uploadedDokument;
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
			getNewAuftrag().setbKey(AbstractKapselEntity.generateBKey());
			getAuftragService().addAuftrag(getNewAuftrag());
			getUtilsContainer().updateNrStorage();
		} catch (Exception e) {
			getUtilsContainer().rollbackLast("ANR");
			e.printStackTrace();
		}
		myInit();
	}

	public void updateAuftrag(){
		getAuftragService().updateAuftrag(this.selectedAuftrag);
	}

	public void deleteAuftrag(){
		getAuftragService().deleteAuftrag(this.selectedAuftrag);
		myInit();
	}
	
	public void calculateNetto(){
		getSelectedAuftrag().setNettoPreis(getAuftragCalc().calculateNettoPrice(getSelectedAuftrag()));
	}
	
	public void calculateBrutto(){
		getSelectedAuftrag().setPreis(getAuftragCalc().calculateBruttoPrice(getSelectedAuftrag()));
	}
	
	public void calculateTime(){
		getSelectedAuftrag().setZeit(getAuftragCalc().calculateTime(getSelectedAuftrag()));
	}
	
	public void calculateDiscount(){
		getSelectedAuftrag().setDiscountPreis(getAuftragCalc().calculateAfterDiscount(getSelectedAuftrag()));
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
			updateItemPosition(getSelectedProduktWrapper().getPosition(), new ArrayList<DTItem>(getSelectedAuftrag().getProdukte()));
			getSelectedAuftrag().getProdukte().remove(getSelectedProduktWrapper());
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
	
	//region DOKUMENTE
	
	public void uploadToFile(FileUploadEvent event) throws IOException{
		InputStream input = null;
		OutputStream output = null;
		
		UploadedFile uploadedDokument = event.getFile();
		String filename = FilenameUtils.getName(uploadedDokument.getFileName());
		
		getNewDokument().setFileName(filename);
		
		input = uploadedDokument.getInputstream();
		String path=System.getProperty("jboss.home.dir")+"/kapselUploads/"+getSelectedAuftrag().getAnr();
		File auftragFolder = new File(path);
		if(!auftragFolder.exists()){
			if(auftragFolder.mkdir()){
				System.out.println("Directory "+auftragFolder.getPath()+" created succesfully");
				getNewDokument().setPath(path);
			}else{
				System.out.println("Couldn't create "+auftragFolder.getPath());
				getNewDokument().setPath(System.getProperty("jboss.home.dir")+"/kapselUploads");
			}
		}else{
			System.out.println("Path already exists.");
			getNewDokument().setPath(path);
		}
		output = new FileOutputStream(new File(getNewDokument().getPath(), getNewDokument().getFileName()));
		
		try{
			IOUtils.copy(input, output);
		}catch (IOException e){
			e.printStackTrace();
		}finally {
			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(output);
		}
		
	}
	
	public void dlDokument(KapselDocument kd) throws IOException{
		 	FacesContext fc = FacesContext.getCurrentInstance();
		    ExternalContext ec = fc.getExternalContext();
		    String filePath = "/"+System.getProperty("jboss.home.dir")+"/kapselUploads/"+getSelectedAuftrag().getAnr()+"/"+kd.getFileName();
		    String fileName = kd.getFileName();
		    ec.responseReset(); // Some JSF component library or some Filter might have set some headers in the buffer beforehand. We want to get rid of them, else it may collide.
		    ec.setResponseContentType(ec.getMimeType(fileName)); // Check http://www.iana.org/assignments/media-types for all types. Use if necessary ExternalContext#getMimeType() for auto-detection based on filename.
		    ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\""); // The Save As popup magic is done here. You can give it any file name you want, this only won't work in MSIE, it will use current request URL as file name instead.

		    OutputStream output = ec.getResponseOutputStream();
	    	FileInputStream input = new FileInputStream(filePath);
		    
	    	try{
				IOUtils.copy(input, output);
			}catch (IOException e){
				e.printStackTrace();
			}finally {
				IOUtils.closeQuietly(input);
				IOUtils.closeQuietly(output);
			}

		    fc.responseComplete();
	}
	
	public void addDokument(){
			getNewDokument().setbKey(AbstractKapselEntity.generateBKey());
			getNewDokument().setPosition(getSelectedAuftrag().getDokumente().size()+1);
			if(!getNewDokument().getPath().equals("")){
				getSelectedAuftrag().getDokumente().add(getNewDokument());
			}
	}
	
	public void deleteDokument(){
		getSelectedAuftrag().getDokumente().remove(getSelectedDokument());
	}
	
	public ArrayList<KapselDocument> kdToList(){
		if(getSelectedAuftrag()==null || getSelectedAuftrag().getDokumente()==null){
			return null;
		}
		ArrayList<KapselDocument> sortedList= new ArrayList<KapselDocument>(getSelectedAuftrag().getDokumente());
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
		Auftrag orig = getAuftragService().getAuftragById(getSelectedAuftrag().getId());
		getAuftraege().set(getAuftraege().indexOf(getSelectedAuftrag()), orig);
		setSelectedAuftrag(orig);
		disableEditMode();
	}

}
