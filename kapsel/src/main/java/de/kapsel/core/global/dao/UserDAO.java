package de.kapsel.core.global.dao;

import de.kapsel.core.global.entities.User;


public class UserDAO extends AbstractDAO<User> implements IGenericDAO<User> {


	public UserDAO(){

		super(User.class);
	};


}
