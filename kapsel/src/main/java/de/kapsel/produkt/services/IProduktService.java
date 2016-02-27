package de.kapsel.produkt.services;

import java.util.List;

import de.kapsel.produkt.Produkt;

public interface IProduktService {

	public abstract void addProdukt(Produkt produkt);

	public abstract void updateProdukt(Produkt produkt);

	public abstract void deleteProdukt(Produkt produkt);

	public abstract Produkt getProduktByName(String name);

	public abstract Produkt getProduktById(long id);

	public abstract List<Produkt> getProdukte();
}
