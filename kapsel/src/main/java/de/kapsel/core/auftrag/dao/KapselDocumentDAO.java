package de.kapsel.core.auftrag.dao;

import de.kapsel.core.auftrag.entities.KapselDocument;
import de.kapsel.core.util.dao.AbstractDAO;
import de.kapsel.core.util.dao.IGenericDAO;

public class KapselDocumentDAO extends AbstractDAO<KapselDocument> implements IGenericDAO<KapselDocument>{

	public KapselDocumentDAO() {
		super(KapselDocument.class);
	}


}
