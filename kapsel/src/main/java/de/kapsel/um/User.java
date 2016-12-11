package de.kapsel.um;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import de.kapsel.global.ETypes;

@Entity
@Table(name = "users")
public class User implements Serializable{


	private static final long serialVersionUID = 1L;
	private long id;
	private ETypes.UserT role;
	private String username;
	private String name;
	private String password;

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
	
	@Column(name="username", nullable=false, unique=true)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name="name", unique=false, nullable=true)
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

	@Override
	public String toString(){

		String user = "ID: " + getId() + ", Name: " + getName() + ", Password: " + getPassword();

		return user;
	}

}
