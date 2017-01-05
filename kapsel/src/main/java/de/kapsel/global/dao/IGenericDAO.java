package de.kapsel.global.dao;

import java.io.Serializable;
import java.util.List;

public interface IGenericDAO<T extends Serializable> {

		void addItem(T item);

		void updateItem(T item);

		void deleteItem(T item);

		T getItemByName(String name);

		T getItemById(long id);

		List<T> getItems();
}
