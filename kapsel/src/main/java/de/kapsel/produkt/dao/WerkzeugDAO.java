package de.kapsel.produkt.dao;

import de.kapsel.global.dao.AbstractDAO;
import de.kapsel.global.dao.IGenericDAO;
import de.kapsel.produkt.entities.Werkzeug;

public class WerkzeugDAO extends AbstractDAO<Werkzeug> implements IGenericDAO<Werkzeug> {

	public WerkzeugDAO() {
		super(Werkzeug.class);
	}

	

}
