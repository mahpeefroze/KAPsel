package de.kapsel.core.global.dao;

import de.kapsel.core.global.entities.Adresse;

public class AdresseDAO extends AbstractDAO<Adresse> implements IGenericDAO<Adresse> {

	public AdresseDAO(){
		super(Adresse.class);
	}

}
