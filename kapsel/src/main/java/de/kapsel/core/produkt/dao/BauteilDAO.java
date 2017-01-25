package de.kapsel.core.produkt.dao;

import de.kapsel.core.produkt.entities.Bauteil;
import de.kapsel.core.util.dao.AbstractDAO;
import de.kapsel.core.util.dao.IGenericDAO;

public class BauteilDAO extends AbstractDAO<Bauteil> implements IGenericDAO<Bauteil> {

	public BauteilDAO(){
		super(Bauteil.class);
	}

}
