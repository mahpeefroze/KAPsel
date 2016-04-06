package de.kapsel.produkt.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.springframework.dao.DataAccessException;

import de.kapsel.produkt.Material;
import de.kapsel.produkt.services.IMaterialService;

@ManagedBean
@RequestScoped
public class MaterialBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Material material;
	private List<Material> materialien;
	private Material selectedMaterial;
	
	
	@ManagedProperty(value="#{materialService}")
	private IMaterialService materialService;

	@PostConstruct
    public void init() {
		try{
			setMaterialien(getMaterialService().getMaterialien());
			setSelectedMaterial(getMaterialien().get(0));
		}catch (DataAccessException e) {
			System.out.println(e.getStackTrace());
		}catch (IndexOutOfBoundsException e){
			System.out.println(e.getMessage() + ": keine Einträge vorhanden");
		}
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
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
	
	
	
	
}
