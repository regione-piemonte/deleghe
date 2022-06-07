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
 * The persistent class for the dele_t_servizio_parametro database table.
 * 
 */
@Entity
@Table(name="dele_t_servizio_parametro")
@NamedQuery(name="DeleTServizioParametro.findAll", query="SELECT d FROM DeleTServizioParametro d")
public class DeleTServizioParametro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DELE_T_SERVIZIO_PARAMETRO_SERPARID_GENERATOR", sequenceName="DELE_T_SERVIZIO_PARAMETRO_SERPAR_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DELE_T_SERVIZIO_PARAMETRO_SERPARID_GENERATOR")
	@Column(name="serpar_id")
	private Integer serparId;

	@Column(name="data_cancellazione")
	private Date dataCancellazione;

	@Column(name="data_creazione")
	private Date dataCreazione;

	@Column(name="data_modifica")
	private Date dataModifica;

	@Column(name="login_operazione")
	private String loginOperazione;

	@Column(name="serpar_cod")
	private String serparCod;

	@Column(name="serpar_desc")
	private String serparDesc;

	@Column(name="serpar_valore")
	private String serparValore;

	@Column(name="validita_fine")
	private Date validitaFine;

	@Column(name="validita_inizio")
	private Date validitaInizio;

	//bi-directional many-to-one association to DeleDRuoloOp
	@ManyToOne
	@JoinColumn(name="ruoloop_id")
	private DeleDRuoloOp deleDRuoloOp;

	//bi-directional many-to-one association to DeleDServizio
	@ManyToOne
	@JoinColumn(name="ser_id")
	private DeleDServizio deleDServizio;

	public DeleTServizioParametro() {
	}

	public Integer getSerparId() {
		return this.serparId;
	}

	public void setSerparId(Integer serparId) {
		this.serparId = serparId;
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

	public String getSerparCod() {
		return this.serparCod;
	}

	public void setSerparCod(String serparCod) {
		this.serparCod = serparCod;
	}

	public String getSerparDesc() {
		return this.serparDesc;
	}

	public void setSerparDesc(String serparDesc) {
		this.serparDesc = serparDesc;
	}

	public String getSerparValore() {
		return this.serparValore;
	}

	public void setSerparValore(String serparValore) {
		this.serparValore = serparValore;
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

}