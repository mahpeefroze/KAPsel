package de.kapsel.um.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;
import org.springframework.dao.DataAccessException;

import de.kapsel.um.User;
import de.kapsel.um.services.IUserService;


@ManagedBean
@ViewScoped
public class UserBean implements Serializable{


	 private static final long serialVersionUID = 1L;

	//Service Injection -> Name from spring.xml for value
	@ManagedProperty(value="#{userService}")
	private IUserService userService;

	private User selectedUser;
	private User newUser;
	private List<User> users;
	private long id;

	//region Getter und Setter
	public IUserService getUserService() {
        return userService;
    }

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

	public List<User> getUsers() {
        return this.users;
    }

	public void setUsers(List<User> userList) {
        this.users = userList;
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
    
	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}

    
    public User getNewUser() {
		return newUser;
	}

	public void setNewUser(User newUser) {
		this.newUser = newUser;
	}
	//endregion
	
	@PostConstruct
	public void init(){
		try {
			setUsers(getUserService().getUsers());
			setSelectedUser(getUsers().get(0));
			setNewUser(new User());
		} catch (DataAccessException e) {
			e.printStackTrace();
		}catch(IndexOutOfBoundsException e){
			System.out.println(e.getMessage() + ": keine Einträge vorhanden");
		}	
	}

	//REGISTER USER
	public void addUser(){

		try {
			getNewUser().setPassword(getNewUser().getName());
			getUserService().addUser(getNewUser());
		} catch (DataAccessException e) {
			e.printStackTrace();
		}

	}
	
	//Reset password in settings
	public void resetPassword(){
		getSelectedUser().setPassword("");
		updateUser();
	}
	
	public void updateUser(){
		getUserService().updateUser(getSelectedUser());
	}
	
	public void deleteUser(){
		getUserService().deleteUser(getSelectedUser());
	}
	
	public void loadUser(SelectEvent event) {
		setSelectedUser((User) event.getObject());

    }


}
