package de.kapsel.produkt;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import de.kapsel.global.ETypes;

@Entity
@Table(name="materialien")
public class Material implements Serializable{

	private static final long serialVersionUID = 1L;
	private long id;
	private String name;
	private double preis;
	private ETypes.UnitT einheit;
	private ETypes.MaterialT typ;
	
	
	@Id
	@GeneratedValue
	@Column(name="id", nullable=false, unique=true)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name="name", nullable=false, unique=true)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="preis", nullable=false)
	public double getPreis() {
		return preis;
	}
	public void setPreis(double preis) {
		this.preis = preis;
	}
	
	@Column(name="einheit", nullable=false)
	public ETypes.UnitT getEinheit() {
		return einheit;
	}
	public void setEinheit(ETypes.UnitT einheit) {
		this.einheit = einheit;
	}
	
	@Column(name="typ", nullable=true)
	public ETypes.MaterialT getTyp() {
		return typ;
	}
	public void setTyp(ETypes.MaterialT typ) {
		this.typ = typ;
	}
	
	
	
}
