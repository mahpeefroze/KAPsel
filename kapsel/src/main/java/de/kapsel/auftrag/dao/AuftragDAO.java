package de.kapsel.auftrag.dao;

import java.util.List;

import de.kapsel.auftrag.entities.Auftrag;
import de.kapsel.global.dao.AbstractDAO;
import de.kapsel.global.dao.ILazyLoadDAO;

public class AuftragDAO extends AbstractDAO<Auftrag> implements ILazyLoadDAO<Auftrag> {

	//SessionFactory coming form AbstractDAO

	public AuftragDAO(){
		super(Auftrag.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Auftrag> getItemsWithChildren() {
		String query="select a"+
					" from Auftrag a"+
					" left join fetch a.produkte p"+
					" left join fetch p.produkt";
		
		return getSessionFactory().getCurrentSession().createQuery(query).list();
	}


}
