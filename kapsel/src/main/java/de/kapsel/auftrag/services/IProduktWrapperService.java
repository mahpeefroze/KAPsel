package de.kapsel.auftrag.services;

import java.util.List;

import de.kapsel.auftrag.entities.ProduktWrapper;

public interface IProduktWrapperService {
	
	public abstract void addProduktWrapper(ProduktWrapper produktWrapper);

	public abstract void updateProduktWrapper(ProduktWrapper produktWrapper);

	public abstract void deleteProduktWrapper(ProduktWrapper produktWrapper);

	public abstract ProduktWrapper getProduktWrapperByName(String name);

	public abstract ProduktWrapper getProduktWrapperById(long id);

	public abstract List<ProduktWrapper> getProduktWrappers();
	
}
