package de.kapsel.produkt.services;

import java.util.List;

import javax.faces.bean.ManagedProperty;

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
	public void addProdukt(Produkt produkt) {
		produktDAO.addItem(produkt);
	}

	@Override
	public void updateProdukt(Produkt produkt) {
		produktDAO.updateItem(produkt);
	}

	@Override
	public void deleteProdukt(Produkt produkt) {
		produktDAO.deleteItem(produkt);
	}

	@Override
	public Produkt getProduktByName(String name) {
		return produktDAO.getItemByName(name);
	}

	@Override
	public Produkt getProduktById(long id) {
		return produktDAO.getItemById(id);
	}

	@Override
	public List<Produkt> getProdukte() {
		return produktDAO.getItems();
	}

}
