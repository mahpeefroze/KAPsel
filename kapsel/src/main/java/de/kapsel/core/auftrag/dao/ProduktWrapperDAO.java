package de.kapsel.core.auftrag.dao;

import de.kapsel.core.auftrag.entities.ProduktWrapper;
import de.kapsel.core.global.dao.AbstractDAO;
import de.kapsel.core.global.dao.IGenericDAO;

public class ProduktWrapperDAO extends AbstractDAO<ProduktWrapper> implements IGenericDAO<ProduktWrapper> {

	//SessionFactory coming form AbstractDAO

	public ProduktWrapperDAO(){
		super(ProduktWrapper.class);
	}


}