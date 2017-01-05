package de.kapsel.produkt.services;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;

import de.kapsel.global.dao.ILazyLoadDAO;
import de.kapsel.produkt.entities.Produkt;

public class ProduktService implements IProduktService{

	//Injection ProduktDAO
	private ILazyLoadDAO<Produkt> produktDAO;

	public ILazyLoadDAO<Produkt> getProduktDAO() {
		return produktDAO;
	}

	public void setProduktDAO(ILazyLoadDAO<Produkt> produktDAO) {
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

	@Override
	@Transactional(readOnly = true)
	public List<Produkt> getProdukteWithChildren() {
		List<Produkt> result = produktDAO.getItems();
		for(Produkt p:result){
			Hibernate.initialize(p.getBauteile());
			Hibernate.initialize(p.getAschritte());
		}
		return result;
	}

}
