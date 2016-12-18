package de.kapsel.global.beans;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.springframework.dao.DataAccessException;
import org.apache.commons.codec.digest.DigestUtils;
import org.primefaces.context.RequestContext;

import de.kapsel.global.ETypes;
import de.kapsel.global.SessionUtils;
import de.kapsel.global.entities.User;
import de.kapsel.global.services.IUserService;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private static final String pwPattern = "^\\w*$";
	private final String success = "/views/secure/index.xhtml?faces-redirect=true";
	private final String error= "/views/home.xhtml?faces-redirect=true";
	
	private User loginUser;
	private String passwordNew;
	private boolean resPassword;
	private boolean logged;
	
	@ManagedProperty(value="#{userService}")
	private IUserService userService;
	
	//region Getter und Setter
	public IUserService getUserService() {
        return userService;
    }

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }
    
    public String getPwPattern(){
    	return pwPattern;
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

	public boolean isLogged() {
		return logged;
	}

	public void setLogged(boolean logged) {
		this.logged = logged;
	}
	
	
	//endregion



	@PostConstruct
	public void init() {
		try {
			setLoginUser(new User());
			clearUser();
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	//ADMIN 0, MOD 1, ADVUSER 2, SYSUSER 3
	public boolean validateAccess(ETypes.UserT role){
		try{
			return getLoginUser().getRole().ordinal()>role.ordinal();
		}catch (NullPointerException e){
			e.printStackTrace();
		}
		return true;
	}
	
	//LOGIN CONTROL
	public String loginUser(){
		
		try {
			User dbUser = getUserService().getUserByUsername(getLoginUser().getName());
			if(dbUser!=null){
				String dbPassword = dbUser.getPassword();
				if(dbPassword.equals(dbUser.getName())){
					setLoginUser(dbUser);
					setResPassword(true);
					return error;
				}
				String userPassword = hashPassword(getLoginUser().getPassword(), dbUser.getSalt());
				if(dbPassword.equals(userPassword)){
					setLoginUser(dbUser);
					setLogged(true);
					//SessionUtils.setLoggedUser(getLoginUser());
					return success;
				}else{
					FacesMessage msg = new FacesMessage("Wrong password.");
					msg.setSeverity(FacesMessage.SEVERITY_ERROR);
					RequestContext.getCurrentInstance().update("loginPanel:password");
					FacesContext.getCurrentInstance().addMessage("loginPanel:password", msg);
				}
			}else{
				FacesMessage msg = new FacesMessage("This username is not registered.");
				msg.setSeverity(FacesMessage.SEVERITY_WARN);
				clearUser();
				RequestContext.getCurrentInstance().update("loginPanel:password");
				FacesContext.getCurrentInstance().addMessage("loginPanel:name", msg);
				return null;
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (NullPointerException e){
			e.printStackTrace();
		}

		return null;
	}
	
	public String logoutUser(){
		SessionUtils.getSession().invalidate();
		clearUser();
		return error;
	}
	
	//Hash Password with SHA-256 + Salt
	private String hashPassword(String passwordToHash, byte[] salt){
		
		String hashedPassword="";
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(salt);
			byte[] hashedBytes = md.digest(passwordToHash.getBytes());
			//Convert bytes to hexString with Spring converter
			hashedPassword=DigestUtils.sha256Hex(hashedBytes);
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return hashedPassword;
	}
	
	private byte[] generateSalt() throws NoSuchAlgorithmException{
		//Always use a SecureRandom generator
	    SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
	    //Create array for salt
	    byte[] salt = new byte[16];
	    //Get a random salt
	    sr.nextBytes(salt);
	    //return salt
	    return salt;
	}
	
	//Create new password on first login/login after reset
	public void createPassword(){
		try {
			byte[] salt = generateSalt();
			getLoginUser().setSalt(salt);
			getLoginUser().setPassword(hashPassword(getPasswordNew(), salt));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		updateUser();
		clearUser();
	}
	
	private void updateUser() {
		getUserService().updateUser(getLoginUser());
	}
	
	public void clearUser(){
		setResPassword(false);
		setLogged(false);
		setPasswordNew(null);
	}
	
}



