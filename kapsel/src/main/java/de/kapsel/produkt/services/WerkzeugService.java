package de.kapsel.produkt.services;

import java.util.List;

import javax.faces.bean.ManagedProperty;

import org.springframework.transaction.annotation.Transactional;

import de.kapsel.produkt.dao.IWerkzeugDAO;
import de.kapsel.produkt.entities.Werkzeug;

public class WerkzeugService implements IWerkzeugService {
	
	@ManagedProperty(value="#{werkzeugDAO}")
	private IWerkzeugDAO werkzeugDAO;
	
	public IWerkzeugDAO getWerkzeugDAO() {
		return werkzeugDAO;
	}

	public void setWerkzeugDAO(IWerkzeugDAO werkzeugDAO) {
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
