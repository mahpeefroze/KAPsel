package de.kapsel.kunde.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import de.kapsel.kunde.dao.IKundeDAO;
import de.kapsel.kunde.entities.Kunde;

public class KundeService implements IKundeService{

	//Injection KundeDAO
	private IKundeDAO kundeDAO;

	public IKundeDAO getKundeDAO() {
		return kundeDAO;
	}

	public void setKundeDAO(IKundeDAO kundeDAO) {
		this.kundeDAO = kundeDAO;
	}

	@Transactional(readOnly = false)
	public void addKunde(Kunde kunde) {
		kundeDAO.addItem(kunde);
	}

	@Transactional(readOnly = false)
	public void updateKunde(Kunde kunde) {
		kundeDAO.updateItem(kunde);
	}

	@Transactional(readOnly = false)
	public void deleteKunde(Kunde kunde) {
		kundeDAO.deleteItem(kunde);
	}

	@Transactional(readOnly = true)
	public Kunde getKundeByName(String name) {
		return kundeDAO.getItemByName(name);
	}

	@Transactional(readOnly = true)
	public Kunde getKundeById(long id) {
		return kundeDAO.getItemById(id);
	}

	@Transactional(readOnly = true)
	public List<Kunde> getKunden() {
		return kundeDAO.getItems();
	}

}
