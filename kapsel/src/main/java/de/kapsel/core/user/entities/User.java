package de.kapsel.core.user.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;

import de.kapsel.core.util.ETypes;

@Entity
@Table(name = "users")
public class User implements Serializable{


	private static final long serialVersionUID = 1L;
	private long id;
	private ETypes.UserT role;
	private String fullname;
	private String name;
	private String password;
	private byte[] salt;
	private boolean resPw=false;

	@Id
	@GeneratedValue
	@Column(name="id", nullable=false, unique=true)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name="role", nullable=false, unique=false)
	public ETypes.UserT getRole() {
		return role;
	}

	public void setRole(ETypes.UserT role) {
		this.role = role;
	}
	
	@Column(name="fullname", nullable=true, unique=false)
	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	@Column(name="name", nullable=false, unique=true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="password", nullable=false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name="salt", nullable=true)
	public byte[] getSalt() {
		return salt;
	}

	public void setSalt(byte[] salt) {
		this.salt = salt;
	}
	
	@Column(name="resPw", nullable=false)
	public boolean isResPw() {
		return resPw;
	}

	public void setResPw(boolean resPw) {
		this.resPw = resPw;
	}

	@Override
	public String toString(){

		String user = "ID: " + getId() + ", Name: " + getName() + ", Role: " + getRole();

		return user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 17;
		result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof User)) return false;
		if (obj == this) return true;

		User u = (User) obj;
		
		return new EqualsBuilder().
                append(u.getName(), getName()).
                isEquals();
	}
	
	

}
