package de.kapsel.kunde.services;

import java.util.List;

import de.kapsel.kunde.entities.KGruppe;

public interface IKGruppeService {

	void addKGruppe(KGruppe kgruppe);

	void updateKGruppe(KGruppe kgruppe);

	void deleteKGruppe(KGruppe kgruppe);

	KGruppe getKGruppeByName(String name);

	KGruppe getKGruppeById(long id);

	List<KGruppe> getKGruppen();
}
