package de.kapsel.um;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User implements Serializable{


	private static final long serialVersionUID = 1L;
	public static final String tname="users";
	private long id;
	private String name;
	private String password;

	@Id
	@GeneratedValue
	@Column(name="id", nullable=false, unique=true, length=11)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name="name", unique=true, length=20, nullable=true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="password", length=20, nullable=true)
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
