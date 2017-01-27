package de.kapsel.core.util.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import de.kapsel.core.user.SessionUtils;

import java.util.Date;

@MappedSuperclass
public abstract class AbstractKapselEntity {
	
	protected long bKey;

	@Column(name="bKey", unique=true)
	public long getbKey() {
		return bKey;
	}

	public void setbKey(long bKey) {
		this.bKey = bKey;
	}
	
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
	
}
