package de.kapsel.global;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import de.kapsel.um.User;

public class SessionUtils {

	public static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
	}

	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}
	
	public static void setUser(User user){
		getSession().setAttribute("user", user);
	}
	
	public static String getUserName() {
		return ((User) getSession().getAttribute("user")).getName();
	}
	
	public static User getUser(){
		return (User) getSession().getAttribute("user");
	}

	public static String getUserId() {
		HttpSession session = getSession();
		if (session != null)
			return (String) session.getAttribute("userid");
		else
			return null;
	}
	
}
