package de.kapsel.core.produkt.dao;

import de.kapsel.core.global.dao.AbstractDAO;
import de.kapsel.core.global.dao.IGenericDAO;
import de.kapsel.core.produkt.entities.Bauteil;

public class BauteilDAO extends AbstractDAO<Bauteil> implements IGenericDAO<Bauteil> {

	public BauteilDAO(){
		super(Bauteil.class);
	}

}
