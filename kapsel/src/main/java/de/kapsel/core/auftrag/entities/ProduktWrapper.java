package de.kapsel.core.auftrag.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import de.kapsel.core.global.DTItem;
import de.kapsel.core.global.entities.AbstractKapselEntity;
import de.kapsel.core.produkt.entities.Produkt;

@Entity
@Table(name="produktwrapper")
public class ProduktWrapper extends AbstractKapselEntity implements Serializable, Comparable<ProduktWrapper>, DTItem{
	
	private static final long serialVersionUID = 1L;
	private long id;
	private int position;
	private int stueckzahl;
	private String name;
	private Produkt produkt;
	
	@Id
	@GeneratedValue
	@Column(name="id", nullable=false, unique=true)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name="position")
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	
	@Column(name="stueckzahl")
	public int getStueckzahl() {
		return stueckzahl;
	}
	public void setStueckzahl(int stueckzahl) {
		this.stueckzahl = stueckzahl;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@OneToOne
	@Cascade({CascadeType.SAVE_UPDATE})
	public Produkt getProdukt() {
		return produkt;
	}
	public void setProdukt(Produkt produkt) {
		this.produkt = produkt;
	} 
	
	@Override
	public int compareTo(ProduktWrapper pw) {
		if(pw==null){
			return -1;
		}
		return Integer.compare(getPosition(), pw.getPosition());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ProduktWrapper)) return false;
        if (obj == this) return true;
        
        ProduktWrapper pw = (ProduktWrapper) obj;
		
        return new EqualsBuilder().
                append(getbKey(), pw.getbKey()).
                isEquals();
	}
	
}
