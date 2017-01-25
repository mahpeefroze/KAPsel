package de.kapsel.core.produkt.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;

import de.kapsel.core.global.DTItem;
import de.kapsel.core.global.ETypes;
import de.kapsel.core.global.entities.AbstractKapselEntity;

@Entity
@Table(name="arbeitsschritte")
public class Arbeitsschritt extends AbstractKapselEntity implements Serializable, Comparable<Arbeitsschritt>,  DTItem {

	private static final long serialVersionUID = 1L;
	
	private long id;
	private int position=0;
	private String name="";
	private int zeit=0;
	private String notiz="";
	private ETypes.AschrittT typ;
	private Werkzeug werkzeug;
	
	
	@Id
	@GeneratedValue
	@Column(name="id", nullable=false, unique=true)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name="pos", nullable=false)
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	
	@Column(name="name", nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="zeit", nullable=false)
	public int getZeit() {
		return zeit;
	}
	public void setZeit(int zeit) {
		this.zeit = zeit;
	}
	
	@Column(name="notiz", nullable=true)
	public String getNotiz() {
		return notiz;
	}
	
	public void setNotiz(String notiz) {
		this.notiz = notiz;
	}
	
	@Column(name="typ", nullable=false)
	public ETypes.AschrittT getTyp() {
		return typ;
	}
	public void setTyp(ETypes.AschrittT typ) {
		this.typ = typ;
	}
	
	@ManyToOne
	public Werkzeug getWerkzeug() {
		return werkzeug;
	}
	
	public void setWerkzeug(Werkzeug werkzeug) {
		this.werkzeug = werkzeug;
	}
	
	public Arbeitsschritt createCopy(){
		Arbeitsschritt a = new Arbeitsschritt();
		a.setbKey(AbstractKapselEntity.generateBKey());
		a.setPosition(getPosition());
		a.setName(getName());
		a.setZeit(getZeit());
		a.setNotiz(getNotiz());
		a.setTyp(getTyp());
		a.setWerkzeug(getWerkzeug());
		return a;
	}
	

	@Override
	public int compareTo(Arbeitsschritt aschritt) {
		if(aschritt==null){
			return -1;
		}
		return Integer.compare(getPosition(), aschritt.getPosition());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Arbeitsschritt)) return false;
        if (obj == this) return true;
        
        Arbeitsschritt a = (Arbeitsschritt) obj;
		
        return new EqualsBuilder().
                append(getbKey(), a.getbKey()).
                isEquals();
	}
	
}
