package de.kapsel.global.services;

import java.util.List;

import javax.faces.bean.ManagedProperty;

import org.springframework.transaction.annotation.Transactional;

import de.kapsel.global.Adresse;
import de.kapsel.global.dao.IAdresseDAO;

public class AdresseService implements IAdresseService{

	//Injection DAO
	@ManagedProperty(value="#{adresseDAO}")
	private IAdresseDAO adresseDAO;

	public IAdresseDAO getKGruppeDAO() {
		return adresseDAO;
	}

	public void setAdresseDAO(IAdresseDAO adresseDAO) {
		this.adresseDAO = adresseDAO;
	}


	@Override
	@Transactional(readOnly = false)
	public void addAdresse(Adresse adresse) {
		this.adresseDAO.addItem(adresse);
	}

	@Override
	@Transactional(readOnly = false)
	public void updateAdresse(Adresse adresse) {
		this.adresseDAO.updateItem(adresse);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteAdresse(Adresse adresse) {
		this.adresseDAO.deleteItem(adresse);
	}

	@Override
	@Transactional(readOnly = true)
	public Adresse getAdresseByName(String name) {
		return this.adresseDAO.getItemByName(name);
	}

	@Override
	@Transactional(readOnly = true)
	public Adresse getAdresseById(long id) {
		return this.adresseDAO.getItemById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Adresse> getAdressen() {
		return this.adresseDAO.getItems();
	}

}
