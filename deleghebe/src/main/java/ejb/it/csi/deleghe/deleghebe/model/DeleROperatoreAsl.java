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
 * The persistent class for the dele_r_operatore_asl database table.
 * 
 */
@Entity
@Table(name="dele_r_operatore_asl")
@NamedQuery(name="DeleROperatoreAsl.findAll", query="SELECT d FROM DeleROperatoreAsl d")
public class DeleROperatoreAsl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DELE_R_OPERATORE_ASL_OPASLID_GENERATOR", sequenceName="DELE_R_OPERATORE_ASL_OPASL_ID_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DELE_R_OPERATORE_ASL_OPASLID_GENERATOR")
	@Column(name="opasl_id")
	private Integer opaslId;

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

	//bi-directional many-to-one association to DeleDRuoloOp
	@ManyToOne
	@JoinColumn(name="ruoloop_id")
	private DeleDRuoloOp deleDRuoloOp;

	//bi-directional many-to-one association to DeleTAsl
	@ManyToOne
	@JoinColumn(name="asl_id")
	private DeleTAsl deleTAsl;

	//bi-directional many-to-one association to DeleTOperatore
	@ManyToOne
	@JoinColumn(name="op_id")
	private DeleTOperatore deleTOperatore;

	public DeleROperatoreAsl() {
	}

	public Integer getOpaslId() {
		return this.opaslId;
	}

	public void setOpaslId(Integer opaslId) {
		this.opaslId = opaslId;
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

	public DeleDRuoloOp getDeleDRuoloOp() {
		return this.deleDRuoloOp;
	}

	public void setDeleDRuoloOp(DeleDRuoloOp deleDRuoloOp) {
		this.deleDRuoloOp = deleDRuoloOp;
	}

	public DeleTAsl getDeleTAsl() {
		return this.deleTAsl;
	}

	public void setDeleTAsl(DeleTAsl deleTAsl) {
		this.deleTAsl = deleTAsl;
	}

	public DeleTOperatore getDeleTOperatore() {
		return this.deleTOperatore;
	}

	public void setDeleTOperatore(DeleTOperatore deleTOperatore) {
		this.deleTOperatore = deleTOperatore;
	}

}