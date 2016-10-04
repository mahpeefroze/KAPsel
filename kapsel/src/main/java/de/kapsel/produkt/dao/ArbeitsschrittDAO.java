package de.kapsel.produkt.dao;

import de.kapsel.global.dao.AbstractDAO;
import de.kapsel.produkt.Arbeitsschritt;

public class ArbeitsschrittDAO extends AbstractDAO<Arbeitsschritt> implements IArbeitsschrittDAO{

	public ArbeitsschrittDAO(){
		super(Arbeitsschritt.class);
	}

}
