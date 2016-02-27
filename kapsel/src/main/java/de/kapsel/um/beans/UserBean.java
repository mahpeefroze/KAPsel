package de.kapsel.um.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.dao.DataAccessException;

import de.kapsel.um.User;
import de.kapsel.um.services.IUserService;


@ManagedBean
public class UserBean implements Serializable{


	 private static final long serialVersionUID = 1L;

	//Service Injection -> Name from spring.xml for value
	@ManagedProperty(value="#{userService}")
	private IUserService userService;


	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	private String password;
	@OneToMany
	private List<User> userList;


	//REGISTER USER
	public String addUser(){

		try {
			User user = new User();
			user.setName(getName());
			user.setPassword(hashPassword());
			getUserService().addUser(user);
			return "success";
		} catch (DataAccessException e) {
			e.printStackTrace();
		}

		return "error";

	}

	//LOGIN CONTROL
	public String loginUser(){

		try {
			User user = new User();
			User dbuser = new User();
			user.setName(getName());
			user.setPassword(hashPassword());
			dbuser = getUserService().getUserByUsername(user);
			if(dbuser!=null){
				if(dbuser.getPassword().equals(hashPassword())){
					return "success";
				}else{
					return "error";
				}
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}

		return "error";
	}


	//Reset Input Fields
	public void reset() {
        this.setName("");
        this.setPassword("");
    }

	public List<User> getUserList() {
        userList = new ArrayList<User>();
        userList.addAll(this.userService.getUsers());
        return userList;
    }

	public void setUserList(List<User> userList) {
        this.userList = userList;
    }


	//Hash Password - TODO
	private String hashPassword(){
		String hashed = getPassword();
		return hashed;
	}




	//Getter und Setter fur ManagedProperty
	public IUserService getUserService() {
        return userService;
    }

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }






	//Getter und Setter fur xhtml Seite
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}





}
