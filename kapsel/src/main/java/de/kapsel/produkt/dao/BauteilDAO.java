package de.kapsel.produkt.dao;

import de.kapsel.global.dao.AbstractDAO;
import de.kapsel.produkt.entities.Bauteil;

public class BauteilDAO extends AbstractDAO<Bauteil> implements IBauteilDAO {

	public BauteilDAO(){
		super(Bauteil.class);
	}

}
