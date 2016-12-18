package de.kapsel.auftrag.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import de.kapsel.auftrag.entities.Auftrag;
import de.kapsel.global.dao.IGenericDAO;

public class AuftragService implements IAuftragService {

	//Used to manipulate data before passing it to bean/db

	//Injection AuftragDAO
	private IGenericDAO<Auftrag> auftragDAO;

	public IGenericDAO<Auftrag> getAuftragDAO() {
		return auftragDAO;
	}

	public void setAuftragDAO(IGenericDAO<Auftrag> auftragDAO) {
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
		return auftragDAO.getItemById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Auftrag> getAuftraege() {
		return auftragDAO.getItems();
	}

}
