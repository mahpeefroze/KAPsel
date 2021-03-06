package de.kapsel.core.auftrag.beans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
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
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;

import de.kapsel.core.auftrag.entities.Auftrag;
import de.kapsel.core.auftrag.entities.KapselDocument;
import de.kapsel.core.auftrag.entities.ProduktWrapper;
import de.kapsel.core.auftrag.services.IAuftragService;
import de.kapsel.core.auftrag.services.IKapselDocumentService;
import de.kapsel.core.auftrag.services.IProduktWrapperService;
import de.kapsel.core.kunde.entities.Kunde;
import de.kapsel.core.produkt.entities.Produkt;
import de.kapsel.core.produkt.services.IProduktService;
import de.kapsel.core.util.DTItem;
import de.kapsel.core.util.ETypes;
import de.kapsel.core.util.IKapselCalculator;
import de.kapsel.core.util.beans.AbstractModulBean;
import de.kapsel.core.util.beans.UtilsBean;
import de.kapsel.core.util.entities.AbstractKapselEntity;

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
	private KapselDocument selectedDokument;
	private KapselDocument newDokument;
	private UploadedFile uploadedDokument;
	
	private boolean newProdBool;
	private boolean templProdBool;
	private boolean selProdBool;
	
	private HashSet<ProduktWrapper> tempPwList;
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
	
	public AuftragBean(){}

	@PostConstruct
    public void myInit() {
		try{
			setAuftraege(auftragService.getAuftraegeWithChildren());
			Collections.sort(getAuftraege());
			setSelectedAuftrag(getAuftraege().get(0));
			setEmptyList(false);
			disableEditMode();
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
		setNewDokument(new KapselDocument());
	}
	
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
	
	public void calculatePreise(){
		getSelectedAuftrag().setPreis(getAuftragCalc().calculateBruttoPrice(getSelectedAuftrag()));
		getSelectedAuftrag().setNettoPreis(getAuftragCalc().calculateNettoPrice(getSelectedAuftrag()));
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
		newProdBool=false;
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
		case 2: prepareProduktSelection(); 
				break;
		}
	}
	
	private void prepareProduktTemplates(){
		if(getTemplates()==null){
			setTemplates(getProduktService().getTemplates());
		}
	}
	
	private void prepareProduktSelection(){
		try{
			if(getStdProdukte()==null){
				ArrayList<String> prodNames = new ArrayList<>();
				produktMap = new HashMap<>();
				for(Produkt p:getProduktService().getNonTemplates()){
					produktMap.put(p.getName(), p);
					prodNames.add(p.getName());
				}
				setStdProdukte(new DualListModel<>(prodNames, new ArrayList<String>()));
			}
			selProdBool=true;
		}catch (NullPointerException e) {
			e.printStackTrace();
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
		String basename = FilenameUtils.getBaseName(filename) + "_";
		basename = basename.replaceAll("\\s+", "_");
		String extension = "." + FilenameUtils.getExtension(filename);
		
		input = uploadedDokument.getInputstream();
		String path="/kapselUploads/"+getSelectedAuftrag().getAnr();
		getNewDokument().setPath(path);
		File auftragFolder = new File(System.getProperty("jboss.home.dir")+path);
		if(!auftragFolder.exists()){
			if(auftragFolder.mkdir()){
				System.out.println("Directory "+auftragFolder.getPath()+" created succesfully");
			}else{
				System.out.println("Couldn't create "+auftragFolder.getPath());
				getNewDokument().setPath("/kapselUploads");
			}
		}else{
			System.out.println("Path already exists.");
		}
		
		File file = File.createTempFile(basename, extension, new File(System.getProperty("jboss.home.dir")+getNewDokument().getPath()));
		output = new FileOutputStream(file);
		getNewDokument().setFileName(file.getName());
		
		try{
			IOUtils.copy(input, output);
		}catch (IOException e){
			e.printStackTrace();
		}finally {
			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(output);
		}
		
	}
	
	public StreamedContent downloadDokument(KapselDocument kd) throws IOException{
		StreamedContent file;
		FacesContext fc = FacesContext.getCurrentInstance();
	    ExternalContext ec = fc.getExternalContext();
		InputStream stream = new URL("http://localhost:8080/uploads/"+getSelectedAuftrag().getAnr()+"/"+kd.getFileName()).openStream();
        file = new DefaultStreamedContent(stream, ec.getMimeType(kd.getFileName()), kd.getFileName());
		return file;
	}
	
	public void addDokument(){
			getNewDokument().setbKey(AbstractKapselEntity.generateBKey());
			getNewDokument().setPosition(getSelectedAuftrag().getDokumente().size()+1);
			if(getNewDokument().getName().equals("")){
				getNewDokument().setName(getNewDokument().getFileName());
			}
			if(!getNewDokument().getPath().equals("")){
				getSelectedAuftrag().getDokumente().add(getNewDokument());
			}
	}
	
	public void deleteDokument(){
		if(getSelectedDokument()!=null){
			updateItemPosition(getSelectedDokument().getPosition(), new ArrayList<DTItem>(getSelectedAuftrag().getDokumente()));
			getSelectedAuftrag().getDokumente().remove(getSelectedDokument());
		}
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
		setSelectedTemplateId(0);
		Auftrag orig = getAuftragService().getAuftragById(getSelectedAuftrag().getId());
		getAuftraege().set(getAuftraege().indexOf(getSelectedAuftrag()), orig);
		setSelectedAuftrag(orig);
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
		try{
		updateAuftrag();
		getUtilsContainer().updateNrStorage();
		}catch (HibernateOptimisticLockingFailureException e){
			System.out.println("Version mismatch. Optimistic Lock.");
			int i = tempPwList.size();
			while(i>0){
				getUtilsContainer().rollbackLast("PNR");
				i--;
			}
		}
		
		disableEditMode();
	}

	@Override
	public void cancelEditMode() {
		Auftrag orig = getAuftragService().getAuftragById(getSelectedAuftrag().getId());
		getAuftraege().set(getAuftraege().indexOf(getSelectedAuftrag()), orig);
		setSelectedAuftrag(orig);
		disableEditMode();
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


	
}
