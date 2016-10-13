package de.kapsel.produkt.services;

import java.util.List;

import de.kapsel.produkt.entities.Werkzeug;

public interface IWerkzeugService {
	
	public abstract void addWerkzeug(Werkzeug werkzeug);

	public abstract void updateWerkzeug(Werkzeug werkzeug);

	public abstract void deleteWerkzeug(Werkzeug werkzeug);

	public abstract Werkzeug getWerkzeugByName(String name);

	public abstract Werkzeug getWerkzeugById(long id);

	public abstract List<Werkzeug> getWerkzeuge();
}
