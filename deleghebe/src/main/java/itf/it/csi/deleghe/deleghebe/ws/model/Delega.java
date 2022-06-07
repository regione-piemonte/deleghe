/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.ws.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.*;

@XmlType
public class Delega extends Entita {
	

	private UUID uuid;
	

	private Integer nPratica;
	private Integer ciIdDelegato;	
	private Integer citIdDelegante;
	
	private Cittadino delegante;
	private Cittadino delegato;
	private Delegato  delegatoInput;
	private String motivazioneMenu;
	private String motivazioneCasella;

	@XmlSchemaType(name = "dateTime")
	protected Date dataCreazioneDelega;

	@XmlElementWrapper
	@XmlElement(name="servizio")
	protected List<DelegaServ> servizi;

	private Boolean presavisione;
	private Date presavisioneData;
	private DichiarazioneDettaglio dichiarazioneDettaglio;
	private String compilatoreCF;
	private String tipoDelega;
	private String statoDelega;
	private Boolean bloccaDelega;

	@XmlTransient
	public List<DelegaServ> getServizi() {
		return servizi;
	}

	@Override
	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazioneDelega = dataCreazione;
	}

	@Override
	public Date getDataCreazione() {
		return dataCreazioneDelega;
	}

	public void setServizi(List<DelegaServ> deleghe) {
		this.servizi = deleghe;
	}

	public Cittadino getDelegante() {
		return delegante;
	}

	public void setDelegante(Cittadino delegante) {
		this.delegante = delegante;
	}

	public Cittadino getDelegato() {
		return delegato;
	}

	public void setDelegato(Cittadino delegato) {
		this.delegato = delegato;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public Delegato getDelegatoInput() {
		return delegatoInput;
	}

	public void setDelegatoInput(Delegato delegatoInput) {
		this.delegatoInput = delegatoInput;
	}

	public String getMotivazioneMenu() {
		return motivazioneMenu;
	}

	public void setMotivazioneMenu(String motivazioneMenu) {
		this.motivazioneMenu = motivazioneMenu;
	}

	public String getMotivazioneCasella() {
		return motivazioneCasella;
	}

	public void setMotivazioneCasella(String motivazioneCasella) {
		this.motivazioneCasella = motivazioneCasella;
	}

	public Boolean getPresavisione() {
		return presavisione;
	}

	public void setPresavisione(Boolean presavisione) {
		this.presavisione = presavisione;
	}

	public Date getPresavisioneData() {
		return presavisioneData;
	}

	public void setPresavisioneData(Date presavisioneData) {
		this.presavisioneData = presavisioneData;
	}

	public DichiarazioneDettaglio getDichiarazioneDettaglio() {
		return dichiarazioneDettaglio;
	}

	public void setDichiarazioneDettaglio(DichiarazioneDettaglio dichiarazioneDettaglio) {
		this.dichiarazioneDettaglio = dichiarazioneDettaglio;
	}

	public String getCompilatoreCF() {
		return compilatoreCF;
	}

	public void setCompilatoreCF(String compilatoreCF) {
		this.compilatoreCF = compilatoreCF;
	}

	public String getTipoDelega() {
		return tipoDelega;
	}

	public void setTipoDelega(String tipoDelega) {
		this.tipoDelega = tipoDelega;
	}

	public String getStatoDelega() {
		return statoDelega;
	}

	public void setStatoDelega(String statoDelega) {
		this.statoDelega = statoDelega;
	}

	public Boolean getBloccaDelega() {
		return bloccaDelega;
	}

	public void setBloccaDelega(Boolean bloccaDelega) {
		this.bloccaDelega = bloccaDelega;
	}

	public Integer getnPratica() {
		return nPratica;
	}

	public void setnPratica(Integer nPratica) {
		this.nPratica = nPratica;
	}

	public Integer getCiIdDelegato() {
		return ciIdDelegato;
	}

	public void setCiIdDelegato(Integer ciIdDelegato) {
		this.ciIdDelegato = ciIdDelegato;
	}

	public Integer getCitIdDelegante() {
		return citIdDelegante;
	}

	public void setCitIdDelegante(Integer citIdDelegante) {
		this.citIdDelegante = citIdDelegante;
	}
	
	

	
}
