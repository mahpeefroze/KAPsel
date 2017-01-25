package de.kapsel.core.produkt.dao;

import de.kapsel.core.global.dao.AbstractDAO;
import de.kapsel.core.global.dao.IGenericDAO;
import de.kapsel.core.produkt.entities.Werkzeug;

public class WerkzeugDAO extends AbstractDAO<Werkzeug> implements IGenericDAO<Werkzeug> {

	public WerkzeugDAO() {
		super(Werkzeug.class);
	}

	

}
