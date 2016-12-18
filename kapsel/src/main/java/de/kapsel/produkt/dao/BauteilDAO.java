package de.kapsel.produkt.dao;

import de.kapsel.global.dao.AbstractDAO;
import de.kapsel.global.dao.IGenericDAO;
import de.kapsel.produkt.entities.Bauteil;

public class BauteilDAO extends AbstractDAO<Bauteil> implements IGenericDAO<Bauteil> {

	public BauteilDAO(){
		super(Bauteil.class);
	}

}
