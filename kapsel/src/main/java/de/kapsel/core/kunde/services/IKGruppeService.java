package de.kapsel.core.kunde.services;

import java.util.List;

import de.kapsel.core.kunde.entities.KGruppe;

public interface IKGruppeService {

	void addKGruppe(KGruppe kgruppe);

	void updateKGruppe(KGruppe kgruppe);

	void deleteKGruppe(KGruppe kgruppe);

	KGruppe getKGruppeByName(String name);

	KGruppe getKGruppeById(long id);

	List<KGruppe> getKGruppen();
}
