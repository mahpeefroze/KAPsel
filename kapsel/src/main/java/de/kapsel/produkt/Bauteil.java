package de.kapsel.produkt;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="bauteile")
public class Bauteil implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private long id;
	private int position=0;
	private String name="";
	private int menge=1;
	private double laenge=0;
	private double breite=0;
	private double dicke=0;
	private String bemerkung="";
	private Material material;

	
	@Id
	@GeneratedValue
	@Column(name="id", nullable=false, unique=true)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name="pos", nullable=false)
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	
	@Column(name="name", nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="menge", nullable=false)
	public int getMenge() {
		return menge;
	}
	public void setMenge(int menge) {
		this.menge = menge;
	}
	
	@Column(name="laenge", nullable=true)
	public double getLaenge() {
		return laenge;
	}
	public void setLaenge(double laenge) {
		this.laenge = laenge;
	}
	
	@Column(name="breite", nullable=true)
	public double getBreite() {
		return breite;
	}
	public void setBreite(double breite) {
		this.breite = breite;
	}
	
	@Column(name="dicke", nullable=true) 
	public double getDicke() {
		return dicke;
	}
	public void setDicke(double dicke) {
		this.dicke = dicke;
	}
	
	@Column(name="bemerkung",nullable=true)
	public String getBemerkung() {
		return bemerkung;
	}
	public void setBemerkung(String bemerkung) {
		this.bemerkung = bemerkung;
	}
	
	@ManyToOne
	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
	}
	
	
}
