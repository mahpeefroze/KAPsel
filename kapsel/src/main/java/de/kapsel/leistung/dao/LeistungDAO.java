package de.kapsel.leistung.dao;

import de.kapsel.global.dao.AbstractDAO;
import de.kapsel.leistung.Leistung;

public class LeistungDAO extends AbstractDAO<Leistung> implements ILeistungDAO {

	public LeistungDAO(){
		super(Leistung.class);
	}
}
