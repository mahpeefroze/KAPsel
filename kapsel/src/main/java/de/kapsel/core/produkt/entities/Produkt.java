package de.kapsel.core.produkt.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;

import de.kapsel.core.util.ETypes;
import de.kapsel.core.util.entities.AbstractKapselEntity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name="produkte")
public class Produkt extends AbstractKapselEntity implements Serializable, Comparable<Produkt> {

	private static final long serialVersionUID = 1L;
	private long id;
	private long pnr;
	private String name;
	private String text;
	private ETypes.ProduktT typ;
	private int zeit;
	private double preis;
	private boolean tempFlag=false;
	private boolean stdFlag=false;
	private Set<Bauteil> bauteile;
	private Set<Arbeitsschritt> aschritte;
	


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
	
	@Column(name="typ")
	public ETypes.ProduktT getTyp() {
		return typ;
	}
	public void setTyp(ETypes.ProduktT typ) {
		this.typ = typ;
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
	
	//mappedBy represents the field in Bauteil.java which is the counterpart to this bidirectional Relation @OneToMany <Set> bauteile => @ManyToOne produkt
	//..which led to Circular Dependency => removed Produkt from Bauteil, hibernate maps the relation in additional table now
	//fetch = FetchType.LAZY enabled by default for @xToMany mappings
	@OneToMany(orphanRemoval = true)
	@Cascade(CascadeType.ALL)
	public Set<Bauteil> getBauteile() {
		return bauteile;
	}
	public void setBauteile(Set<Bauteil> bauteile) {
		this.bauteile = bauteile;
	}
	
	@OneToMany(orphanRemoval = true)
	@Cascade({CascadeType.ALL})
	public Set<Arbeitsschritt> getAschritte() {
		return aschritte;
	}
	public void setAschritte(Set<Arbeitsschritt> aschritte) {
		this.aschritte = aschritte;
	}
	
	@Column(name="tempFlag")
	public boolean isTempFlag() {
		return tempFlag;
	}
	public void setTempFlag(boolean tempFlag) {
		this.tempFlag = tempFlag;
	}
	
	@Column(name="stdFlag")
	public boolean isStdFlag() {
		return stdFlag;
	}
	public void setStdFlag(boolean stdFlag) {
		this.stdFlag = stdFlag;
	}
	
	//Creates deep copy
	public Produkt createFromTemplate(Produkt template){
		Produkt p = new Produkt();
		p.setbKey(AbstractKapselEntity.generateBKey());
		p.setName(template.getName());
		p.setText(template.getText());
		p.setTyp(template.getTyp());
		p.setZeit(template.getZeit());
		p.setPreis(template.getPreis());
		p.setBauteile(new HashSet<Bauteil>());
		for(Bauteil b:template.getBauteile()){
			p.getBauteile().add(b.createCopy());
		}
		p.setAschritte(new HashSet<Arbeitsschritt>());
		for(Arbeitsschritt a:template.getAschritte()){
			p.getAschritte().add(a.createCopy());
		}
		return p;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Produkt)) return false;
        if (obj == this) return true;
        
        Produkt p = (Produkt) obj;
		
        return new EqualsBuilder().
                append(getbKey(), p.getbKey()).
                isEquals();
	}
	@Override
	public int compareTo(Produkt p) {
		if(p==null){
			return -1;
		}
		return Long.compare(getPnr(), p.getPnr());
	}
	
}
