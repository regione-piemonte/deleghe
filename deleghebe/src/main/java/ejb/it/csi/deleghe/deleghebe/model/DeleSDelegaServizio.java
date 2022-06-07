/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the dele_s_delega_servizio database table.
 * 
 */
@Entity
@Table(name="dele_s_delega_servizio")
@NamedQuery(name="DeleSDelegaServizio.findAll", query="SELECT d FROM DeleSDelegaServizio d")
public class DeleSDelegaServizio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DELE_S_DELEGA_SERVIZIO_DELSID_GENERATOR", sequenceName="DELE_S_DELEGA_SERVIZIO_DELS_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DELE_S_DELEGA_SERVIZIO_DELSID_GENERATOR")
	@Column(name="dels_id")
	private Integer delsId;

	@Column(name="data_cancellazione")
	private java.util.Date dataCancellazione;

	@Column(name="data_creazione")
	private java.util.Date dataCreazione;

	@Column(name="data_modifica")
	private java.util.Date dataModifica;

	@Column(name="del_datadecorrenza")
	private java.util.Date delDatadecorrenza;

	@Column(name="del_datarevoca")
	private java.util.Date delDatarevoca;

	@Column(name="del_datarinuncia")
	private java.util.Date delDatarinuncia;

	@Column(name="del_datascadenza")
	private java.util.Date delDatascadenza;

	@Column(name="login_operazione")
	private String loginOperazione;

	@Column(name="validita_fine")
	private java.util.Date validitaFine;

	@Column(name="validita_inizio")
	private java.util.Date validitaInizio;

	//bi-directional many-to-one association to CsiLogAudit
	@ManyToOne
	@JoinColumn(name="audit_id")
	private CsiLogAudit csiLogAudit;

	//bi-directional many-to-one association to DeleDDelegaServizioStato
	@ManyToOne
	@JoinColumn(name="delstato_id")
	private DeleDDelegaServizioStato deleDDelegaServizioStato;

	//bi-directional many-to-one association to DeleDRuoloOp
	@ManyToOne
	@JoinColumn(name="ruoloop_id")
	private DeleDRuoloOp deleDRuoloOp;

	//bi-directional many-to-one association to DeleDServizio
	@ManyToOne
	@JoinColumn(name="ser_id")
	private DeleDServizio deleDServizio;

	//bi-directional many-to-one association to DeleTDelega
	@ManyToOne
	@JoinColumn(name="dlga_id")
	private DeleTDelega deleTDelega;

	//bi-directional many-to-one association to DeleTDelegaServizio
	@ManyToOne
	@JoinColumn(name="del_id")
	private DeleTDelegaServizio deleTDelegaServizio;

	//bi-directional many-to-one association to DeleDGrado
	@ManyToOne
	@JoinColumn(name="del_grado_id ")
	private DeleDGrado deleDGrado;

	public DeleSDelegaServizio() {
	}

	public Integer getDelsId() {
		return this.delsId;
	}

	public void setDelsId(Integer delsId) {
		this.delsId = delsId;
	}

	public java.util.Date getDataCancellazione() {
		return this.dataCancellazione;
	}

	public void setDataCancellazione(java.util.Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public java.util.Date getDataCreazione() {
		return this.dataCreazione;
	}

	public void setDataCreazione(java.util.Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public java.util.Date getDataModifica() {
		return this.dataModifica;
	}

	public void setDataModifica(java.util.Date dataModifica) {
		this.dataModifica = dataModifica;
	}

	public java.util.Date getDelDatadecorrenza() {
		return this.delDatadecorrenza;
	}

	public void setDelDatadecorrenza(java.util.Date delDatadecorrenza) {
		this.delDatadecorrenza = delDatadecorrenza;
	}

	public java.util.Date getDelDatarevoca() {
		return this.delDatarevoca;
	}

	public void setDelDatarevoca(java.util.Date delDatarevoca) {
		this.delDatarevoca = delDatarevoca;
	}

	public java.util.Date getDelDatarinuncia() {
		return this.delDatarinuncia;
	}

	public void setDelDatarinuncia(java.util.Date delDatarinuncia) {
		this.delDatarinuncia = delDatarinuncia;
	}

	public java.util.Date getDelDatascadenza() {
		return this.delDatascadenza;
	}

	public void setDelDatascadenza(java.util.Date delDatascadenza) {
		this.delDatascadenza = delDatascadenza;
	}

	public String getLoginOperazione() {
		return this.loginOperazione;
	}

	public void setLoginOperazione(String loginOperazione) {
		this.loginOperazione = loginOperazione;
	}

	public java.util.Date getValiditaFine() {
		return this.validitaFine;
	}

	public void setValiditaFine(java.util.Date validitaFine) {
		this.validitaFine = validitaFine;
	}

	public java.util.Date getValiditaInizio() {
		return this.validitaInizio;
	}

	public void setValiditaInizio(java.util.Date validitaInizio) {
		this.validitaInizio = validitaInizio;
	}

	public CsiLogAudit getCsiLogAudit() {
		return this.csiLogAudit;
	}

	public void setCsiLogAudit(CsiLogAudit csiLogAudit) {
		this.csiLogAudit = csiLogAudit;
	}

	public DeleDDelegaServizioStato getDeleDDelegaServizioStato() {
		return this.deleDDelegaServizioStato;
	}

	public void setDeleDDelegaServizioStato(DeleDDelegaServizioStato deleDDelegaServizioStato) {
		this.deleDDelegaServizioStato = deleDDelegaServizioStato;
	}

	public DeleDRuoloOp getDeleDRuoloOp() {
		return this.deleDRuoloOp;
	}

	public void setDeleDRuoloOp(DeleDRuoloOp deleDRuoloOp) {
		this.deleDRuoloOp = deleDRuoloOp;
	}

	public DeleDServizio getDeleDServizio() {
		return this.deleDServizio;
	}

	public void setDeleDServizio(DeleDServizio deleDServizio) {
		this.deleDServizio = deleDServizio;
	}

	public DeleTDelega getDeleTDelega() {
		return this.deleTDelega;
	}

	public void setDeleTDelega(DeleTDelega deleTDelega) {
		this.deleTDelega = deleTDelega;
	}

	public DeleTDelegaServizio getDeleTDelegaServizio() {
		return this.deleTDelegaServizio;
	}

	public void setDeleTDelegaServizio(DeleTDelegaServizio deleTDelegaServizio) {
		this.deleTDelegaServizio = deleTDelegaServizio;
	}

	public DeleDGrado getDeleDGrado() {
		return deleDGrado;
	}

	public void setDeleDGrado(DeleDGrado deleDGrado) {
		this.deleDGrado = deleDGrado;
	}
}