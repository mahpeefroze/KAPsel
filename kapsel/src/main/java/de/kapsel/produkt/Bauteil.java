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
	private String name;
	private int menge;
	private double laenge;
	private double breite;
	private double dicke;
	private String bemerkung;
	private String werkstoff;
	private Produkt produkt;
	
	
	@Column(name="id", nullable=false, unique=true)
	@GeneratedValue
	@Id
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
	
	@Column(name="werkstoff")
	public String getWerkstoff() {
		return werkstoff;
	}
	public void setWerkstoff(String werkstoff) {
		this.werkstoff = werkstoff;
	}
	
	@ManyToOne
	public Produkt getProdukt() {
		return produkt;
	}
	public void setProdukt(Produkt produkt) {
		this.produkt = produkt;
	}
	
	
	
}
