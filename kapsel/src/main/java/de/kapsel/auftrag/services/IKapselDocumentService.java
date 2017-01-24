package de.kapsel.auftrag.services;

import java.util.List;

import de.kapsel.auftrag.entities.KapselDocument;


public interface IKapselDocumentService {
	
	public abstract void addKapselDocument(KapselDocument kapselDocument);

	public abstract void updateKapselDocument(KapselDocument kapselDocument);

	public abstract void deleteKapselDocument(KapselDocument kapselDocument);

	public abstract KapselDocument getKapselDocumentByName(String name);

	public abstract KapselDocument getKapselDocumentById(long id);

	public abstract List<KapselDocument> getKapselDocument();
	
	
}
