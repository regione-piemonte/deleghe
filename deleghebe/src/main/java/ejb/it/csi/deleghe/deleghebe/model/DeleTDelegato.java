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
 * The persistent class for the dele_t_delegato database table.
 * 
 */
@Entity
@Table(name="dele_t_delegato")
@NamedQuery(name="DeleTDelegato.findAll", query="SELECT d FROM DeleTDelegato d")
public class DeleTDelegato implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DELE_T_DELEGATO_DLGOID_GENERATOR", sequenceName="DELE_T_DELEGATO_DLGO_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DELE_T_DELEGATO_DLGOID_GENERATOR")
	@Column(name="dlgo_id")
	private Integer dlgoId;

	@Column(name="data_cancellazione")
	private Date dataCancellazione;

	@Column(name="data_creazione")
	private Date dataCreazione;

	@Column(name="data_modifica")
	private Date dataModifica;

	@Column(name="dlgo_cf")
	private String dlgoCf;

	@Column(name="dlgo_cognome")
	private String dlgoCognome;

	@Column(name="dlgo_nascita_comune")
	private String dlgoNascitaComune;

	@Column(name="dlgo_nascita_data")
	private Date dlgoNascitaData;

	@Column(name="dlgo_nome")
	private String dlgoNome;

	@Column(name="dlgo_sesso")
	private String dlgoSesso;

	@Column(name="login_operazione")
	private String loginOperazione;

	//bi-directional many-to-one association to DeleDRuoloOp
	@ManyToOne
	@JoinColumn(name="ruoloop_id")
	private DeleDRuoloOp deleDRuoloOp;

	//bi-directional many-to-one association to DeleTDelega
	@ManyToOne
	@JoinColumn(name="dlga_id")
	private DeleTDelega deleTDelega;

	public DeleTDelegato() {
	}

	public Integer getDlgoId() {
		return this.dlgoId;
	}

	public void setDlgoId(Integer dlgoId) {
		this.dlgoId = dlgoId;
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

	public String getDlgoCf() {
		return this.dlgoCf;
	}

	public void setDlgoCf(String dlgoCf) {
		this.dlgoCf = dlgoCf;
	}

	public String getDlgoCognome() {
		return this.dlgoCognome;
	}

	public void setDlgoCognome(String dlgoCognome) {
		this.dlgoCognome = dlgoCognome;
	}

	public String getDlgoNascitaComune() {
		return this.dlgoNascitaComune;
	}

	public void setDlgoNascitaComune(String dlgoNascitaComune) {
		this.dlgoNascitaComune = dlgoNascitaComune;
	}

	public Date getDlgoNascitaData() {
		return this.dlgoNascitaData;
	}

	public void setDlgoNascitaData(Date dlgoNascitaData) {
		this.dlgoNascitaData = dlgoNascitaData;
	}

	public String getDlgoNome() {
		return this.dlgoNome;
	}

	public void setDlgoNome(String dlgoNome) {
		this.dlgoNome = dlgoNome;
	}

	public String getDlgoSesso() {
		return this.dlgoSesso;
	}

	public void setDlgoSesso(String dlgoSesso) {
		this.dlgoSesso = dlgoSesso;
	}

	public String getLoginOperazione() {
		return this.loginOperazione;
	}

	public void setLoginOperazione(String loginOperazione) {
		this.loginOperazione = loginOperazione;
	}

	public DeleDRuoloOp getDeleDRuoloOp() {
		return this.deleDRuoloOp;
	}

	public void setDeleDRuoloOp(DeleDRuoloOp deleDRuoloOp) {
		this.deleDRuoloOp = deleDRuoloOp;
	}

	public DeleTDelega getDeleTDelega() {
		return this.deleTDelega;
	}

	public void setDeleTDelega(DeleTDelega deleTDelega) {
		this.deleTDelega = deleTDelega;
	}

}