package de.kapsel.produkt.dao;

import java.util.List;

import de.kapsel.global.dao.AbstractDAO;
import de.kapsel.global.dao.ILazyLoadDAO;
import de.kapsel.produkt.entities.Produkt;

public class ProduktDAO extends AbstractDAO<Produkt> implements ILazyLoadDAO<Produkt> {

	public ProduktDAO(){
		super(Produkt.class);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Produkt> getItemsWithChildren() {
		String query="select distinct p"+
				" from Produkt p"+
				" left join fetch p.bauteile";
		List resultP = getSessionFactory().getCurrentSession().createQuery(query).list();
		
		query="select distinct p"+
			" from Produkt p"+
			" left join fetch p.aschritte";
		List resultA = getSessionFactory().getCurrentSession().createQuery(query).list();
		
		for(int i=0; i<resultP.size(); i++){
			if(resultP.get(i).equals(resultA.get(i))){
				((Produkt) resultP.get(i)).setAschritte(((Produkt) resultA.get(i)).getAschritte());
			}
		}
	
	return resultP;
	}
	
}
