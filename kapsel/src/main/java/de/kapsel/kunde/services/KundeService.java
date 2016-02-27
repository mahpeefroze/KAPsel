package de.kapsel.kunde.services;

import java.util.List;

import javax.faces.bean.ManagedProperty;

import org.springframework.transaction.annotation.Transactional;

import de.kapsel.kunde.Kunde;
import de.kapsel.kunde.dao.IKundeDAO;

public class KundeService implements IKundeService{

	//Injection KundeDAO
	@ManagedProperty(value="#{kundeDAO}")
	private IKundeDAO kundeDAO;

	public IKundeDAO getKundeDAO() {
		return kundeDAO;
	}

	public void setKundeDAO(IKundeDAO kundeDAO) {
		this.kundeDAO = kundeDAO;
	}


	@Override
	@Transactional(readOnly = false)
	public void addKunde(Kunde kunde) {
		kundeDAO.addItem(kunde);
	}

	@Override
	@Transactional(readOnly = false)
	public void updateKunde(Kunde kunde) {
		kundeDAO.updateItem(kunde);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteKunde(Kunde kunde) {
		kundeDAO.deleteItem(kunde);
	}

	@Override
	@Transactional(readOnly = true)
	public Kunde getKundeByName(String name) {
		return kundeDAO.getItemByName(name);
	}

	@Override
	@Transactional(readOnly = true)
	public Kunde getKundeById(long id) {
		return kundeDAO.getItemById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Kunde> getKunden() {
		return kundeDAO.getItems();
	}

}
