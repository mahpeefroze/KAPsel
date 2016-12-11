package de.kapsel.um.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.SelectEvent;
import org.springframework.dao.DataAccessException;

import de.kapsel.um.User;
import de.kapsel.um.services.IUserService;


@ManagedBean
@SessionScoped
public class UserBean implements Serializable{


	 private static final long serialVersionUID = 1L;

	//Service Injection -> Name from spring.xml for value
	@ManagedProperty(value="#{userService}")
	private IUserService userService;

	private User selectedUser;
	private User loginUser;
	private User newUser;
	private List<User> users;
	private long id;
	private String passwordNew;
	private boolean resPassword;
	
	
	@PostConstruct
	public void init(){
		try {
			setUsers(getUserService().getUsers());
			setSelectedUser(getUsers().get(0));
			setResPassword(false);
			setLoginUser(new User());
			setNewUser(new User());
		} catch (DataAccessException e) {
			e.printStackTrace();
		}catch(IndexOutOfBoundsException e){
			System.out.println(e.getMessage() + ": keine Einträge vorhanden");
		}	
	}

	//REGISTER USER
	public String addUser(){

		try {
			getNewUser().setPassword("");
			getUserService().addUser(getNewUser());
			return "success";
		} catch (DataAccessException e) {
			e.printStackTrace();
		}

		return "error";

	}
	
	public void updateUser(User user){
		getUserService().updateUser(user);
	}
	
	public void deleteUser(){
		getUserService().deleteUser(getSelectedUser());
	}
	
	public void loadUser(SelectEvent event) {
		setSelectedUser((User) event.getObject());

    }
	
	
	//LOGIN CONTROL
	public String loginUser(){

		try {
			User dbUser = getUserService().getUserByUsername(getLoginUser().getName());
			if(dbUser!=null){
				String dbPassword=dbUser.getPassword();
				if(dbPassword.equals("")){
					setLoginUser(dbUser);
					setResPassword(true);
				}else if(dbPassword.equals(hashPassword(getLoginUser().getPassword()))){
					setLoginUser(dbUser);
					return "index.xhtml?faces-redirect=true";
				}
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (NullPointerException e){
			e.printStackTrace();
		}

		return "home.xhtml?faces-redirect=true";
	}
	
	public String logoutUser(){
		init();
		return "home.xhtml?faces-redirect=true";
	}
	
	//Hash Password - TODO
	private String hashPassword(String password){
		String hashed = password;
		return hashed;
	}
	
	//Create new password on first login/login after reset
	public void createPassword(){
		getLoginUser().setPassword(hashPassword(getPasswordNew()));
		updateUser(getLoginUser());
		setResPassword(false);
	}
	
	//Reset password in control panel
	public void resetPassword(){
		getSelectedUser().setPassword("");
		updateUser(getSelectedUser());
	}


	//Getter und Setter fur ManagedProperty
	public IUserService getUserService() {
        return userService;
    }

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

	public List<User> getUsers() {
        users = new ArrayList<User>();
        users.addAll(this.userService.getUsers());
        return users;
    }

	public void setUsers(List<User> userList) {
        this.users = userList;
    }

	//Getter und Setter fur xhtml Seite
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

	public User getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(User loginUser) {
		this.loginUser = loginUser;
	}

	public String getPasswordNew() {
		return passwordNew;
	}

	public void setPasswordNew(String passwordNew) {
		this.passwordNew = passwordNew;
	}

	public boolean isResPassword() {
		return resPassword;
	}

	public void setResPassword(boolean resPassword) {
		this.resPassword = resPassword;
	}

	
	



}
