package de.kapsel.core.produkt.dao;

import de.kapsel.core.global.dao.AbstractDAO;
import de.kapsel.core.global.dao.IGenericDAO;
import de.kapsel.core.produkt.entities.Material;

public class MaterialDAO extends AbstractDAO<Material> implements IGenericDAO<Material>{

	public MaterialDAO() {
		super(Material.class);
	}

}
