package de.kapsel.produkt.dao;

import de.kapsel.global.dao.AbstractDAO;
import de.kapsel.global.dao.IGenericDAO;
import de.kapsel.produkt.entities.Produkt;

public class ProduktDAO extends AbstractDAO<Produkt> implements IGenericDAO<Produkt> {

	public ProduktDAO(){
		super(Produkt.class);
	}

}
