package de.kapsel.core.produkt.dao;

import de.kapsel.core.global.dao.AbstractDAO;
import de.kapsel.core.global.dao.IGenericDAO;
import de.kapsel.core.produkt.entities.Arbeitsschritt;

public class ArbeitsschrittDAO extends AbstractDAO<Arbeitsschritt> implements IGenericDAO<Arbeitsschritt>{

	public ArbeitsschrittDAO(){
		super(Arbeitsschritt.class);
	}

}
