package de.kapsel.core.produkt.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import de.kapsel.core.global.dao.IGenericDAO;
import de.kapsel.core.produkt.entities.Arbeitsschritt;

public class ArbeitsschrittService implements IArbeitsschrittService{
	
	//Injection BauteilDAO
	private IGenericDAO<Arbeitsschritt> arbeitsschrittDAO;

	public IGenericDAO<Arbeitsschritt> getArbeitsschrittDAO() {
		return arbeitsschrittDAO;
	}

	public void setArbeitsschrittDAO(IGenericDAO<Arbeitsschritt> arbeitsschrittDAO) {
		this.arbeitsschrittDAO = arbeitsschrittDAO;
	}
	
	@Override
	@Transactional(readOnly = false)
	public void addArbeitsschritt(Arbeitsschritt arbeitsschritt) {
		arbeitsschrittDAO.addItem(arbeitsschritt);
	}

	@Override
	@Transactional(readOnly = false)
	public void updateArbeitsschritt(Arbeitsschritt arbeitsschritt) {
		arbeitsschrittDAO.updateItem(arbeitsschritt);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteArbeitsschritt(Arbeitsschritt arbeitsschritt) {
		arbeitsschrittDAO.deleteItem(arbeitsschritt);
	}

	@Override
	@Transactional(readOnly = true)
	public Arbeitsschritt getArbeitsschrittByName(String name) {
		return arbeitsschrittDAO.getItemByName(name);
	}

	@Override
	@Transactional(readOnly = true)
	public Arbeitsschritt getArbeitsschrittById(long id) {
		return arbeitsschrittDAO.getItemById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Arbeitsschritt> getArbeitsschritte() {
		return arbeitsschrittDAO.getItems();
	}
	
	
	
}
