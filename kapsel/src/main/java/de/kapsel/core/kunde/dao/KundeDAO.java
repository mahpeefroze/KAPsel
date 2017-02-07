package de.kapsel.core.kunde.dao;

import java.util.List;

import de.kapsel.core.kunde.entities.Kunde;
import de.kapsel.core.util.dao.AbstractDAO;
import de.kapsel.core.util.dao.ILazyLoadDAO;

public class KundeDAO extends AbstractDAO<Kunde> implements ILazyLoadDAO<Kunde>{

	public KundeDAO(){
		super(Kunde.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Kunde> getItemsWithChildren() {
		String query="select distinct k"+
				" from Kunde k"+
				" left join fetch k.auftraege";
	
	return getSessionFactory().getCurrentSession().createQuery(query).list();
	}

}
