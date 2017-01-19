package de.kapsel.auftrag.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import de.kapsel.global.ETypes;
import de.kapsel.global.entities.User;
import de.kapsel.kunde.entities.Kunde;


@Entity
@Table(name = "auftraege")
public class Auftrag implements Serializable, Comparable<Auftrag>{

	private static final long serialVersionUID = 1L;
	private long id;
	private long anr;
	private String name;
	private String text;
	private Kunde kunde;
	private User bearbeiter;
	private ETypes.AuftragS status;
	private int zeit;
	private double preis;
	private Date sollenddatum;
	private Date startdatum;
	private Date enddatum;
	private Timestamp erstDatum;
	private Timestamp modDatum;
	private Set<ProduktWrapper> produkte;
	

	

	//Annotations at accessor-methods tell hibernate to use them to change variables, so get/set logic is aplied
	//As opposed to annotating the private Variables itself
	//Use wrapper classes for nullable fields as Exception will come when trying to assess null to primitive type e.g. null->int

	@Id
	@GeneratedValue
	@Column(name="id", nullable=false, unique=true)
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
	
	@Column(name="text", nullable=true)
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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
	
	@ManyToOne
	public User getBearbeiter() {
		return bearbeiter;
	}

	public void setBearbeiter(User bearbeiter) {
		this.bearbeiter = bearbeiter;
	}

	@Column(name="status", nullable=false)
	public ETypes.AuftragS getStatus() {
		return status;
	}

	public void setStatus(ETypes.AuftragS status) {
		this.status = status;
	}
	
	@Column(name="zeit", nullable=false)
	public int getZeit() {
		return zeit;
	}

	public void setZeit(int zeit) {
		this.zeit = zeit;
	}
	
	@Column(name="preis", nullable=false)
	public double getPreis() {
		return preis;
	}

	public void setPreis(double preis) {
		this.preis = preis;
	}
	
	@Column(name="soll_enddatum")
	public Date getSollenddatum() {
		return sollenddatum;
	}

	public void setSollenddatum(Date sollenddatum) {
		this.sollenddatum = sollenddatum;
	}
	
	@Column(name="startdatum")
	public Date getStartdatum() {
		return startdatum;
	}

	public void setStartdatum(Date startdatum) {
		this.startdatum = startdatum;
	}
	
	@Column(name="enddatum")
	public Date getEnddatum() {
		return enddatum;
	}

	public void setEnddatum(Date enddatum) {
		this.enddatum = enddatum;
	}

	@CreationTimestamp
	@Column(name="erst_datum", nullable=false)
	public Timestamp getErstDatum() {
		return erstDatum;
	}

	public void setErstDatum(Timestamp erstDatum) {
		this.erstDatum = erstDatum;
	}
	
	@UpdateTimestamp
	@Column(name="mod_datum", nullable=false)
	public Timestamp getModDatum() {
		return modDatum;
	}

	public void setModDatum(Timestamp modDatum) {
		this.modDatum = modDatum;
	}
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	public Set<ProduktWrapper> getProdukte() {
		return produkte;
	}

	public void setProdukte(Set<ProduktWrapper> produkte) {
		this.produkte = produkte;
	}


	@Override
	public int compareTo(Auftrag a) {
		if(a==null){
			return -1;
		}
		return Long.compare(getAnr(), a.getAnr());
	}

}
