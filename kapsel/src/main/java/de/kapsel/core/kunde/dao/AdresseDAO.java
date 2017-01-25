package de.kapsel.core.kunde.dao;

import de.kapsel.core.kunde.entities.Adresse;
import de.kapsel.core.util.dao.AbstractDAO;
import de.kapsel.core.util.dao.IGenericDAO;

public class AdresseDAO extends AbstractDAO<Adresse> implements IGenericDAO<Adresse> {

	public AdresseDAO(){
		super(Adresse.class);
	}

}
