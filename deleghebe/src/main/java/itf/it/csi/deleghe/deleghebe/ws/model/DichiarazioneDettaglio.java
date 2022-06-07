/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.ws.model;

import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.*;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dichiarazioneDettaglio",
propOrder = {
		"uuid",
		"dichiarazione",
		"stato",
		"cittadino1",
		"ruoloCittadino1",
		"cittadino2",
		"ruoloCittadino2",
		"notaRevocaBlocco",
		"tipoDelega",
		"deleghe",
		"documento",
		"motivazioneMenu",
		"motivazioneCasella",
		"idDettaglio"
})
public class DichiarazioneDettaglio extends Entita {
	
	private UUID uuid;
	
	private Dichiarazione dichiarazione;
	private DichiarazioneDettaglioStato stato;
	
	private Cittadino cittadino1;
	private DichiarazioneRuolo ruoloCittadino1; 
	
	private Cittadino cittadino2;
	private DichiarazioneRuolo ruoloCittadino2; 
	
	private String notaRevocaBlocco;
	private String tipoDelega;

	private String motivazioneMenu;
	private String motivazioneCasella;
	private Integer idDettaglio;

	@XmlElementWrapper
	@XmlElement(name="delega")
	protected List<DelegaServizio> deleghe;

	private Documento documento;
	
	public Dichiarazione getDichiarazione() {
		return dichiarazione;
	}
	public void setDichiarazione(Dichiarazione dichiarazione) {
		this.dichiarazione = dichiarazione;
	}
	public DichiarazioneDettaglioStato getStato() {
		return stato;
	}
	public void setStato(DichiarazioneDettaglioStato stato) {
		this.stato = stato;
	}
	public Cittadino getCittadino1() {
		return cittadino1;
	}
	public void setCittadino1(Cittadino cittadino1) {
		this.cittadino1 = cittadino1;
	}
	public Cittadino getCittadino2() {
		return cittadino2;
	}
	public void setCittadino2(Cittadino cittadino2) {
		this.cittadino2 = cittadino2;
	}
	public Documento getDocumento() {
		return documento;
	}
	public void setDocumento(Documento documento) {
		this.documento = documento;
	}
	public DichiarazioneRuolo getRuoloCittadino1() {
		return ruoloCittadino1;
	}
	public void setRuoloCittadino1(DichiarazioneRuolo ruoloCittadino1) {
		this.ruoloCittadino1 = ruoloCittadino1;
	}
	public DichiarazioneRuolo getRuoloCittadino2() {
		return ruoloCittadino2;
	}
	public void setRuoloCittadino2(DichiarazioneRuolo ruoloCittadino2) {
		this.ruoloCittadino2 = ruoloCittadino2;
	}
	public UUID getUuid() {
		return uuid;
	}
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	public String getNotaRevocaBlocco() {
		return notaRevocaBlocco;
	}
	public void setNotaRevocaBlocco(String notaRevocaBlocco) {
		this.notaRevocaBlocco = notaRevocaBlocco;
	}
	public String getTipoDelega() {
		return tipoDelega;
	}
	public void setTipoDelega(String tipoDelega) {
		this.tipoDelega = tipoDelega;
	}
	public List<DelegaServizio> getDeleghe() {
		return deleghe;
	}
	public void setDeleghe(List<DelegaServizio> deleghe) {
		this.deleghe = deleghe;
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
	public Integer getIdDettaglio() {
		return idDettaglio;
	}
	public void setIdDettaglio(Integer idDettaglio) {
		this.idDettaglio = idDettaglio;
	}
}
