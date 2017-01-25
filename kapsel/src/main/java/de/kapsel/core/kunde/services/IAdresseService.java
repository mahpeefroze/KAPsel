package de.kapsel.core.kunde.services;

import java.util.List;

import de.kapsel.core.kunde.entities.Adresse;

public interface IAdresseService {

	public abstract void addAdresse(Adresse adresse);

	public abstract void updateAdresse(Adresse adresse);

	public abstract void deleteAdresse(Adresse adresse);

	public abstract Adresse getAdresseByName(String name);

	public abstract Adresse getAdresseById(long id);

	public abstract List<Adresse> getAdressen();

}
