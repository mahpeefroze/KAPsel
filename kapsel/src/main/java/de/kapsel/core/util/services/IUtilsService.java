package de.kapsel.core.util.services;

import java.util.List;

import de.kapsel.core.util.entities.Utils;

public interface IUtilsService {
	
	public abstract void updateUtils(Utils utils);

	public abstract Utils getUtilsByName(String name);

	public abstract Utils getUtilsById(long id);

	public abstract List<Utils> getUtils();
	
}
