package de.kapsel.core.kunde.services;

import java.util.List;

import de.kapsel.core.kunde.entities.Kunde;


public interface IKundeService {

	void addKunde(Kunde kunde);

	void updateKunde(Kunde kunde);

	void deleteKunde(Kunde kunde);

	Kunde getKundeByName(String name);

	Kunde getKundeById(long id);

	List<Kunde> getKunden();
	
	List<Kunde> getKundenWithChildren();

}
