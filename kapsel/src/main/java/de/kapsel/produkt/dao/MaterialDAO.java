package de.kapsel.produkt.dao;

import de.kapsel.global.dao.AbstractDAO;
import de.kapsel.global.dao.IGenericDAO;
import de.kapsel.produkt.entities.Material;

public class MaterialDAO extends AbstractDAO<Material> implements IGenericDAO<Material>{

	public MaterialDAO() {
		super(Material.class);
	}

}
