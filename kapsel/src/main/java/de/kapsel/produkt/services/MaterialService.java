package de.kapsel.produkt.services;

import java.util.List;

import javax.faces.bean.ManagedProperty;

import org.springframework.transaction.annotation.Transactional;

import de.kapsel.produkt.Material;
import de.kapsel.produkt.dao.IMaterialDAO;

public class MaterialService implements IMaterialService {


	//Injection MaterialDAO
	@ManagedProperty(value="#{materialDAO}")
	private IMaterialDAO materialDAO;

	public IMaterialDAO getMaterialDAO() {
		return materialDAO;
	}

	public void setMaterialDAO(IMaterialDAO materialDAO) {
		this.materialDAO = materialDAO;
	}
	
	
	@Override
	@Transactional(readOnly = false)
	public void addMaterial(Material material) {
		materialDAO.addItem(material);

	}

	@Override
	@Transactional(readOnly = false)
	public void updateMaterial(Material material) {
		materialDAO.updateItem(material);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteMaterial(Material material) {
		materialDAO.deleteItem(material);

	}

	@Override
	@Transactional(readOnly = true)
	public Material getMaterialByName(String name) {
		return materialDAO.getItemByName(name);
	}

	@Override
	@Transactional(readOnly = true)
	public Material getMaterialById(long id) {
		return materialDAO.getItemById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Material> getMaterialien() {
		return materialDAO.getItems();
	}

}
