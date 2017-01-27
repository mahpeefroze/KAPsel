package de.kapsel.core.auftrag.dao;

import java.util.List;

import de.kapsel.core.auftrag.entities.Auftrag;
import de.kapsel.core.util.dao.AbstractDAO;
import de.kapsel.core.util.dao.ILazyLoadDAO;

public class AuftragDAO extends AbstractDAO<Auftrag> implements ILazyLoadDAO<Auftrag> {

	//SessionFactory coming form AbstractDAO

	public AuftragDAO(){
		super(Auftrag.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Auftrag> getItemsWithChildren() {
		String query="select distinct a"+
					" from Auftrag a"+
					" left join fetch a.kunde k" +
					" left join fetch k.gruppe" +
					" left join fetch a.bearbeiter" +
					" left join fetch a.dokumente d" +
					" left join fetch a.produkte p"+
					" left join fetch p.produkt";
		
		return getSessionFactory().getCurrentSession().createQuery(query).list();
	}


}
