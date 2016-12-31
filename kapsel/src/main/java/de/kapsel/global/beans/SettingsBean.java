package de.kapsel.global.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.springframework.dao.DataAccessException;

import de.kapsel.global.entities.User;
import de.kapsel.global.entities.Utils;
import de.kapsel.global.services.IUserService;
import de.kapsel.global.services.IUtilsService;


@ManagedBean
@ViewScoped
public class SettingsBean implements Serializable{


	 private static final long serialVersionUID = 1L;

	//JSF Service Injection
	@ManagedProperty(value="#{userService}")
	private IUserService userService;

	private User selectedUser;
	private User newUser;
	private List<User> users;
	private long id;
	
	@ManagedProperty(value="#{utilsService}")
	private IUtilsService utilsService;
	
	private Utils selectedUtils;
	private HashMap<String, Utils> utilsMap;
	private String[] rabatteCB;
	
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

	//Utils Part
	public IUtilsService getUtilsService() {
		return utilsService;
	}

	public void setUtilsService(IUtilsService utilsService) {
		this.utilsService = utilsService;
	}
	
	public Utils getSelectedUtils() {
		return selectedUtils;
	}

	public void setSelectedUtils(Utils selectedUtils) {
		this.selectedUtils = selectedUtils;
	}

	public HashMap<String, Utils> getUtilsMap() {
		return utilsMap;
	}

	public void setUtilsMap(HashMap<String, Utils> utilsMap) {
		this.utilsMap = utilsMap;
	}

	public String[] getRabatteCB() {
		ArrayList<String> rabatte = new ArrayList<String>();
		if(getUtilsMap()!=null){
			for(String key: getUtilsMap().keySet()){
				if(key.startsWith("Rab") && key.endsWith("S") && getUtilsMap().get(key).getValue()==1){
					rabatte.add(key);
				}
			}
		}
		this.rabatteCB = new String[rabatte.size()];
		for(int i=0; i<rabatte.size(); i++){
			this.rabatteCB[i]=rabatte.get(i);
		}
		
		return this.rabatteCB;
	}

	public void setRabatteCB(String[] rabatteCB) {
		this.rabatteCB = rabatteCB;
		//Set all statuses 0
		setRabattActivity("RabAlleS", 0);
		setRabattActivity("RabOeffS", 0);
		setRabattActivity("RabFiS", 0);
		setRabattActivity("RabPrivS", 0);
		
		for(int i=0; i<this.rabatteCB.length; i++){
			//Set all activated Statuses to 1 
			setRabattActivity(this.rabatteCB[i],1);
		}
	}
	
	private void setRabattActivity(String rabName, int value){
		getUtilsMap().get(rabName).setValue(value);
	}
	
	//endregion




	@PostConstruct
	public void myInit(){
		try {
			setUsers(getUserService().getUsers());
			setSelectedUser(getUsers().get(0));
			resetNewUser();
			setUtilsMap(new HashMap<String, Utils>());
			for(Utils u:getUtilsService().getUtils()){
				getUtilsMap().put(u.getAbbr(), u);
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}catch(IndexOutOfBoundsException e){
			System.out.println(e.getMessage() + ": keine Einträge vorhanden");
		}	
	}

	//region USER Settings
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
	
	//endregion 
	
	//region UTILS Settings
	public void updateUtils(){
		getUtilsService().updateUtils(getSelectedUtils());
	}
	
	public void updateUtilsByType(char type){
		for(Utils u:getUtilsMap().values()){
			if(u.getTyp()!=type){
				continue;
			}
			getUtilsService().updateUtils(u);
		}
	}
	
	public boolean grabRabStatus(String rabName){
		if(getUtilsMap().get(rabName).getValue()==0){
			return false;
		}
		return true;
	}
	
	//endregion UTILS

	
	
	

}
