package de.kapsel.auftrag.dao;

import de.kapsel.auftrag.entities.Auftrag;
import de.kapsel.global.dao.AbstractDAO;
import de.kapsel.global.dao.IGenericDAO;

public class AuftragDAO extends AbstractDAO<Auftrag> implements IGenericDAO<Auftrag> {

	//SessionFactory coming form AbstractDAO

	public AuftragDAO(){
		super(Auftrag.class);
	}


}
