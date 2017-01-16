package de.kapsel.global.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import de.kapsel.global.dao.IGenericDAO;
import de.kapsel.global.entities.User;

public class UserService implements IUserService{


	//Injection UserDAO
	private IGenericDAO<User> userDAO;

	public IGenericDAO<User> getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(IGenericDAO<User> userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	@Transactional(readOnly = false)
	public void addUser(User user) {
		userDAO.addItem(user);

	}

	@Override
	@Transactional(readOnly = false)
	public void updateUser(User user) {
		userDAO.updateItem(user);

	}

	@Override
	@Transactional(readOnly = false)
	public void deleteUser(User user) {
		userDAO.deleteItem(user);

	}

	@Override
	@Transactional(readOnly = false)
	public User getUserByUsername(String name){
		return userDAO.getItemByName(name);
	}

	@Override
	@Transactional(readOnly = true)
	public User getUserById(long id) {
		return userDAO.getItemById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> getUsers() {
		 return userDAO.getItems();
	}



}