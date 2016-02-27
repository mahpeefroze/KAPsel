package de.kapsel.auftrag.services;

import java.util.List;

import javax.faces.bean.ManagedProperty;

import org.springframework.transaction.annotation.Transactional;

import de.kapsel.auftrag.Auftrag;
import de.kapsel.auftrag.dao.IAuftragDAO;

public class AuftragService implements IAuftragService {

	//Used to manipulate data before passing it to bean/db

	//Injection AuftragDAO
	@ManagedProperty(value="#{auftragDAO}")
	private IAuftragDAO auftragDAO;

	public IAuftragDAO getAuftragDAO() {
		return auftragDAO;
	}

	public void setAuftragDAO(IAuftragDAO auftragDAO) {
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
