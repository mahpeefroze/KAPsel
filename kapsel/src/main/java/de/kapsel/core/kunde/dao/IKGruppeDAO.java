package de.kapsel.core.kunde.dao;

import de.kapsel.core.kunde.entities.KGruppe;
import de.kapsel.core.util.dao.IGenericDAO;

public interface IKGruppeDAO extends IGenericDAO<KGruppe> {

	void unregisterDelete(KGruppe kgruppe);

}
