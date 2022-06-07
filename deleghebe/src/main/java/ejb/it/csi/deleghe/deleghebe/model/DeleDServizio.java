/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the dele_d_servizio database table.
 * 
 */
@Entity
@Table(name="dele_d_servizio")
@NamedQuery(name="DeleDServizio.findAll", query="SELECT d FROM DeleDServizio d")
public class DeleDServizio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DELE_D_SERVIZIO_SERID_GENERATOR", sequenceName="DELE_D_SERVIZIO_SER_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DELE_D_SERVIZIO_SERID_GENERATOR")
	@Column(name="ser_id")
	private Integer serId;

	@Column(name="data_cancellazione")
	private Date dataCancellazione;

	@Column(name="data_creazione")
	private Date dataCreazione;

	@Column(name="data_modifica")
	private Date dataModifica;

	@Column(name="login_operazione")
	private String loginOperazione;

	@Column(name="ser_arruolabile")
	private Boolean serArruolabile;

	@Column(name="ser_cod")
	private String serCod;

	@Column(name="ser_delegabile")
	private Boolean serDelegabile;

	@Column(name="ser_desc")
	private String serDesc;

	@Column(name="ser_descestesa")
	private String serDescestesa;

	@Column(name="ser_minore")
	private Boolean serMinore;

	@Column(name="ser_notificabile")
	private Boolean serNotificabile;

	@Column(name="ser_obblarruolamento")
	private Boolean serObblarruolamento;

	@Column(name="ser_url")
	private Boolean serUrl;

	@Column(name="validita_fine")
	private Date validitaFine;

	@Column(name="validita_inizio")
	private Date validitaInizio;
	
	//NUOVO CAMPO
	@Column(name="cod_ser_padre")
	private String cod_ser_padre;
	
	//NUOVO CAMPO
	@Column(name="frase_debole")
	private String fraseDebole;
	
	//NUOVO CAMPO
	@Column(name="frase_forte")
	private String fraseForte;

	//bi-directional many-to-one association to DeleDRuoloOp
	@ManyToOne
	@JoinColumn(name="ruoloop_id")
	private DeleDRuoloOp deleDRuoloOp;

	//bi-directional many-to-one association to DeleSDelegaServizio
	@OneToMany(mappedBy="deleDServizio")
	private List<DeleSDelegaServizio> deleSDelegaServizios;

	//bi-directional many-to-one association to DeleTDelegaServizio
	@OneToMany(mappedBy="deleDServizio")
	private List<DeleTDelegaServizio> deleTDelegaServizios;

	//bi-directional many-to-one association to DeleTServizioParametro
	@OneToMany(mappedBy="deleDServizio")
	private List<DeleTServizioParametro> deleTServizioParametros;

	//bi-directional many-to-one association to DeleRServizioDelegaTipoGrado
	@OneToMany(mappedBy="deleDServizio")
	private List<DeleRServizioDelegaTipoGrado> deleRServizioDelegaTipoGrados;

	public DeleDServizio() {
	}

	public Integer getSerId() {
		return this.serId;
	}

	public void setSerId(Integer serId) {
		this.serId = serId;
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

	public Boolean getSerArruolabile() {
		return this.serArruolabile;
	}

	public void setSerArruolabile(Boolean serArruolabile) {
		this.serArruolabile = serArruolabile;
	}

	public String getSerCod() {
		return this.serCod;
	}

	public void setSerCod(String serCod) {
		this.serCod = serCod;
	}

	public Boolean getSerDelegabile() {
		return this.serDelegabile;
	}

	public void setSerDelegabile(Boolean serDelegabile) {
		this.serDelegabile = serDelegabile;
	}

	public String getSerDesc() {
		return this.serDesc;
	}

	public void setSerDesc(String serDesc) {
		this.serDesc = serDesc;
	}

	public String getSerDescestesa() {
		return this.serDescestesa;
	}

	public void setSerDescestesa(String serDescestesa) {
		this.serDescestesa = serDescestesa;
	}

	public Boolean getSerMinore() {
		return this.serMinore;
	}

	public void setSerMinore(Boolean serMinore) {
		this.serMinore = serMinore;
	}

	public Boolean getSerNotificabile() {
		return this.serNotificabile;
	}

	public void setSerNotificabile(Boolean serNotificabile) {
		this.serNotificabile = serNotificabile;
	}

	public Boolean getSerObblarruolamento() {
		return this.serObblarruolamento;
	}

	public void setSerObblarruolamento(Boolean serObblarruolamento) {
		this.serObblarruolamento = serObblarruolamento;
	}

	public Boolean getSerUrl() {
		return this.serUrl;
	}

	public void setSerUrl(Boolean serUrl) {
		this.serUrl = serUrl;
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

	public List<DeleSDelegaServizio> getDeleSDelegaServizios() {
		return this.deleSDelegaServizios;
	}

	public void setDeleSDelegaServizios(List<DeleSDelegaServizio> deleSDelegaServizios) {
		this.deleSDelegaServizios = deleSDelegaServizios;
	}

	public DeleSDelegaServizio addDeleSDelegaServizio(DeleSDelegaServizio deleSDelegaServizio) {
		getDeleSDelegaServizios().add(deleSDelegaServizio);
		deleSDelegaServizio.setDeleDServizio(this);

		return deleSDelegaServizio;
	}

	public DeleSDelegaServizio removeDeleSDelegaServizio(DeleSDelegaServizio deleSDelegaServizio) {
		getDeleSDelegaServizios().remove(deleSDelegaServizio);
		deleSDelegaServizio.setDeleDServizio(null);

		return deleSDelegaServizio;
	}

	public List<DeleTDelegaServizio> getDeleTDelegaServizios() {
		return this.deleTDelegaServizios;
	}

	public void setDeleTDelegaServizios(List<DeleTDelegaServizio> deleTDelegaServizios) {
		this.deleTDelegaServizios = deleTDelegaServizios;
	}

	public DeleTDelegaServizio addDeleTDelegaServizio(DeleTDelegaServizio deleTDelegaServizio) {
		getDeleTDelegaServizios().add(deleTDelegaServizio);
		deleTDelegaServizio.setDeleDServizio(this);

		return deleTDelegaServizio;
	}

	public DeleTDelegaServizio removeDeleTDelegaServizio(DeleTDelegaServizio deleTDelegaServizio) {
		getDeleTDelegaServizios().remove(deleTDelegaServizio);
		deleTDelegaServizio.setDeleDServizio(null);

		return deleTDelegaServizio;
	}

	public List<DeleTServizioParametro> getDeleTServizioParametros() {
		return this.deleTServizioParametros;
	}

	public void setDeleTServizioParametros(List<DeleTServizioParametro> deleTServizioParametros) {
		this.deleTServizioParametros = deleTServizioParametros;
	}

	public DeleTServizioParametro addDeleTServizioParametro(DeleTServizioParametro deleTServizioParametro) {
		getDeleTServizioParametros().add(deleTServizioParametro);
		deleTServizioParametro.setDeleDServizio(this);

		return deleTServizioParametro;
	}

	public DeleTServizioParametro removeDeleTServizioParametro(DeleTServizioParametro deleTServizioParametro) {
		getDeleTServizioParametros().remove(deleTServizioParametro);
		deleTServizioParametro.setDeleDServizio(null);

		return deleTServizioParametro;
	}

	public String getCod_ser_padre() {
		return cod_ser_padre;
	}

	public void setCod_ser_padre(String cod_ser_padre) {
		this.cod_ser_padre = cod_ser_padre;
	}

	public String getFraseDebole() {
		return fraseDebole;
	}

	public void setFraseDebole(String fraseDebole) {
		this.fraseDebole = fraseDebole;
	}

	public String getFraseForte() {
		return fraseForte;
	}

	public void setFraseForte(String fraseForte) {
		this.fraseForte = fraseForte;
	}

}