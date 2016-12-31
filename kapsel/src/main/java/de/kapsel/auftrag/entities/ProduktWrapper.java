package de.kapsel.auftrag.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import de.kapsel.produkt.entities.Produkt;

@Entity
@Table(name="produktwrapper")
public class ProduktWrapper implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private long id;
	private int position;
	private int stueckzahl;
	private String name;
	private Produkt produkt;
	
	@Id
	@GeneratedValue
	@Column(name="id", nullable=false, unique=true)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name="position")
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	
	@Column(name="stueckzahl")
	public int getStueckzahl() {
		return stueckzahl;
	}
	public void setStueckzahl(int stueckzahl) {
		this.stueckzahl = stueckzahl;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@OneToOne
	public Produkt getProdukt() {
		return produkt;
	}
	public void setProdukt(Produkt produkt) {
		this.produkt = produkt;
	} 
	
	
	
}
