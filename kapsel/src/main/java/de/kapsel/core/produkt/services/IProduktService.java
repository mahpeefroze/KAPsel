package de.kapsel.core.produkt.services;

import java.util.List;

import de.kapsel.core.produkt.entities.Produkt;

public interface IProduktService {

	void addProdukt(Produkt produkt);

	void updateProdukt(Produkt produkt);

	void deleteProdukt(Produkt produkt);

	Produkt getProduktByName(String name);

	Produkt getProduktById(long id);

	List<Produkt> getProdukte();
	
	List<Produkt> getProdukteWithChildren();
	
	List<Produkt> getTemplates();
}
