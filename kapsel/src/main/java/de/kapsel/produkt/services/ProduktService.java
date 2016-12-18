package de.kapsel.produkt.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import de.kapsel.global.dao.IGenericDAO;
import de.kapsel.produkt.entities.Produkt;

public class ProduktService implements IProduktService{

	//Injection ProduktDAO
	private IGenericDAO<Produkt> produktDAO;

	public IGenericDAO<Produkt> getProduktDAO() {
		return produktDAO;
	}

	public void setProduktDAO(IGenericDAO<Produkt> produktDAO) {
		this.produktDAO = produktDAO;
	}

	@Override
	@Transactional(readOnly = false)
	public void addProdukt(Produkt produkt) {
		produktDAO.addItem(produkt);
	}

	@Override
	@Transactional(readOnly = false)
	public void updateProdukt(Produkt produkt) {
		produktDAO.updateItem(produkt);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteProdukt(Produkt produkt) {
		produktDAO.deleteItem(produkt);
	}

	@Override
	@Transactional(readOnly = true)
	public Produkt getProduktByName(String name) {
		return produktDAO.getItemByName(name);
	}

	@Override
	@Transactional(readOnly = true)
	public Produkt getProduktById(long id) {
		return produktDAO.getItemById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Produkt> getProdukte() {
		return produktDAO.getItems();
	}

}
