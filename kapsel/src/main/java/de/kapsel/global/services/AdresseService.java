package de.kapsel.global.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import de.kapsel.global.dao.IGenericDAO;
import de.kapsel.global.entities.Adresse;

public class AdresseService implements IAdresseService{

	//Injection DAO
	private IGenericDAO<Adresse> adresseDAO;

	public IGenericDAO<Adresse> getKGruppeDAO() {
		return adresseDAO;
	}

	public void setAdresseDAO(IGenericDAO<Adresse> adresseDAO) {
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
