package de.kapsel.kunde.services;

import java.util.List;

import de.kapsel.kunde.entities.Kunde;


public interface IKundeService {

	public abstract void addKunde(Kunde kunde);

	public abstract void updateKunde(Kunde kunde);

	public abstract void deleteKunde(Kunde kunde);

	public abstract Kunde getKundeByName(String name);

	public abstract Kunde getKundeById(long id);

	public abstract List<Kunde> getKunden();

}
