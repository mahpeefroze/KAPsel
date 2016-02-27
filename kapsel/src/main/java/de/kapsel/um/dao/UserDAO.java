package de.kapsel.um.dao;

import de.kapsel.global.dao.AbstractDAO;
import de.kapsel.um.User;


public class UserDAO extends AbstractDAO<User> implements IUserDAO {


	public UserDAO(){

		super(User.class);
	};


}
