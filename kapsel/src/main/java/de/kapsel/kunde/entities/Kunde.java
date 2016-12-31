package de.kapsel.kunde.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import de.kapsel.auftrag.entities.Auftrag;
import de.kapsel.global.ETypes;
import de.kapsel.global.entities.Adresse;

@Entity
@Table(name = "kunden")
public class Kunde implements Serializable {

	private static final long serialVersionUID = 1L;
	private long id;
	private long knr;
	private String name;
	private String aPartner;
	private ETypes.KundeT typ;
	private KGruppe gruppe;
	private String text;
	private Adresse adresse;
	private double rabatt;
	private ETypes.KundeStatus status;
	private List<Auftrag> auftraege;


	@Id
	@GeneratedValue
	@Column(name="id", nullable=false, unique=true)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name="knr", nullable=false, unique=true)
	public long getKnr(){
		return knr;
	}
	public void setKnr(long knr){
		this.knr = knr;
	}

	@Column(name="name", unique=false, length=40, nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Column(name="apartner", unique=false, length=20, nullable=false)
	public String getAPartner() {
		return aPartner;
	}
	public void setAPartner(String apartner) {
		this.aPartner = apartner;
	}

	@Column(name="typ", unique=false, nullable=false)
	public ETypes.KundeT getTyp() {
		return typ;
	}
	public void setTyp(ETypes.KundeT typ) {
		this.typ = typ;
	}
	
	@ManyToOne
	public KGruppe getGruppe() {
		return gruppe;
	}
	public void setGruppe(KGruppe gruppe) {
		this.gruppe = gruppe;
	}

	@Column(name="text", nullable=true)
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	@OneToOne(cascade=CascadeType.ALL)
	public Adresse getAdresse() {
		return adresse;
	}
	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}
	
	@Column(name="rabatt", nullable=true)
	public double getRabatt() {
		return rabatt;
	}
	public void setRabatt(double rabatt) {
		this.rabatt = rabatt;
	}
	
	@Column(name="status", nullable=false)
	public ETypes.KundeStatus getStatus() {
		return status;
	}
	public void setStatus(ETypes.KundeStatus status) {
		this.status = status;
	}
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "kunde")
	public List<Auftrag> getAuftraege() {
		return auftraege;
	}
	public void setAuftraege(List<Auftrag> auftraege) {
		this.auftraege = auftraege;
	}
	
	
	
}
