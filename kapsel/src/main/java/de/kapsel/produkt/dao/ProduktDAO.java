package de.kapsel.produkt.dao;

import java.util.List;

import de.kapsel.global.dao.AbstractDAO;
import de.kapsel.global.dao.ILazyLoadDAO;
import de.kapsel.produkt.entities.Produkt;

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
