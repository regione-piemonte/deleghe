/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.model;

import java.io.Serializable;
import java.util.Date;

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
 * The persistent class for the dele_s_dichiarazione database table.
 * 
 */
@Entity
@Table(name="dele_s_dichiarazione")
@NamedQuery(name="DeleSDichiarazione.findAll", query="SELECT d FROM DeleSDichiarazione d")
public class DeleSDichiarazione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DELE_S_DICHIARAZIONE_DICSID_GENERATOR", sequenceName="DELE_S_DICHIARAZIONE_DICS_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DELE_S_DICHIARAZIONE_DICSID_GENERATOR")
	@Column(name="dics_id")
	private Integer dicsId;

	@Column(name="data_cancellazione")
	private Date dataCancellazione;

	@Column(name="data_creazione")
	private Date dataCreazione;

	@Column(name="data_modifica")
	private Date dataModifica;

	@Column(name="login_operazione")
	private String loginOperazione;

	@Column(name="validita_fine")
	private Date validitaFine;

	@Column(name="validita_inizio")
	private Date validitaInizio;

	//bi-directional many-to-one association to DeleDDichiarazioneModo
	@ManyToOne
	@JoinColumn(name="dic_modo_id")
	private DeleDDichiarazioneModo deleDDichiarazioneModo;

	//bi-directional many-to-one association to DeleDDichiarazioneStato
	@ManyToOne
	@JoinColumn(name="dic_stato_id")
	private DeleDDichiarazioneStato deleDDichiarazioneStato;

	//bi-directional many-to-one association to DeleDDichiarazioneTipo
	@ManyToOne
	@JoinColumn(name="dic_tipo_id")
	private DeleDDichiarazioneTipo deleDDichiarazioneTipo;

	//bi-directional many-to-one association to DeleDRuoloOp
	@ManyToOne
	@JoinColumn(name="ruoloop_id")
	private DeleDRuoloOp deleDRuoloOp;

	//bi-directional many-to-one association to DeleTCittadino
	@ManyToOne
	@JoinColumn(name="cit_id")
	private DeleTCittadino deleTCittadino;

	//bi-directional many-to-one association to DeleTDichiarazione
	@ManyToOne
	@JoinColumn(name="dic_id")
	private DeleTDichiarazione deleTDichiarazione;

	@Column(name="compilatore_cf")
	private String compilatoreCf;

	public DeleSDichiarazione() {
	}

	public Integer getDicsId() {
		return this.dicsId;
	}

	public void setDicsId(Integer dicsId) {
		this.dicsId = dicsId;
	}

	public Date getDataCancellazione() {
		return this.dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public Date getDataCreazione() {
		return this.dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public Date getDataModifica() {
		return this.dataModifica;
	}

	public void setDataModifica(Date dataModifica) {
		this.dataModifica = dataModifica;
	}

	public String getLoginOperazione() {
		return this.loginOperazione;
	}

	public void setLoginOperazione(String loginOperazione) {
		this.loginOperazione = loginOperazione;
	}

	public Date getValiditaFine() {
		return this.validitaFine;
	}

	public void setValiditaFine(Date validitaFine) {
		this.validitaFine = validitaFine;
	}

	public Date getValiditaInizio() {
		return this.validitaInizio;
	}

	public void setValiditaInizio(Date validitaInizio) {
		this.validitaInizio = validitaInizio;
	}

	public DeleDDichiarazioneModo getDeleDDichiarazioneModo() {
		return this.deleDDichiarazioneModo;
	}

	public void setDeleDDichiarazioneModo(DeleDDichiarazioneModo deleDDichiarazioneModo) {
		this.deleDDichiarazioneModo = deleDDichiarazioneModo;
	}

	public DeleDDichiarazioneStato getDeleDDichiarazioneStato() {
		return this.deleDDichiarazioneStato;
	}

	public void setDeleDDichiarazioneStato(DeleDDichiarazioneStato deleDDichiarazioneStato) {
		this.deleDDichiarazioneStato = deleDDichiarazioneStato;
	}

	public DeleDDichiarazioneTipo getDeleDDichiarazioneTipo() {
		return this.deleDDichiarazioneTipo;
	}

	public void setDeleDDichiarazioneTipo(DeleDDichiarazioneTipo deleDDichiarazioneTipo) {
		this.deleDDichiarazioneTipo = deleDDichiarazioneTipo;
	}

	public DeleDRuoloOp getDeleDRuoloOp() {
		return this.deleDRuoloOp;
	}

	public void setDeleDRuoloOp(DeleDRuoloOp deleDRuoloOp) {
		this.deleDRuoloOp = deleDRuoloOp;
	}

	public DeleTCittadino getDeleTCittadino() {
		return this.deleTCittadino;
	}

	public void setDeleTCittadino(DeleTCittadino deleTCittadino) {
		this.deleTCittadino = deleTCittadino;
	}

	public DeleTDichiarazione getDeleTDichiarazione() {
		return this.deleTDichiarazione;
	}

	public void setDeleTDichiarazione(DeleTDichiarazione deleTDichiarazione) {
		this.deleTDichiarazione = deleTDichiarazione;
	}

	public String getCompilatoreCf() {
		return compilatoreCf;
	}

	public void setCompilatoreCf(String compilatoreCf) {
		this.compilatoreCf = compilatoreCf;
	}
}