package de.kapsel.auftrag.dao;

import de.kapsel.auftrag.Auftrag;
import de.kapsel.global.dao.AbstractDAO;

public class AuftragDAO extends AbstractDAO<Auftrag> implements IAuftragDAO {

	//SessionFactory coming form AbstractDAO

	public AuftragDAO(){
		super(Auftrag.class);
	}


}
