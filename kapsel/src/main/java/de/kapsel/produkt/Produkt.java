package de.kapsel.produkt;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="produkte")
public class Produkt implements Serializable {

	private static final long serialVersionUID = 1L;
	private long id;
	private long pnr;
	private String name;
	private String text;
	private int zeit;
	private double preis;
	private Timestamp erstDatum;
	private Timestamp modDatum;
	private List<Bauteil> bauteile;
	//private ArbeitsSchritt aschritt; <- include when bauteil is ready and working


	@Id
	@GeneratedValue
	@Column(name="id", nullable=false, unique=true)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name="pnr", nullable=false, unique=true)
	public long getPnr() {
		return pnr;
	}
	public void setPnr(long pnr) {
		this.pnr = pnr;
	}
	
	@Column(name="name", nullable=false, unique=true)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="text")
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	@Column(name="zeit")
	public int getZeit() {
		return zeit;
	}
	public void setZeit(int zeit) {
		this.zeit = zeit;
	}
	
	@Column(name="preis")
	public double getPreis() {
		return preis;
	}
	public void setPreis(double preis) {
		this.preis = preis;
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
	//mappedBy represents the field in Bauteil.java which is the counterpart to this bidirectional Relation @OneToMany <Set> bauteile => @ManyToOne produkt
	//..which led to Circular Dependency => removed Produkt from Bauteil, hibernate maps the relation in additional table now
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	public List<Bauteil> getBauteile() {
		return bauteile;
	}
	public void setBauteile(List<Bauteil> bauteile) {
		this.bauteile = bauteile;
	}
	
}
