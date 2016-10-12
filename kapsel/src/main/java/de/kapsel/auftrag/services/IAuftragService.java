package de.kapsel.auftrag.services;

import java.util.List;

import de.kapsel.auftrag.entities.Auftrag;

public interface IAuftragService {

	public abstract void addAuftrag(Auftrag auftrag);

	public abstract void updateAuftrag(Auftrag auftrag);

	public abstract void deleteAuftrag(Auftrag auftrag);

	public abstract Auftrag getAuftragByName(String name);

	public abstract Auftrag getAuftragById(long id);

	public abstract List<Auftrag> getAuftraege();

}
