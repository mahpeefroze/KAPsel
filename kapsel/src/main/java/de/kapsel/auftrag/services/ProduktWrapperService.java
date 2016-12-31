package de.kapsel.auftrag.services;

import java.util.List;

import de.kapsel.auftrag.entities.ProduktWrapper;
import de.kapsel.global.dao.IGenericDAO;

public class ProduktWrapperService implements IProduktWrapperService{
	
	private IGenericDAO<ProduktWrapper> produktWrapperDAO;
	
	public IGenericDAO<ProduktWrapper> getProduktWrapperDAO() {
		return produktWrapperDAO;
	}

	public void setProduktWrapperDAO(IGenericDAO<ProduktWrapper> produktWrapperDAO) {
		this.produktWrapperDAO = produktWrapperDAO;
	}

	@Override
	public void addProduktWrapper(ProduktWrapper produktWrapper) {
		//Name Property isnt used for display anywhere, it consists of produkt name and "Wrapper"
		produktWrapper.setName(produktWrapper.getProdukt().getName() + "Wrapper");
		produktWrapperDAO.addItem(produktWrapper);
	}

	@Override
	public void updateProduktWrapper(ProduktWrapper produktWrapper) {
		produktWrapperDAO.updateItem(produktWrapper);
	}

	@Override
	public void deleteProduktWrapper(ProduktWrapper produktWrapper) {
		produktWrapperDAO.deleteItem(produktWrapper);
	}

	@Override
	public ProduktWrapper getProduktWrapperByName(String name) {
		return produktWrapperDAO.getItemByName(name);
	}

	@Override
	public ProduktWrapper getProduktWrapperById(long id) {
		return produktWrapperDAO.getItemById(id);
	}

	@Override
	public List<ProduktWrapper> getProduktWrappers() {
		return produktWrapperDAO.getItems();
	}

	
	
}
