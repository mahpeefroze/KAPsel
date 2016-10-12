package de.kapsel.kunde.dao;

import de.kapsel.global.dao.AbstractDAO;
import de.kapsel.kunde.entities.KGruppe;

public class KGruppeDAO extends AbstractDAO<KGruppe> implements IKGruppeDAO {

	public KGruppeDAO(){
		super(KGruppe.class);
	}

}
