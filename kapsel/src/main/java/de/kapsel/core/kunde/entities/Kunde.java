package de.kapsel.core.kunde.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;

import de.kapsel.core.auftrag.entities.Auftrag;
import de.kapsel.core.util.ETypes;
import de.kapsel.core.util.entities.AbstractKapselEntity;

@Entity
@Table(name = "kunden")
public class Kunde extends AbstractKapselEntity implements Serializable, Comparable<Kunde> {

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
	private boolean aktiv;
	private Set<Auftrag> auftraege;


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

	@Column(name="name", unique=false, nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Column(name="apartner", unique=false, nullable=false)
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

	@OneToOne(cascade=CascadeType.ALL, orphanRemoval=true)
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
	
	@Column(name="aktiv", nullable=false)
	public boolean isAktiv() {
		return aktiv;
	}
	public void setAktiv(boolean aktiv) {
		this.aktiv = aktiv;
	}
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "kunde")
	public Set<Auftrag> getAuftraege() {
		return auftraege;
	}
	public void setAuftraege(Set<Auftrag> auftraege) {
		this.auftraege = auftraege;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Kunde)) return false;
        if (obj == this) return true;
        
        Kunde k = (Kunde) obj;
		
        return new EqualsBuilder().
                append(getbKey(), k.getbKey()).
                isEquals();
	}
	@Override
	public int compareTo(Kunde k) {
		if(k==null){
			return -1;
		}
		return Long.compare(getKnr(), k.getKnr());
	}
	
}
