package de.kapsel.produkt.services;

import java.util.ArrayList;
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
		Produkt p = produktDAO.getItemById(id);
		Hibernate.initialize(p.getAschritte());
		Hibernate.initialize(p.getBauteile());
		return p;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Produkt> getProdukte() {
		ArrayList<Produkt> nontemps = new ArrayList<Produkt>();
		List<Produkt> produkte = produktDAO.getItems();
		for(Produkt p:produkte){
			if(!p.isTempFlag()){
				nontemps.add(p);
			}
			
		}
		if(!nontemps.isEmpty()){
			return nontemps;
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Produkt> getProdukteWithChildren() {
		return produktDAO.getItemsWithChildren();
	}
	
	@Override
	@Transactional
	public List<Produkt> getTemplates() {
		ArrayList<Produkt> temps = new ArrayList<Produkt>();
		List<Produkt> produkte = produktDAO.getItems();
		for(Produkt p:produkte){
			if(p.isTempFlag()){
				temps.add(p);
			}
		}
		if(!temps.isEmpty()){
			return temps;
		}
		return null;
	}
	
	

}
