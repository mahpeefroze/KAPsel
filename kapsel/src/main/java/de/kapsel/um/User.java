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
	private String fullname;
	private String name;
	private String password;
	private byte[] salt;

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
	
	@Column(name="salt", nullable=false)
	public byte[] getSalt() {
		return salt;
	}

	public void setSalt(byte[] salt) {
		this.salt = salt;
	}

	@Override
	public String toString(){

		String user = "ID: " + getId() + ", Name: " + getName() + ", Password: " + getPassword();

		return user;
	}

}
