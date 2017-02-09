package de.kapsel.core.util.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import de.kapsel.core.user.SessionUtils;

import java.sql.Timestamp;
import java.util.Date;

@MappedSuperclass
public abstract class AbstractKapselEntity {
	
	protected long bKey;
	protected Timestamp erstDatum;
	protected Timestamp modDatum;
	protected int version;

	public static long generateBKey(){
		long stamp = new Date().getTime();
		int hash = SessionUtils.getLoggedUser().hashCode();
		double rand = Math.random();
		int factor = (int) (rand*3719);
		return stamp+(factor*hash);
	}
	
	public int hashCode() {
		return new HashCodeBuilder(17, 31).
				append(getbKey()).
				toHashCode();
	}

	public abstract boolean equals(Object obj);
	
	@Column(name="bKey", unique=true)
	public long getbKey() {
		return bKey;
	}

	public void setbKey(long bKey) {
		this.bKey = bKey;
	}
	
	@CreationTimestamp
	@Column(name="erst_datum", nullable=false)
	public Timestamp getErstDatum() {
		return erstDatum;
	}
	public void setErstDatum(Timestamp erstDatum) {
		this.erstDatum = erstDatum;
	}
	
	@UpdateTimestamp
	@Column(name="mod_datum", nullable=false)
	public Timestamp getModDatum() {
		return modDatum;
	}
	public void setModDatum(Timestamp modDatum) {
		this.modDatum = modDatum;
	}
	
	@Version
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
}
