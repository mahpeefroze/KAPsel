package de.kapsel.produkt.services;

import java.util.List;

import javax.faces.bean.ManagedProperty;

import org.springframework.transaction.annotation.Transactional;

import de.kapsel.produkt.dao.IBauteilDAO;
import de.kapsel.produkt.entities.Bauteil;

public class BauteilService implements IBauteilService {
	
	
	//Injection BauteilDAO
	@ManagedProperty(value="#{bauteilDAO}")
	private IBauteilDAO bauteilDAO;

	public IBauteilDAO getBauteilDAO() {
		return bauteilDAO;
	}

	public void setBauteilDAO(IBauteilDAO bauteilDAO) {
		this.bauteilDAO = bauteilDAO;
	}
	
	
	@Override
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
