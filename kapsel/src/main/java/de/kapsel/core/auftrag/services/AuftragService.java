package de.kapsel.core.auftrag.services;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;

import de.kapsel.core.auftrag.entities.Auftrag;
import de.kapsel.core.global.dao.ILazyLoadDAO;

public class AuftragService implements IAuftragService {

	//Used to manipulate data before passing it to bean/db

	//Injection AuftragDAO
	private ILazyLoadDAO<Auftrag> auftragDAO;

	public ILazyLoadDAO<Auftrag> getAuftragDAO() {
		return auftragDAO;
	}

	public void setAuftragDAO(ILazyLoadDAO<Auftrag> auftragDAO) {
		this.auftragDAO = auftragDAO;
	}
	

	@Override
	@Transactional(readOnly = false)
	public void addAuftrag(Auftrag auftrag) {
		auftragDAO.addItem(auftrag);
	}

	@Override
	@Transactional(readOnly = false)
	public void updateAuftrag(Auftrag auftrag) {
		auftragDAO.updateItem(auftrag);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteAuftrag(Auftrag auftrag) {
		auftragDAO.deleteItem(auftrag);
	}

	@Override
	@Transactional(readOnly = true)
	public Auftrag getAuftragByName(String name) {
		return auftragDAO.getItemByName(name);
	}

	@Override
	@Transactional(readOnly = true)
	public Auftrag getAuftragById(long id) {
		Auftrag result = auftragDAO.getItemById(id);
		Hibernate.initialize(result.getProdukte());
		Hibernate.initialize(result.getDokumente());
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Auftrag> getAuftraege() {
		return auftragDAO.getItems();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Auftrag> getAuftraegeWithChildren() {
		return auftragDAO.getItemsWithChildren();
	}

}
