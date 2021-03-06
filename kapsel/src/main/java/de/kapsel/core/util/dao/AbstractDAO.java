package de.kapsel.core.util.dao;

import java.io.Serializable;
import java.util.List;


import org.hibernate.SessionFactory;

@SuppressWarnings({"unchecked" ,"rawtypes"})
public abstract class AbstractDAO<T extends Serializable>{

	protected SessionFactory sessionFactory;

	//Getter and setter for managed property
	public void setSessionFactory(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory(){
		return this.sessionFactory;
	}

	//Get value of T as String to insert into query, passed from extending class via super()
	private Class<T> tClass;

    public AbstractDAO(Class<T> tClass) {
        this.tClass = tClass;
    }

    //getName() returns full name with package -> thus need to split and pass only last element
    public String getTName(){
    	String name=this.tClass.getName();
    	String[] subs=name.split("\\.");
    	return subs[subs.length-1];
    }


    //Standard CRUD and queries

	public void addItem(T item) {
		sessionFactory.getCurrentSession().persist(item);
	}

	public void updateItem(T item) {
		sessionFactory.getCurrentSession().update(item);
	}

	public void deleteItem(T item) {
		sessionFactory.getCurrentSession().delete(item);
	}

	public T getItemByName(String name) {
		String query="from "+getTName()+" where name=:nameParam";
		List list = sessionFactory.getCurrentSession()
                .createQuery(query)
                .setString("nameParam", name).list();
		if(!list.isEmpty()){
			return (T)list.get(0);
		}
		return null;
	}

	public T getItemById(long id) {

		List list = sessionFactory.getCurrentSession()
                .createQuery("from "+getTName()+" where id=:idParam")
                .setLong("idParam", id).list();
		if(!list.isEmpty()){
			return (T)list.get(0);
		}
		return null;
	}

	public List<T> getItems() {
		List list = sessionFactory.getCurrentSession().createQuery("from "+getTName()).list();
		return list;
	}

}
