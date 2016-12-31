package de.kapsel.produkt.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.springframework.dao.DataAccessException;

import de.kapsel.produkt.entities.Material;
import de.kapsel.produkt.services.IMaterialService;

@ManagedBean
@RequestScoped
public class MaterialBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Material newMaterial;
	private List<Material> materialien;
	private Material selectedMaterial;
	
	
	@ManagedProperty(value="#{materialService}")
	private IMaterialService materialService;
	
	//region Getter & Setter
	public Material getNewMaterial() {
		return newMaterial;
	}

	public void setNewMaterial(Material newMaterial) {
		this.newMaterial = newMaterial;
	}

	public List<Material> getMaterialien() {
		return materialien;
	}

	public void setMaterialien(List<Material> materialien) {
		this.materialien = materialien;
	}

	public Material getSelectedMaterial() {
		return selectedMaterial;
	}

	public void setSelectedMaterial(Material selectedMaterial) {
		this.selectedMaterial = selectedMaterial;
	}

	public IMaterialService getMaterialService() {
		return materialService;
	}

	public void setMaterialService(IMaterialService materialService) {
		this.materialService = materialService;
	}
	//endregion

	@PostConstruct
    public void myInit() {
		try{
			setMaterialien(getMaterialService().getMaterialien());
			setSelectedMaterial(getMaterialien().get(0));
			resetNewMaterial();
		}catch (DataAccessException e) {
			System.out.println(e.getStackTrace());
		}catch (IndexOutOfBoundsException e){
			System.out.println(e.getMessage() + ": keine Materialien vorhanden");
		}
	}

	
	public void onMaterialEdit(Material m){
		setSelectedMaterial(m);
		updateMaterial();
	}
	
	public void resetNewMaterial(){
		setNewMaterial(new Material());
	}
	
	public void addMaterial(){
		getMaterialService().addMaterial(getNewMaterial());
		myInit();
	}
	
	public void updateMaterial(){
		getMaterialService().updateMaterial(getSelectedMaterial());
	}
	
	public void deleteMaterial(){
		getMaterialService().deleteMaterial(getSelectedMaterial());
		myInit();
	}
	
}
