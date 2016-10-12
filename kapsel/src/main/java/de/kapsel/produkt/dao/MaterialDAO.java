package de.kapsel.produkt.dao;

import de.kapsel.global.dao.AbstractDAO;
import de.kapsel.produkt.entities.Material;

public class MaterialDAO extends AbstractDAO<Material> implements IMaterialDAO{

	public MaterialDAO() {
		super(Material.class);
	}

}
