package de.kapsel.produkt.beans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import de.kapsel.produkt.Bauteil;
import de.kapsel.produkt.services.IBauteilService;

@ManagedBean
@RequestScoped
public class BauteilBean implements Serializable{

		private static final long serialVersionUID = 1L;
		private ArrayList<Bauteil> bauteile;
		private Bauteil selectedBauteil;
		private Bauteil newBauteil;
		
		
		@ManagedProperty(value="#{bauteilService}")
		private IBauteilService bauteilService;


		//Gather Items to fill the table
		public BauteilBean(){
			//Cant call the Service at Bean creation time, because injection happens later so NullPointer would be thrown
			this.newBauteil = new Bauteil();
		}

	
}
