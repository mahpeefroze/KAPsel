package de.kapsel.produkt.services;

import java.util.List;

import javax.faces.bean.ManagedProperty;

import org.springframework.transaction.annotation.Transactional;

import de.kapsel.produkt.Bauteil;
import de.kapsel.produkt.dao.IBauteilDAO;

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
	public void addBauteil(Bauteil produkt) {
		bauteilDAO.addItem(produkt);
	}

	@Override
	@Transactional(readOnly = false)
	public void updateBauteil(Bauteil produkt) {
		bauteilDAO.updateItem(produkt);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteBauteil(Bauteil produkt) {
		bauteilDAO.deleteItem(produkt);
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
