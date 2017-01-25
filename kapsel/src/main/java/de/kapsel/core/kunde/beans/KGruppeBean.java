package de.kapsel.core.kunde.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.springframework.dao.DataAccessException;

import de.kapsel.core.kunde.entities.KGruppe;
import de.kapsel.core.kunde.services.IKGruppeService;

@ManagedBean
@ViewScoped
public class KGruppeBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private KGruppe newKGruppe;
	private List<KGruppe> kGruppen;
	private KGruppe selectedKGruppe;

	@ManagedProperty(value="#{kgruppeService}")
	private IKGruppeService kGruppeService;

	@PostConstruct
    public void myInit() {
		try{
			setkGruppen(getkGruppeService().getKGruppen());
			setSelectedKGruppe(getkGruppen().get(0));
		}catch (DataAccessException e) {
			System.out.println(e.getStackTrace());
		}catch (IndexOutOfBoundsException e){
			System.out.println(e.getMessage() + ": keine KGruppen vorhanden");
		}
		resetNewKGruppe();
	}
	
	public void onKGruppeEdit(KGruppe kGruppe){
		setSelectedKGruppe(kGruppe);
		updateKGruppe();
	}
	
	public void addKGruppe(){
		getNewKGruppe().setAktiv(true);
		getkGruppeService().addKGruppe(getNewKGruppe());
		myInit();
	}
	
	public void resetNewKGruppe(){
		setNewKGruppe(new KGruppe());
	}
	
	public void updateKGruppe(){
		getkGruppeService().updateKGruppe(getSelectedKGruppe());
	}
	
	public void deleteKGruppe(){
		getkGruppeService().deleteKGruppe(getSelectedKGruppe());
		myInit();
	}
	

	//region getter & setter
	public KGruppe getNewKGruppe() {
		return newKGruppe;
	}

	public void setNewKGruppe(KGruppe newKGruppe) {
		this.newKGruppe = newKGruppe;
	}

	public List<KGruppe> getkGruppen() {
		return kGruppen;
	}

	public void setkGruppen(List<KGruppe> kGruppen) {
		this.kGruppen = kGruppen;
	}

	public IKGruppeService getkGruppeService() {
		return kGruppeService;
	}

	public void setkGruppeService(IKGruppeService kGruppeService) {
		this.kGruppeService = kGruppeService;
	}

	public KGruppe getSelectedKGruppe() {
		return selectedKGruppe;
	}

	public void setSelectedKGruppe(KGruppe selectedKGruppe) {
		this.selectedKGruppe = selectedKGruppe;
	}

	//endregion

}
