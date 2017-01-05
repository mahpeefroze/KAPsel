package de.kapsel.kunde.services;

import java.util.List;

import de.kapsel.kunde.entities.Kunde;


public interface IKundeService {

	void addKunde(Kunde kunde);

	void updateKunde(Kunde kunde);

	void deleteKunde(Kunde kunde);

	Kunde getKundeByName(String name);

	Kunde getKundeById(long id);

	List<Kunde> getKunden();
	
	List<Kunde> getKundenWithChildren();

}
