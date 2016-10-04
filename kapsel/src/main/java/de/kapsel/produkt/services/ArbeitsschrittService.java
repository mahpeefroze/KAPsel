package de.kapsel.produkt.services;

import java.util.List;

import javax.faces.bean.ManagedProperty;

import de.kapsel.produkt.Arbeitsschritt;
import de.kapsel.produkt.dao.IArbeitsschrittDAO;

public class ArbeitsschrittService implements IArbeitsschrittService{
	
	//Injection BauteilDAO
		@ManagedProperty(value="#{arbeitsschrittDAO}")
		private IArbeitsschrittDAO arbeitsschrittDAO;

		public IArbeitsschrittDAO getArbeitsschrittDAO() {
			return arbeitsschrittDAO;
		}

		public void setArbeitsschrittDAO(IArbeitsschrittDAO arbeitsschrittDAO) {
			this.arbeitsschrittDAO = arbeitsschrittDAO;
		}
	
	@Override
	public void addArbeitsschritt(Arbeitsschritt arbeitsschritt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateArbeitsschritt(Arbeitsschritt arbeitsschritt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteArbeitsschritt(Arbeitsschritt arbeitsschritt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Arbeitsschritt getArbeitsschrittByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Arbeitsschritt getArbeitsschrittById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Arbeitsschritt> getArbeitsschritte() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
