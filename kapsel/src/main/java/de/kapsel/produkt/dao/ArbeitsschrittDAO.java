package de.kapsel.produkt.dao;

import de.kapsel.global.dao.AbstractDAO;
import de.kapsel.global.dao.IGenericDAO;
import de.kapsel.produkt.entities.Arbeitsschritt;

public class ArbeitsschrittDAO extends AbstractDAO<Arbeitsschritt> implements IGenericDAO<Arbeitsschritt>{

	public ArbeitsschrittDAO(){
		super(Arbeitsschritt.class);
	}

}
