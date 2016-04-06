package de.kapsel.produkt.dao;

import de.kapsel.global.dao.AbstractDAO;
import de.kapsel.produkt.Material;

public class MaterialDAO extends AbstractDAO<Material> implements IMaterialDAO{

	public MaterialDAO() {
		super(Material.class);
	}

}
