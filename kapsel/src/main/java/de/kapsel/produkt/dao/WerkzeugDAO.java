package de.kapsel.produkt.dao;

import de.kapsel.global.dao.AbstractDAO;
import de.kapsel.produkt.entities.Werkzeug;

public class WerkzeugDAO extends AbstractDAO<Werkzeug> implements IWerkzeugDAO {

	public WerkzeugDAO() {
		super(Werkzeug.class);
	}

	

}
