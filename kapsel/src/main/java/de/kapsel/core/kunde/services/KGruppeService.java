package de.kapsel.core.kunde.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import de.kapsel.core.kunde.dao.IKGruppeDAO;
import de.kapsel.core.kunde.entities.KGruppe;

public class KGruppeService implements IKGruppeService {


	//Injection DAO
	private IKGruppeDAO kgruppeDAO;

	public IKGruppeDAO getKgruppeDAO() {
		return kgruppeDAO;
	}

	public void setKgruppeDAO(IKGruppeDAO kgruppeDAO) {
		this.kgruppeDAO = kgruppeDAO;
	}

	@Override
	@Transactional(readOnly = false)
	public void addKGruppe(KGruppe kgruppe) {
		//java.sql.Date sqlDate = new java.sql.Date(kgruppe.getDatum().getTime());
		this.kgruppeDAO.addItem(kgruppe);
	}

	@Override
	@Transactional(readOnly = false)
	public void updateKGruppe(KGruppe kgruppe) {
		this.kgruppeDAO.updateItem(kgruppe);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteKGruppe(KGruppe kgruppe) {
		this.kgruppeDAO.deleteKGruppe(kgruppe);
	}

	@Override
	@Transactional(readOnly = true)
	public KGruppe getKGruppeByName(String name) {
		return this.kgruppeDAO.getItemByName(name);
	}

	@Override
	@Transactional(readOnly = true)
	public KGruppe getKGruppeById(long id) {
		return this.kgruppeDAO.getItemById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<KGruppe> getKGruppen() {
		return this.kgruppeDAO.getItems();
	}

}
