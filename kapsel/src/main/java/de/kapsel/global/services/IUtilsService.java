package de.kapsel.global.services;

import java.util.List;

import de.kapsel.global.entities.Utils;

public interface IUtilsService {
	
	public abstract void addUtils(Utils utils);

	public abstract void updateUtils(Utils utils);

	public abstract void deleteUtils(Utils utils);

	public abstract Utils getUtilsByName(String name);

	public abstract Utils getUtilsById(long id);

	public abstract List<Utils> getUtils();
	
}
