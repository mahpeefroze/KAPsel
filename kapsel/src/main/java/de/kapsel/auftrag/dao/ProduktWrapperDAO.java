package de.kapsel.auftrag.dao;

import de.kapsel.auftrag.entities.ProduktWrapper;
import de.kapsel.global.dao.AbstractDAO;
import de.kapsel.global.dao.IGenericDAO;

public class ProduktWrapperDAO extends AbstractDAO<ProduktWrapper> implements IGenericDAO<ProduktWrapper> {

	//SessionFactory coming form AbstractDAO

	public ProduktWrapperDAO(){
		super(ProduktWrapper.class);
	}


}