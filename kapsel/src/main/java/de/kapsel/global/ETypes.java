package de.kapsel.global;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean
@ApplicationScoped
public class ETypes {
	
	public KundeT[] getKundeT(){
		return KundeT.values();
	}

	public enum KundeT{
		PRIVAT,
		FIRMA,
		OEFFENTLICH;
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
		Beschlag;
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
	
	
}
