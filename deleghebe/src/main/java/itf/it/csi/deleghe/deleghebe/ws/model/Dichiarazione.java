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
public class Dichiarazione extends Entita {
	
	private UUID uuid;
	
	private TipoDichiarazione tipo;
	private StatoDichiarazione stato;
	private ModoDichiarazione modo;
	
	private Cittadino cittadino;

	@XmlSchemaType(name = "dateTime")
	protected Date dataCreazioneDichiarazione;

	@XmlElementWrapper
	@XmlElement(name="dettaglio")
	private List<DichiarazioneDettaglio> dettagli;

	private String compilatoreCF;
	private Integer nPratica;

	private String numeroDocumento;
	private String autorita;
	private Date dataInizioTutela;
	private Date dataFineTutela;

	@Override
	public Date getDataCreazione() {
		return dataCreazioneDichiarazione;
	}

	@Override
	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazioneDichiarazione = dataCreazione;
	}

	public TipoDichiarazione getTipo() {
		return tipo;
	}

	public void setTipo(TipoDichiarazione tipo) {
		this.tipo = tipo;
	}

	public StatoDichiarazione getStato() {
		return stato;
	}

	public void setStato(StatoDichiarazione stato) {
		this.stato = stato;
	}

	public ModoDichiarazione getModo() {
		return modo;
	}

	public void setModo(ModoDichiarazione modo) {
		this.modo = modo;
	}

	public Cittadino getCittadino() {
		return cittadino;
	}

	public void setCittadino(Cittadino cittadino) {
		this.cittadino = cittadino;
	}

	@XmlTransient
	public List<DichiarazioneDettaglio> getDettagli() {
		return dettagli;
	}

	public void setDettagli(List<DichiarazioneDettaglio> dettagli) {
		this.dettagli = dettagli;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getCompilatoreCF() {
		return compilatoreCF;
	}

	public void setCompilatoreCF(String compilatoreCF) {
		this.compilatoreCF = compilatoreCF;
	}

	public Integer getnPratica() {
		return nPratica;
	}

	public void setnPratica(Integer nPratica) {
		this.nPratica = nPratica;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getAutorita() {
		return autorita;
	}

	public void setAutorita(String autorita) {
		this.autorita = autorita;
	}

	public Date getDataInizioTutela() {
		return dataInizioTutela;
	}

	public void setDataInizioTutela(Date dataInizioTutela) {
		this.dataInizioTutela = dataInizioTutela;
	}

	public Date getDataFineTutela() {
		return dataFineTutela;
	}

	public void setDataFineTutela(Date dataFineTutela) {
		this.dataFineTutela = dataFineTutela;
	}
}
