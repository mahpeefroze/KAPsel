package de.kapsel.produkt;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="produkte")
public class Produkt implements Serializable {

	private static final long serialVersionUID = 1L;
	private long id;
	private long pnr;
	private String name;
	private Date erstDatum;
	private Date modDatum;
	private Set<Bauteil> bauteile;
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
	
	@Column(name="erst_datum", nullable=true)
	public Date getErstDatum() {
		return erstDatum;
	}
	public void setErstDatum(Date erstDatum) {
		this.erstDatum = erstDatum;
	}
	
	@Column(name="mod_datum", nullable=true)
	public Date getModDatum() {
		return modDatum;
	}
	public void setModDatum(Date modDatum) {
		this.modDatum = modDatum;
	}
	//mappedBy represents the field in Bauteil.java which is the counterpart to this bidirectional Relation @OneToMany <Set> bauteile => @ManyToOne produkt
	//..which led to Circular Dependency => removed Produkt from Bauteil, hibernate maps the relation in additional table now
	@OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	public Set<Bauteil> getBauteile() {
		return bauteile;
	}
	public void setBauteile(Set<Bauteil> bauteile) {
		this.bauteile = bauteile;
	}
	

}
