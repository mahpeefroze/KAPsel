package de.kapsel.produkt.services;

import java.util.List;

import de.kapsel.produkt.entities.Material;

public interface IMaterialService {
	
	public abstract void addMaterial(Material material);

	public abstract void updateMaterial(Material material);

	public abstract void deleteMaterial(Material material);

	public abstract Material getMaterialByName(String name);

	public abstract Material getMaterialById(long id);
	
	public abstract List<Material> getMaterialien();
	
}
