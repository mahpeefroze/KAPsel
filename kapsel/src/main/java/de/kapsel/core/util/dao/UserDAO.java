package de.kapsel.core.util.dao;

import de.kapsel.core.util.entities.User;


public class UserDAO extends AbstractDAO<User> implements IGenericDAO<User> {


	public UserDAO(){

		super(User.class);
	};


}
