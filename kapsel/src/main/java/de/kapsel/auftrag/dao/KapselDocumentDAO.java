package de.kapsel.auftrag.dao;

import de.kapsel.auftrag.entities.KapselDocument;
import de.kapsel.global.dao.AbstractDAO;
import de.kapsel.global.dao.IGenericDAO;

public class KapselDocumentDAO extends AbstractDAO<KapselDocument> implements IGenericDAO<KapselDocument>{

	public KapselDocumentDAO() {
		super(KapselDocument.class);
	}


}
