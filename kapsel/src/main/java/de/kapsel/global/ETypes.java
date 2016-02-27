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

}
