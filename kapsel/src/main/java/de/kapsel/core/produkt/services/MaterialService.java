package de.kapsel.core.produkt.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import de.kapsel.core.produkt.entities.Material;
import de.kapsel.core.util.dao.IGenericDAO;

public class MaterialService implements IMaterialService {


	//Injection MaterialDAO
	private IGenericDAO<Material> materialDAO;

	public IGenericDAO<Material> getMaterialDAO() {
		return materialDAO;
	}

	public void setMaterialDAO(IGenericDAO<Material> materialDAO) {
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
