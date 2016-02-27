package de.kapsel.global;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="adressen")
public class Adresse implements Serializable{

	private static final long serialVersionUID = 1L;

	private long id;
	private String strasse;
	private String hausnr;
	private String plz;
	private String stadt;
	private String land;
	private String email;
	private long telefon;
	private long mobil;
	private String web;

	@Id
	@GeneratedValue
	@Column(name="id", nullable=false, unique=true)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	@Column(name="strasse", nullable=false)
	public String getStrasse() {
		return strasse;
	}
	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	@Column(name="hausnr", nullable=false)
	public String getHausnr() {
		return hausnr;
	}
	public void setHausnr(String hausnr) {
		this.hausnr = hausnr;
	}

	@Column(name="plz", nullable=false)
	public String getPlz() {
		return plz;
	}
	public void setPlz(String plz) {
		this.plz = plz;
	}

	@Column(name="stadt", nullable=false)
	public String getStadt() {
		return stadt;
	}
	public void setStadt(String stadt) {
		this.stadt = stadt;
	}

	@Column(name="land", nullable=false)
	public String getLand() {
		return land;
	}
	public void setLand(String land) {
		this.land = land;
	}

	@Column(name="email", nullable=true)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name="telefon", nullable=true)
	public long getTelefon() {
		return telefon;
	}
	public void setTelefon(long telefon) {
		this.telefon = telefon;
	}

	@Column(name="mobil", nullable=true)
	public long getMobil() {
		return mobil;
	}
	public void setMobil(long mobil) {
		this.mobil = mobil;
	}
	
	@Column(name="web", nullable=true)
	public String getWeb() {
		return web;
	}
	public void setWeb(String web) {
		this.web = web;
	}

	


}
