package de.kapsel.kunde.services;

import java.util.List;

import de.kapsel.kunde.KGruppe;

public interface IKGruppeService {

	public abstract void addKGruppe(KGruppe kgruppe);

	public abstract void updateKGruppe(KGruppe kgruppe);

	public abstract void deleteKGruppe(KGruppe kgruppe);

	public abstract KGruppe getKGruppeByName(String name);

	public abstract KGruppe getKGruppeById(long id);

	public abstract List<KGruppe> getKGruppen();
}
