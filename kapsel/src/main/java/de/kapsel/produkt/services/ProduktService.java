package de.kapsel.produkt.services;

import java.util.List;

import javax.faces.bean.ManagedProperty;

import org.springframework.transaction.annotation.Transactional;

import de.kapsel.produkt.Produkt;
import de.kapsel.produkt.dao.IProduktDAO;

public class ProduktService implements IProduktService{

	//Injection AuftragDAO
	@ManagedProperty(value="#{produktDAO}")
	private IProduktDAO produktDAO;

	public IProduktDAO getProduktDAO() {
		return produktDAO;
	}

	public void setProduktDAO(IProduktDAO produktDAO) {
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
