package de.kapsel.core.auftrag.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import de.kapsel.core.auftrag.entities.KapselDocument;
import de.kapsel.core.util.dao.IGenericDAO;

public class KapselDocumentService implements IKapselDocumentService{
	
	private IGenericDAO<KapselDocument> kapselDocumentDAO;
	
	public IGenericDAO<KapselDocument> getKapselDocumentDAO() {
		return kapselDocumentDAO;
	}

	public void setKapselDocumentDAO(IGenericDAO<KapselDocument> kapselDocumentDAO) {
		this.kapselDocumentDAO = kapselDocumentDAO;
	}
	
	
	@Transactional(readOnly = false)
	public void addKapselDocument(KapselDocument kapselDocument) {
		kapselDocumentDAO.addItem(kapselDocument);
	}

	@Transactional(readOnly = false)
	public void updateKapselDocument(KapselDocument kapselDocument) {
		kapselDocumentDAO.updateItem(kapselDocument);
	}

	@Transactional(readOnly = false)
	public void deleteKapselDocument(KapselDocument kapselDocument) {
		kapselDocumentDAO.deleteItem(kapselDocument);
	}

	@Transactional(readOnly = true)
	public KapselDocument getKapselDocumentByName(String name) {
		return kapselDocumentDAO.getItemByName(name);
	}

	@Transactional(readOnly = true)
	public KapselDocument getKapselDocumentById(long id) {
		return kapselDocumentDAO.getItemById(id);
	}

	@Transactional(readOnly = true)
	public List<KapselDocument> getKapselDocument() {
		return kapselDocumentDAO.getItems();
	}


}
