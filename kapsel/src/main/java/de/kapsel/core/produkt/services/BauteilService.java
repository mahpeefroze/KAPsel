package de.kapsel.core.produkt.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import de.kapsel.core.produkt.entities.Bauteil;
import de.kapsel.core.util.dao.IGenericDAO;

public class BauteilService implements IBauteilService {
	
	
	//Injection BauteilDAO
	private IGenericDAO<Bauteil> bauteilDAO;

	public IGenericDAO<Bauteil> getBauteilDAO() {
		return bauteilDAO;
	}

	public void setBauteilDAO(IGenericDAO<Bauteil> bauteilDAO) {
		this.bauteilDAO = bauteilDAO;
	}
	
	
	@Transactional(readOnly = false)
	public void addBauteil(Bauteil bauteil) {
		bauteilDAO.addItem(bauteil);
	}

	@Override
	@Transactional(readOnly = false)
	public void updateBauteil(Bauteil bauteil) {
		bauteilDAO.updateItem(bauteil);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteBauteil(Bauteil bauteil) {
		bauteilDAO.deleteItem(bauteil);
	}

	@Override
	@Transactional(readOnly = true)
	public Bauteil getBauteilByName(String name) {
		return bauteilDAO.getItemByName(name);
	}

	@Override
	@Transactional(readOnly = true)
	public Bauteil getBauteilById(long id) {
		return bauteilDAO.getItemById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Bauteil> getBauteile() {
		return bauteilDAO.getItems();
	}



}
