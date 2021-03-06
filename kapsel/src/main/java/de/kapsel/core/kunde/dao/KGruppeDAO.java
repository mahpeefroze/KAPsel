package de.kapsel.core.kunde.dao;

import java.util.List;

import de.kapsel.core.kunde.entities.KGruppe;
import de.kapsel.core.kunde.entities.Kunde;
import de.kapsel.core.util.dao.AbstractDAO;

public class KGruppeDAO extends AbstractDAO<KGruppe> implements IKGruppeDAO {

	public KGruppeDAO(){
		super(KGruppe.class);
	}
	
	public void unregisterDelete(KGruppe kgruppe){
		System.out.println("Want to delete kGruppe from Kunde...");
		@SuppressWarnings("unchecked")
		List<Kunde> list = sessionFactory.getCurrentSession().createQuery("from Kunde").list();
		for(Kunde k:list){
			if(k.getGruppe()!=null && k.getGruppe().getId()==kgruppe.getId()){
				k.setGruppe(null);
				sessionFactory.getCurrentSession().update(k);
			}
		}
		//KGruppe obtained once to fill DT isn't equal to the one passed by the method -> hibernate uses object equality and not equals method
		//So 2 different objects but with the same primarykey
		deleteItem((KGruppe)sessionFactory.getCurrentSession().merge(kgruppe));
	}

}
