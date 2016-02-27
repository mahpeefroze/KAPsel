package de.kapsel.produkt.dao;

import de.kapsel.global.dao.AbstractDAO;
import de.kapsel.produkt.Produkt;

public class ProduktDAO extends AbstractDAO<Produkt> implements IProduktDAO {

	public ProduktDAO(){
		super(Produkt.class);
	}

}
