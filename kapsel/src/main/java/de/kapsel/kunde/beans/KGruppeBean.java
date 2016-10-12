package de.kapsel.kunde.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import de.kapsel.kunde.entities.KGruppe;
import de.kapsel.kunde.services.IKGruppeService;

@ManagedBean
@RequestScoped
public class KGruppeBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private KGruppe kGruppe;
	private List<KGruppe> kGruppen;
	private KGruppe selectedKGruppe;

	@ManagedProperty(value="#{kgruppeService}")
	private IKGruppeService kGruppeService;

	@PostConstruct
    public void init() {
		setkGruppen(getkGruppeService().getKGruppen());
		setSelectedKGruppe(getkGruppen().get(0));
	}

	public KGruppe getkGruppe() {
		return kGruppe;
	}

	public void setkGruppe(KGruppe kGruppe) {
		this.kGruppe = kGruppe;
	}

	public List<KGruppe> getkGruppen() {
		return kGruppen;
	}

	public void setkGruppen(List<KGruppe> kGruppen) {
		this.kGruppen = kGruppen;
	}

	public IKGruppeService getkGruppeService() {
		return kGruppeService;
	}

	public void setkGruppeService(IKGruppeService kGruppeService) {
		this.kGruppeService = kGruppeService;
	}

	public KGruppe getSelectedKGruppe() {
		return selectedKGruppe;
	}

	public void setSelectedKGruppe(KGruppe selectedKGruppe) {
		this.selectedKGruppe = selectedKGruppe;
	}

	

}
