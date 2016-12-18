package de.kapsel.global;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import de.kapsel.global.entities.User;

public class SessionUtils {

	public static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
	}

	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}
	
	public static void setLoggedUser(User user){
		getSession().setAttribute("loggedUser", user);
	}
	
	public static User getLoggedUser(){
		return (User) getSession().getAttribute("loggedUser");
	}
	
}
