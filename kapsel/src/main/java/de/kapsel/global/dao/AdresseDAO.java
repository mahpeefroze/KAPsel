package de.kapsel.global.dao;

import de.kapsel.global.entities.Adresse;

public class AdresseDAO extends AbstractDAO<Adresse> implements IGenericDAO<Adresse> {

	public AdresseDAO(){
		super(Adresse.class);
	}

}
