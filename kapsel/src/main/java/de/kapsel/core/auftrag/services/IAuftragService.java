package de.kapsel.core.auftrag.services;

import java.util.List;

import de.kapsel.core.auftrag.entities.Auftrag;

public interface IAuftragService {

	void addAuftrag(Auftrag auftrag);

	void updateAuftrag(Auftrag auftrag);

	void deleteAuftrag(Auftrag auftrag);

	Auftrag getAuftragByName(String name);

	Auftrag getAuftragById(long id);

	List<Auftrag> getAuftraege();
	
	List<Auftrag> getAuftraegeWithChildren();

}
