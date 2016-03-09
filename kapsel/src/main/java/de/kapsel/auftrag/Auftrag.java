package de.kapsel.auftrag;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.kapsel.kunde.Kunde;


@Entity
@Table(name = "auftraege")
public class Auftrag implements Serializable{

	private static final long serialVersionUID = 1L;
	private long id;
	private long anr;
	private String name;
	private String text;
	private String notizen;
	private Kunde kunde;
	private String status;
	private String typ;
	private Date termin;
	private Date anfang;
	//private Produkt produkt;
	//private Leistung leistung;

	//Annotations at accessor-methods tell hibernate to use them to change variables, so get/set logic is aplied
	//As opposed to annotating the private Variables itself
	//Use wrapper classes for nullable fields as Exception will come when trying to assess null to primitive type e.g. null->int

	@Id
	@GeneratedValue
	@Column(name="id", nullable=false, unique=true, length=11)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name="anr", nullable=false, unique=true, length=11)
	public long getAnr() {
		return anr;
	}

	public void setAnr(long anr) {
		this.anr = anr;
	}


	@Column(name="name", unique=true, length=40, nullable=false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	//ManyToOne -> MANY objects of this class relate to ONE object of the other class (MANY auftraege -> ONE kunde)
	//Cascade.Type.ALL connects internally (hibernate) and adds/deletes combined stuff e.g. removes all auftraege when kunde is removed

	@ManyToOne
	public Kunde getKunde() {
		return this.kunde;
	}

	public void setKunde(Kunde kunde) {
		this.kunde = kunde;
	}

	@Column(name="text", nullable=true)
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Column(name="notes", nullable=true)
	public String getNotizen() {
		return notizen;
	}


	public void setNotizen(String notizen) {
		this.notizen = notizen;
	}



	@Column(name="termin", nullable=false)
	public Date getTermin() {
		return termin;
	}

	public void setTermin(Date termin) {
		this.termin = termin;
	}

	@Column(name="anfang", nullable=false)
	public Date getAnfang() {
		return anfang;
	}

	public void setAnfang(Date anfang) {
		this.anfang = anfang;
	}

	/*
	public Produkt getProdukt() {
		return produkt;
	}

	public void setProdukt(Produkt produkt) {
		this.produkt = produkt;
	}

	public Leistung getLeistung() {
		return leistung;
	}

	public void setLeistung(Leistung leistung) {
		this.leistung = leistung;
	}
	*/

	@Column(name="status", nullable=false)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="typ", nullable=false)
	public String getTyp() {
		return typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

	@Override
	public String toString(){

		String user = "Anr: " + getAnr() + ", Name: " + getName() + ", Kunde: " + getKunde().toString();

		return user;
	}

}
