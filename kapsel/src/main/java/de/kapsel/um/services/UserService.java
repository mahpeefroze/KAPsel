package de.kapsel.um.services;

import java.util.List;

import javax.faces.bean.ManagedProperty;

import org.springframework.transaction.annotation.Transactional;

import de.kapsel.um.User;
import de.kapsel.um.dao.IUserDAO;

public class UserService implements IUserService{


	//Injection UserDAO
	@ManagedProperty(value="#{userDAO}")
	private IUserDAO userDAO;

	public IUserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(IUserDAO userDAO) {
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
	public User getUserByUsername(User user){
		return userDAO.getItemByName(user.getName());
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
