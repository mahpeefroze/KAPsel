package de.kapsel.core.util;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class ETypes {
	
	public AuftragS[] getAuftragS(){
		return AuftragS.values();
	}
	
	public KundeT[] getKundeT(){
		return KundeT.values();
	}
	
	public ProduktT[] getProduktT(){
		return ProduktT.values();
	}
	
	public AschrittT[] getAschrittT(){
		return AschrittT.values();
	}
	
	public UserT[] getUserT(){
		return UserT.values();
	}
	
	public MaterialT[] getMaterialT(){
		return MaterialT.values();
	}
	
	public UnitT[] getUnitT(){
		return UnitT.values();
	}
	
	public DocumentT[] getDocumentT(){
		return DocumentT.values();
	}
	
	public enum AuftragS{
		Offen,
		Bearbeitung,
		Gestoppt,
		Storniert,
		Abgeschloßen;
		
	}

	public enum KundeT{
		Privat,
		Firma,
		Öffentlich;
	}
	
	public enum UserT{
		ADMIN,
		MOD,
		ADVUSER,
		SYSUSER;
	}
	
	public enum MaterialT{
		Holz,
		Platte,
		Klebstoff,
		Farbe,
		Lack,
		Öl,
		Beschlag,
		Verbindungsmittel,
		Kunststoffkante,
		Holzanleimer,
		Sonstiges;
	}
	
	public enum UnitT{
		kg,
		l,
		lfm,
		m2,
		m3,
		stck;
	}
	
	public enum ProduktT{
		Herstellung,
		Reparatur,
		Sonstiges;
	}
	
	public enum AschrittT{
		Maschinenarbeit,
		Bankraum,
		Montage;
	}
	
	public enum DocumentT{
		Angebot,
		Auftragsbestätigung,
		Rechnung,
		Abschlagsrechnung,
		Brief,
		Gesprächsprotokoll,
		Lieferschein;
	}
	
}
