package de.kapsel.core.produkt.dao;

import de.kapsel.core.produkt.entities.Arbeitsschritt;
import de.kapsel.core.util.dao.AbstractDAO;
import de.kapsel.core.util.dao.IGenericDAO;

public class ArbeitsschrittDAO extends AbstractDAO<Arbeitsschritt> implements IGenericDAO<Arbeitsschritt>{

	public ArbeitsschrittDAO(){
		super(Arbeitsschritt.class);
	}

}
