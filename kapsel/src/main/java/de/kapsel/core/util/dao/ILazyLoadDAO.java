package de.kapsel.core.util.dao;

import java.io.Serializable;
import java.util.List;

public interface ILazyLoadDAO<T extends Serializable> extends IGenericDAO<T> {

	List<T> getItemsWithChildren();
	
}
