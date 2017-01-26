package de.kapsel.core.auftrag.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import de.kapsel.core.auftrag.entities.ProduktWrapper;
import de.kapsel.core.util.dao.IGenericDAO;

public class ProduktWrapperService implements IProduktWrapperService{
	
	private IGenericDAO<ProduktWrapper> produktWrapperDAO;
	
	public IGenericDAO<ProduktWrapper> getProduktWrapperDAO() {
		return produktWrapperDAO;
	}

	public void setProduktWrapperDAO(IGenericDAO<ProduktWrapper> produktWrapperDAO) {
		this.produktWrapperDAO = produktWrapperDAO;
	}

	@Transactional(readOnly = false)
	public void addProduktWrapper(ProduktWrapper produktWrapper) {
		//Name Property isnt used for display anywhere, it consists of produkt name and "Wrapper"
		produktWrapper.setName(produktWrapper.getProdukt().getName() + "Wrapper");
		produktWrapperDAO.addItem(produktWrapper);
	}

	@Transactional(readOnly = false)
	public void updateProduktWrapper(ProduktWrapper produktWrapper) {
		produktWrapperDAO.updateItem(produktWrapper);
	}

	@Transactional(readOnly = false)
	public void deleteProduktWrapper(ProduktWrapper produktWrapper) {
		produktWrapperDAO.deleteItem(produktWrapper);
	}

	@Transactional(readOnly = true)
	public ProduktWrapper getProduktWrapperByName(String name) {
		return produktWrapperDAO.getItemByName(name);
	}

	@Transactional(readOnly = true)
	public ProduktWrapper getProduktWrapperById(long id) {
		return produktWrapperDAO.getItemById(id);
	}

	@Transactional(readOnly = true)
	public List<ProduktWrapper> getProduktWrappers() {
		return produktWrapperDAO.getItems();
	}

	
	
}
