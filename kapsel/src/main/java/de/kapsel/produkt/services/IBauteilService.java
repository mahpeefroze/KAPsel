package de.kapsel.produkt.services;

import java.util.List;

import de.kapsel.produkt.Bauteil;

public interface IBauteilService {
	
	public abstract void addBauteil(Bauteil bauteil);

	public abstract void updateBauteil(Bauteil bauteil);

	public abstract void deleteBauteil(Bauteil bauteil);

	public abstract Bauteil getBauteilByName(String name);

	public abstract Bauteil getBauteilById(long id);
	
	public abstract List<Bauteil> getBauteile();
}
