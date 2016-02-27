package de.kapsel.global.dao;

import java.io.Serializable;
import java.util.List;

public interface IGenericDAO<T extends Serializable> {

		public abstract void addItem(T item);

		public abstract void updateItem(T item);

		public abstract void deleteItem(T item);

		public abstract T getItemByName(String name);

		public abstract T getItemById(long id);

		public abstract List<T> getItems();
}
