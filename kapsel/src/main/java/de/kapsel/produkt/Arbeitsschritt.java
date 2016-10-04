package de.kapsel.produkt;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="aschritte")
public class Arbeitsschritt implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long id;
	private String name;
	private double zeit;
	private String notiz;
	
	
	@Id
	@GeneratedValue
	@Column(name="id", nullable=false, unique=true)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name="name", nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="zeit")
	public double getZeit() {
		return zeit;
	}
	public void setZeit(double zeit) {
		this.zeit = zeit;
	}
	
	@Column(name="notiz")
	public String getNotiz() {
		return notiz;
	}
	
	public void setNotiz(String notiz) {
		this.notiz = notiz;
	}
	
	
	
}
