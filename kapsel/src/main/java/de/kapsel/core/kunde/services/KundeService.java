package de.kapsel.core.kunde.services;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;

import de.kapsel.core.kunde.entities.Kunde;
import de.kapsel.core.util.dao.ILazyLoadDAO;

public class KundeService implements IKundeService{

	//Injection KundeDAO
	private ILazyLoadDAO<Kunde> kundeDAO;

	public ILazyLoadDAO<Kunde> getKundeDAO() {
		return kundeDAO;
	}

	public void setKundeDAO(ILazyLoadDAO<Kunde> kundeDAO) {
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
		Kunde result = kundeDAO.getItemByName(name);
		Hibernate.initialize(result.getAuftraege());
		return result;
	}

	@Transactional(readOnly = true)
	public Kunde getKundeById(long id) {
		Kunde result = kundeDAO.getItemById(id);
		Hibernate.initialize(result.getAuftraege());
		return result;
	}

	@Transactional(readOnly = true)
	public List<Kunde> getKunden() {
		return kundeDAO.getItems();
	}

	@Transactional(readOnly = true)
	public List<Kunde> getKundenWithChildren() {
		return kundeDAO.getItemsWithChildren();
	}

}
