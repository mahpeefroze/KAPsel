package de.kapsel.kunde.dao;

import de.kapsel.global.dao.AbstractDAO;
import de.kapsel.kunde.entities.Kunde;

public class KundeDAO extends AbstractDAO<Kunde> implements IKundeDAO{

	public KundeDAO(){
		super(Kunde.class);
	}

}
