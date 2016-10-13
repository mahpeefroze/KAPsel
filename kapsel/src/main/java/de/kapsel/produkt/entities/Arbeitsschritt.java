package de.kapsel.produkt.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.kapsel.global.DTItem;
import de.kapsel.global.ETypes;

@Entity
@Table(name="arbeitsschritte")
public class Arbeitsschritt implements DTItem, Serializable, Comparable<Arbeitsschritt> {

	private static final long serialVersionUID = 1L;
	
	private long id;
	private int position=0;
	private String name="";
	private double zeit=0;
	private String notiz="";
	private ETypes.ASchrittT typ;
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
	
	@Column(name="zeit")
	public double getZeit() {
		return zeit;
	}
	public void setZeit(double zeit) {
		this.zeit = zeit;
	}
	
	@Column(name="notiz")
	public String getNotiz() {
		return notiz;
	}
	
	public void setNotiz(String notiz) {
		this.notiz = notiz;
	}
	
	@Column(name="typ")
	public ETypes.ASchrittT getTyp() {
		return typ;
	}
	public void setTyp(ETypes.ASchrittT typ) {
		this.typ = typ;
	}
	
	@ManyToOne
	public Werkzeug getWerkzeug() {
		return werkzeug;
	}
	public void setWerkzeug(Werkzeug werkzeug) {
		this.werkzeug = werkzeug;
	}
	@Override
	public int compareTo(Arbeitsschritt aschritt) {
		if(aschritt==null){
			return -1;
		}
		return Integer.compare(this.position, aschritt.getPosition());
	}
	
}
