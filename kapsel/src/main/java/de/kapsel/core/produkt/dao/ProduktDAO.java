package de.kapsel.core.produkt.dao;

import java.util.List;

import de.kapsel.core.produkt.entities.Produkt;
import de.kapsel.core.util.dao.AbstractDAO;
import de.kapsel.core.util.dao.ILazyLoadDAO;

public class ProduktDAO extends AbstractDAO<Produkt> implements ILazyLoadDAO<Produkt> {

	public ProduktDAO(){
		super(Produkt.class);
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<Produkt> getItemsWithChildren() {
		String query="select distinct p"+
				" from Produkt p"+
				" left join fetch p.bauteile b" +
				" left join fetch b.material" +
				" left join fetch p.aschritte a" +
				" left join fetch a.werkzeug";
		return getSessionFactory().getCurrentSession().createQuery(query).list();
	
	}
	
}
