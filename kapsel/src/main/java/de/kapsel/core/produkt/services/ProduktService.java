package de.kapsel.core.produkt.services;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;

import de.kapsel.core.produkt.entities.Produkt;
import de.kapsel.core.util.dao.ILazyLoadDAO;

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
		Produkt result = produktDAO.getItemByName(name);
		Hibernate.initialize(result.getAschritte());
		Hibernate.initialize(result.getBauteile());
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public Produkt getProduktById(long id) {
		Produkt result = produktDAO.getItemById(id);
		Hibernate.initialize(result.getAschritte());
		Hibernate.initialize(result.getBauteile());
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Produkt> getProdukte() {
		return produktDAO.getItems();
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

	@Override
	@Transactional
	public List<Produkt> getNonTemplates() {
		List<Produkt> nontemps = new ArrayList<Produkt>();
		for(Produkt p:produktDAO.getItems()){
			if(!p.isTempFlag()){
				nontemps.add(p);
			}
			
		}
		if(!nontemps.isEmpty()){
			return nontemps;
		}
		return null;
	}
	
	

}
