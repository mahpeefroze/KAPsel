package de.kapsel.produkt.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.springframework.dao.DataAccessException;

import de.kapsel.produkt.entities.Werkzeug;
import de.kapsel.produkt.services.IWerkzeugService;

@ManagedBean
@RequestScoped
public class WerkzeugBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private Werkzeug newWerkzeug;
	private Werkzeug selectedWerkzeug;
	private List<Werkzeug> werkzeuge;
	
	@ManagedProperty(value="#{werkzeugService}")
	private IWerkzeugService werkzeugService;
	
	//region Getter & Setter
	public Werkzeug getNewWerkzeug() {
		return newWerkzeug;
	}

	public void setNewWerkzeug(Werkzeug newWerkzeug) {
		this.newWerkzeug = newWerkzeug;
	}

	public Werkzeug getSelectedWerkzeug() {
		return selectedWerkzeug;
	}

	public void setSelectedWerkzeug(Werkzeug selectedWerkzeug) {
		this.selectedWerkzeug = selectedWerkzeug;
	}

	public List<Werkzeug> getWerkzeuge() {
		return werkzeuge;
	}

	public void setWerkzeuge(List<Werkzeug> werkzeuge) {
		this.werkzeuge = werkzeuge;
	}

	public IWerkzeugService getWerkzeugService() {
		return werkzeugService;
	}

	public void setWerkzeugService(IWerkzeugService werkzeugService) {
		this.werkzeugService = werkzeugService;
	}
	//endregion
	
	@PostConstruct
    public void myInit() {
		try{
			setWerkzeuge(getWerkzeugService().getWerkzeuge());
			setSelectedWerkzeug(getWerkzeuge().get(0));
			resetNewWerkzeug();
		}catch (DataAccessException e) {
			System.out.println(e.getStackTrace());
		}catch (IndexOutOfBoundsException e){
			System.out.println(e.getMessage() + ": keine Einträge vorhanden");
		}
	}
	

	public void onWerkzeugEdit(Werkzeug w){
		setSelectedWerkzeug(w);
		updateWerkzeug();
	}
	
	public void resetNewWerkzeug(){
		setNewWerkzeug(new Werkzeug());
	}
	
	public void addWerkzeug(){
		getWerkzeugService().addWerkzeug(getNewWerkzeug());
		myInit();
	}
	
	public void updateWerkzeug(){
		getWerkzeugService().updateWerkzeug(getSelectedWerkzeug());
	}
	
	public void deleteWerkzeug(){
		getWerkzeugService().deleteWerkzeug(getSelectedWerkzeug());
		myInit();
	}
	
	
	
	
}
