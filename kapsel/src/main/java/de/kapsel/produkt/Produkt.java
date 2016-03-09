package de.kapsel.produkt;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
	private Date datum;
	private Set<Bauteil> bauteil;
	//private ArbeitsSchritt aschritt; <- include when bauteil is ready and working


	@Column(name="id", nullable=false, unique=true)
	@GeneratedValue
	@Id
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

	@Column(name="datum", nullable=true)
	public Date getDatum() {
		return datum;
	}
	public void setDatum(Date datum) {
		this.datum = datum;
	}
	
	//mappedBy represents the field in Bauteil.java which is the counterpart to this bidirectional Relation @OneToMany <Set> bauteile => @ManyToOne produkt
	@OneToMany(cascade=CascadeType.ALL, mappedBy="produkt")
	public Set<Bauteil> getBauteil() {
		return bauteil;
	}
	public void setBauteil(Set<Bauteil> bauteil) {
		this.bauteil = bauteil;
	}
	

}
