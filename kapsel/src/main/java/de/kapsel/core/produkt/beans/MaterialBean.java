package de.kapsel.core.produkt.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.springframework.dao.DataAccessException;

import de.kapsel.core.produkt.entities.Material;
import de.kapsel.core.produkt.services.IMaterialService;
import de.kapsel.core.util.entities.AbstractKapselEntity;

@ManagedBean
@ApplicationScoped
public class MaterialBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Material newMaterial;
	private List<Material> materialien;
	private Material selectedMaterial;
	
	
	@ManagedProperty(value="#{materialService}")
	private IMaterialService materialService;

	@PostConstruct
    public void myInit() {
		try{
			setMaterialien(getMaterialService().getMaterialien());
			setSelectedMaterial(getMaterialien().get(0));
		}catch (DataAccessException e) {
			System.out.println(e.getStackTrace());
		}catch (IndexOutOfBoundsException e){
			System.out.println(e.getMessage() + ": keine Materialien vorhanden");
		}
		resetNewMaterial();
	}

	
	public void onMaterialEdit(Material m){
		setSelectedMaterial(m);
		updateMaterial();
	}
	
	public void resetNewMaterial(){
		setNewMaterial(new Material());
	}
	
	public Material findMaterial(long id){
		for(Material m:getMaterialien()){
			if(m.getId()==id){
				return m;
			}
		}
		return null;
	}
	
	public void addMaterial(){
		getNewMaterial().setbKey(AbstractKapselEntity.generateBKey());
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


	
}
