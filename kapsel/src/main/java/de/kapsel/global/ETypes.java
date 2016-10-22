package de.kapsel.global;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean
@ApplicationScoped
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
	
	public enum AuftragS{
		offen,
		bearbeitung,
		gestoppt,
		storniert,
		abgeschloßen;
		
	}

	public enum KundeT{
		Privat,
		Firma,
		Öffentlich;
	}
	
	public enum KundeStatus{
		aktiv,
		gesperrt;
	}

	public enum UserT{
		ADMIN,
		MOD,
		SYSUSER,
		DBUSER,
		GAST;
	}

	public enum ETables{
		kunden,
		auftraege,
		users,
		produkte,
		leistungen,
		adressen;
	}
	
	public enum MaterialT{
		Holz,
		Platte,
		Klebstoff,
		Farbe,
		Lack,
		Öl,
		Beschlag,
		Sonstiges;
	}
	
	public enum UnitT{
		kg,
		g,
		l,
		m2,
		m3;
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
	
}
