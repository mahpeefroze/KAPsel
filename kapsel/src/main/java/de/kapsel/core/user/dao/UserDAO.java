package de.kapsel.core.user.dao;

import de.kapsel.core.user.entities.User;
import de.kapsel.core.util.dao.AbstractDAO;
import de.kapsel.core.util.dao.IGenericDAO;


public class UserDAO extends AbstractDAO<User> implements IGenericDAO<User> {


	public UserDAO(){

		super(User.class);
	};


}
