package de.kapsel.core.kunde.dao;

import de.kapsel.core.global.dao.IGenericDAO;
import de.kapsel.core.kunde.entities.KGruppe;

public interface IKGruppeDAO extends IGenericDAO<KGruppe> {

	void deleteKGruppe(KGruppe kgruppe);

}
