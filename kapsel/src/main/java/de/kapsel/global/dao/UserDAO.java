package de.kapsel.global.dao;

import de.kapsel.global.entities.User;


public class UserDAO extends AbstractDAO<User> implements IGenericDAO<User> {


	public UserDAO(){

		super(User.class);
	};


}
