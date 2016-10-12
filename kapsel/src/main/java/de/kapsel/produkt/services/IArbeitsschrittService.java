package de.kapsel.produkt.services;

import java.util.List;

import de.kapsel.produkt.entities.Arbeitsschritt;

public interface IArbeitsschrittService{
	
	public abstract void addArbeitsschritt(Arbeitsschritt arbeitsschritt);

	public abstract void updateArbeitsschritt(Arbeitsschritt arbeitsschritt);

	public abstract void deleteArbeitsschritt(Arbeitsschritt arbeitsschritt);

	public abstract Arbeitsschritt getArbeitsschrittByName(String name);

	public abstract Arbeitsschritt getArbeitsschrittById(long id);
	
	public abstract List<Arbeitsschritt> getArbeitsschritte();
}
