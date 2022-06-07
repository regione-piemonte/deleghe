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
 * The persistent class for the d_servizio database table.
 * 
 */
@Entity
@Table(name="d_servizio")
@NamedQuery(name="DServizio.findAll", query="SELECT d FROM DServizio d")
public class DServizio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="D_SERVIZIO_SERVID_GENERATOR", sequenceName="D_SERVIZIO_SERV_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="D_SERVIZIO_SERVID_GENERATOR")
	@Column(name="serv_id")
	private Integer servId;

	@Column(name="data_cancellazione")
	private Date dataCancellazione;

	@Column(name="data_creazione")
	private Date dataCreazione;

	@Column(name="data_modifica")
	private Date dataModifica;

	@Column(name="login_operazione")
	private String loginOperazione;

	@Column(name="serv_arruolabile")
	private Boolean servArruolabile;

	@Column(name="serv_cod")
	private String servCod;

	@Column(name="serv_delegabile")
	private Boolean servDelegabile;

	@Column(name="serv_desc")
	private String servDesc;

	@Column(name="serv_descestesa")
	private String servDescestesa;

	@Column(name="serv_minore")
	private Boolean servMinore;

	@Column(name="serv_notificabile")
	private Boolean servNotificabile;

	@Column(name="serv_obblarruolamento")
	private Boolean servObblarruolamento;

	@Column(name="serv_maxgg_delega")
	private Integer servMaxggDelega;

	@Column(name="serv_url")
	private String servUrl;

	@Column(name="validita_fine")
	private Date validitaFine;

	@Column(name="validita_inizio")
	private Date validitaInizio;

	//bi-directional many-to-one association to DeleDRuoloOp
	@ManyToOne
	@JoinColumn(name="ruoloop_id")
	private DeleDRuoloOp deleDRuoloOp;

	public DServizio() {
	}

	public Integer getServId() {
		return this.servId;
	}

	public void setServId(Integer servId) {
		this.servId = servId;
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

	public Boolean getServArruolabile() {
		return this.servArruolabile;
	}

	public void setServArruolabile(Boolean servArruolabile) {
		this.servArruolabile = servArruolabile;
	}

	public String getServCod() {
		return this.servCod;
	}

	public void setServCod(String servCod) {
		this.servCod = servCod;
	}

	public Boolean getServDelegabile() {
		return this.servDelegabile;
	}

	public void setServDelegabile(Boolean servDelegabile) {
		this.servDelegabile = servDelegabile;
	}

	public String getServDesc() {
		return this.servDesc;
	}

	public void setServDesc(String servDesc) {
		this.servDesc = servDesc;
	}

	public String getServDescestesa() {
		return this.servDescestesa;
	}

	public void setServDescestesa(String servDescestesa) {
		this.servDescestesa = servDescestesa;
	}

	public Boolean getServMinore() {
		return this.servMinore;
	}

	public void setServMinore(Boolean servMinore) {
		this.servMinore = servMinore;
	}

	public Boolean getServNotificabile() {
		return this.servNotificabile;
	}

	public void setServNotificabile(Boolean servNotificabile) {
		this.servNotificabile = servNotificabile;
	}

	public Boolean getServObblarruolamento() {
		return this.servObblarruolamento;
	}

	public void setServObblarruolamento(Boolean servObblarruolamento) {
		this.servObblarruolamento = servObblarruolamento;
	}

	public Integer getServMaxggDelega() {
		return servMaxggDelega;
	}

	public void setServMaxggDelega(Integer servMaxggDelega) {
		this.servMaxggDelega = servMaxggDelega;
	}

	public String getServUrl() {
		return this.servUrl;
	}

	public void setServUrl(String servUrl) {
		this.servUrl = servUrl;
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

}