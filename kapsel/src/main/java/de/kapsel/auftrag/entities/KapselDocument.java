package de.kapsel.auftrag.entities;

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
@Table(name = "dokumente")
public class KapselDocument extends AbstractKapselEntity implements Serializable, Comparable<KapselDocument>{
	
	private static final long serialVersionUID = 1L;
	private long id;
	private int position;
	private String name;
	private String fileName;
	private String path;
	private ETypes.DocumentT typ;
	private boolean markup;
	
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
	
	@Column(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="fileName")
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@Column(name="path")
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	@Column(name="typ")
	public ETypes.DocumentT getTyp() {
		return typ;
	}
	public void setTyp(ETypes.DocumentT typ) {
		this.typ = typ;
	}
	
	@Column(name="markup")
	public boolean isMarkup() {
		return markup;
	}
	public void setMarkup(boolean markup) {
		this.markup = markup;
	}
	
	@Override
	public int compareTo(KapselDocument kd) {
		if(kd==null){
			return -1;
		}
		return Integer.compare(getPosition(), kd.getPosition());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof KapselDocument)) return false;
        if (obj == this) return true;
        
        KapselDocument kd = (KapselDocument) obj;
		
        return new EqualsBuilder().
                append(getbKey(), kd.getbKey()).
                isEquals();
	}
	
}
