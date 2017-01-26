package de.kapsel.core.user.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.springframework.dao.DataAccessException;

import de.kapsel.core.user.entities.User;
import de.kapsel.core.user.services.IUserService;


@ManagedBean
@ViewScoped
public class UserBean implements Serializable{


	 private static final long serialVersionUID = 1L;

	//JSF Service Injection
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
	public void myInit(){
		try {
			setUsers(getUserService().getUsers());
			setSelectedUser(getUsers().get(0));
			
		} catch (DataAccessException e) {
			e.printStackTrace();
		}catch(IndexOutOfBoundsException e){
			System.out.println(e.getMessage() + ": keine User vorhanden");
		}
		resetNewUser();
	}

	public void addUser(){

		try {
			getNewUser().setPassword("");
			getNewUser().setResPw(true);
			getUserService().addUser(getNewUser());
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		myInit();
	}
	
	public void onUserEdit(User u){
		setSelectedUser(u);
		updateUser();
	}
	
	public void resetNewUser(){
		setNewUser(new User());
	}
	
	public void resetPassword(User u){
		u.setPassword(u.getName());
		u.setResPw(true);
		getUserService().updateUser(u);
	}
	
	public void updateUser(User u){
		getUserService().updateUser(u);
	}
	
	public void updateUser(){
		getUserService().updateUser(getSelectedUser());
	}
		
	public void deleteUser(){
		getUserService().deleteUser(getSelectedUser());
		myInit();
	}
	
	


	
	
	

}
