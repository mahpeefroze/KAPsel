package de.kapsel.global.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import de.kapsel.global.dao.IGenericDAO;
import de.kapsel.global.entities.Utils;

public class UtilsService implements IUtilsService{
	
	//Injection DAO
	private IGenericDAO<Utils> utilsDAO;
	
	public IGenericDAO<Utils> getUtilsDAO() {
		return utilsDAO;
	}

	public void setUtilsDAO(IGenericDAO<Utils> utilsDAO) {
		this.utilsDAO = utilsDAO;
	}

	@Override
	@Transactional(readOnly = false)
	public void addUtils(Utils utils) {
		utilsDAO.addItem(utils);
	}

	@Override
	@Transactional(readOnly = false)
	public void updateUtils(Utils utils) {
		utilsDAO.updateItem(utils);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteUtils(Utils utils) {
		utilsDAO.deleteItem(utils);
	}

	@Override
	@Transactional(readOnly = true)
	public Utils getUtilsByName(String name) {
		return utilsDAO.getItemByName(name);
	}

	@Override
	@Transactional(readOnly = true)
	public Utils getUtilsById(long id) {
		return utilsDAO.getItemById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Utils> getUtils() {
		return utilsDAO.getItems();
	}

}
