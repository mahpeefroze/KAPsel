package de.kapsel.core.user.services;

import java.util.List;

import de.kapsel.core.user.entities.User;

public interface IUserService {

	public abstract void addUser(User user);

	public abstract void updateUser(User user);

    public abstract void deleteUser(User user);

    public abstract User getUserByUsername(String name);

    public abstract User getUserById(long id);

    public abstract List<User> getUsers();


}
