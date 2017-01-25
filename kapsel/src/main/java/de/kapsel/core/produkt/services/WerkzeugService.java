package de.kapsel.core.produkt.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import de.kapsel.core.global.dao.IGenericDAO;
import de.kapsel.core.produkt.entities.Werkzeug;

public class WerkzeugService implements IWerkzeugService {
	
	private IGenericDAO<Werkzeug> werkzeugDAO;
	
	public IGenericDAO<Werkzeug> getWerkzeugDAO() {
		return werkzeugDAO;
	}

	public void setWerkzeugDAO(IGenericDAO<Werkzeug> werkzeugDAO) {
		this.werkzeugDAO = werkzeugDAO;
	}

	
	@Override
	@Transactional(readOnly = false)
	public void addWerkzeug(Werkzeug werkzeug) {
		werkzeugDAO.addItem(werkzeug);
	}

	@Override
	@Transactional(readOnly = false)
	public void updateWerkzeug(Werkzeug werkzeug) {
		werkzeugDAO.updateItem(werkzeug);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteWerkzeug(Werkzeug werkzeug) {
		werkzeugDAO.deleteItem(werkzeug);
	}

	@Override
	@Transactional(readOnly = true)
	public Werkzeug getWerkzeugByName(String name) {
		return werkzeugDAO.getItemByName(name);
	}

	@Override
	@Transactional(readOnly = true)
	public Werkzeug getWerkzeugById(long id) {
		return werkzeugDAO.getItemById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Werkzeug> getWerkzeuge() {
		return werkzeugDAO.getItems();
	}

}
