package de.kapsel.core.auftrag.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import de.kapsel.core.auftrag.entities.KapselDocument;
import de.kapsel.core.global.dao.IGenericDAO;

public class KapselDocumentService implements IKapselDocumentService{
	
	private IGenericDAO<KapselDocument> kapselDocumentDAO;
	
	public IGenericDAO<KapselDocument> getKapselDocumentDAO() {
		return kapselDocumentDAO;
	}

	public void setKapselDocumentDAO(IGenericDAO<KapselDocument> kapselDocumentDAO) {
		this.kapselDocumentDAO = kapselDocumentDAO;
	}
	
	
	@Transactional
	public void addKapselDocument(KapselDocument kapselDocument) {
		kapselDocumentDAO.addItem(kapselDocument);
	}

	@Transactional
	public void updateKapselDocument(KapselDocument kapselDocument) {
		kapselDocumentDAO.updateItem(kapselDocument);
	}

	@Transactional
	public void deleteKapselDocument(KapselDocument kapselDocument) {
		kapselDocumentDAO.deleteItem(kapselDocument);
	}

	@Transactional
	public KapselDocument getKapselDocumentByName(String name) {
		return kapselDocumentDAO.getItemByName(name);
	}

	@Transactional
	public KapselDocument getKapselDocumentById(long id) {
		return kapselDocumentDAO.getItemById(id);
	}

	@Transactional
	public List<KapselDocument> getKapselDocument() {
		return kapselDocumentDAO.getItems();
	}


}
