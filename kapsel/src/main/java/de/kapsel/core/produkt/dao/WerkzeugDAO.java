package de.kapsel.core.produkt.dao;

import de.kapsel.core.produkt.entities.Werkzeug;
import de.kapsel.core.util.dao.AbstractDAO;
import de.kapsel.core.util.dao.IGenericDAO;

public class WerkzeugDAO extends AbstractDAO<Werkzeug> implements IGenericDAO<Werkzeug> {

	public WerkzeugDAO() {
		super(Werkzeug.class);
	}

	

}
