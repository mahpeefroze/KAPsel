package de.kapsel.produkt.dao;

import de.kapsel.global.dao.AbstractDAO;
import de.kapsel.produkt.Bauteil;

public class BauteilDAO extends AbstractDAO<Bauteil> implements IBauteilDAO {

	public BauteilDAO(){
		super(Bauteil.class);
	}

}
