package de.kapsel.produkt.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;

import de.kapsel.global.ETypes;
import de.kapsel.global.entities.AbstractKapselEntity;

@Entity
@Table(name="materialien")
public class Material extends AbstractKapselEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	private long id;
	private String name;
	private double preis;
	private ETypes.UnitT einheit;
	private ETypes.MaterialT typ;
	
	
	@Id
	@GeneratedValue
	@Column(name="id", nullable=false, unique=true)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name="name", nullable=false, unique=true)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="preis", nullable=false)
	public double getPreis() {
		return preis;
	}
	public void setPreis(double preis) {
		this.preis = preis;
	}
	
	@Column(name="einheit", nullable=false)
	public ETypes.UnitT getEinheit() {
		return einheit;
	}
	public void setEinheit(ETypes.UnitT einheit) {
		this.einheit = einheit;
	}
	
	@Column(name="typ", nullable=true)
	public ETypes.MaterialT getTyp() {
		return typ;
	}
	public void setTyp(ETypes.MaterialT typ) {
		this.typ = typ;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Material)) return false;
        if (obj == this) return true;
        
        Material m = (Material) obj;
		
        return new EqualsBuilder().
                append(getbKey(), m.getbKey()).
                isEquals();
	}
	
	
	
}
