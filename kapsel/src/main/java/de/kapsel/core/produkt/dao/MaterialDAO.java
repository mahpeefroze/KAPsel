package de.kapsel.core.produkt.dao;

import de.kapsel.core.produkt.entities.Material;
import de.kapsel.core.util.dao.AbstractDAO;
import de.kapsel.core.util.dao.IGenericDAO;

public class MaterialDAO extends AbstractDAO<Material> implements IGenericDAO<Material>{

	public MaterialDAO() {
		super(Material.class);
	}

}
