package de.kapsel.core.kunde.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;

import de.kapsel.core.util.entities.AbstractKapselEntity;

@Entity
@Table(name="k_gruppen")
public class KGruppe extends AbstractKapselEntity implements Serializable{


	private static final long serialVersionUID = 1L;
	private long id;
	private String name;
	private double rabatt;
	private boolean aktiv;

	@Column(name="id", nullable=false, unique=true)
	@GeneratedValue
	@Id
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

	@Column(name="rabatt",nullable=false)
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
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof KGruppe)) return false;
        if (obj == this) return true;
        
        KGruppe kG = (KGruppe) obj;
		
        return new EqualsBuilder().
                append(getbKey(), kG.getbKey()).
                isEquals();
	}
	
}
