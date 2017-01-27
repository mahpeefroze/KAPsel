package de.kapsel.core.produkt.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;

import de.kapsel.core.util.entities.AbstractKapselEntity;

@Entity
@Table(name="werkzeuge")
public class Werkzeug extends AbstractKapselEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private long id;
	private String name;
	private double stundensatz;
	private String notiz;
	
	@Id
	@GeneratedValue
	@Column(name="id", nullable=false, unique=true)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
	@Column(name="name", nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="stundensatz", nullable=false)
	public double getStundensatz() {
		return stundensatz;
	}
	public void setStundensatz(double stundensatz) {
		this.stundensatz = stundensatz;
	}
	
	@Column(name="notiz", nullable=true)
	public String getNotiz() {
		return notiz;
	}
	public void setNotiz(String notiz) {
		this.notiz = notiz;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Werkzeug)) return false;
        if (obj == this) return true;
        
        Werkzeug w = (Werkzeug) obj;
		
        return new EqualsBuilder().
                append(getbKey(), w.getbKey()).
                isEquals();
	}
	
}
