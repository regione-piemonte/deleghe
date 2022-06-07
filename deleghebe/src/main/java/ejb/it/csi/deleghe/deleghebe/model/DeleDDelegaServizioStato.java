/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.model;

import java.io.Serializable;
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
 * The persistent class for the dele_d_delega_servizio_stato database table.
 * 
 */
@Entity
@Table(name="dele_d_delega_servizio_stato")
@NamedQuery(name="DeleDDelegaServizioStato.findAll", query="SELECT d FROM DeleDDelegaServizioStato d")
public class DeleDDelegaServizioStato implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DELE_D_DELEGA_SERVIZIO_STATO_DELSTATOID_GENERATOR", sequenceName="DELE_D_DELEGA_SERVIZIO_STATO_DELSTATO_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DELE_D_DELEGA_SERVIZIO_STATO_DELSTATOID_GENERATOR")
	@Column(name="delstato_id")
	private Integer delstatoId;

	@Column(name="data_cancellazione")
	private java.util.Date dataCancellazione;

	@Column(name="data_creazione")
	private java.util.Date dataCreazione;

	@Column(name="data_modifica")
	private java.util.Date dataModifica;

	@Column(name="delstato_cod")
	private String delstatoCod;

	@Column(name="delstato_desc")
	private String delstatoDesc;

	@Column(name="login_operazione")
	private String loginOperazione;

	//bi-directional many-to-one association to DeleDRuoloOp
	@ManyToOne
	@JoinColumn(name="ruoloop_id")
	private DeleDRuoloOp deleDRuoloOp;

	//bi-directional many-to-one association to DeleSDelegaServizio
	@OneToMany(mappedBy="deleDDelegaServizioStato")
	private List<DeleSDelegaServizio> deleSDelegaServizios;

	//bi-directional many-to-one association to DeleTDelegaServizio
	@OneToMany(mappedBy="deleDDelegaServizioStato")
	private List<DeleTDelegaServizio> deleTDelegaServizios;

	public DeleDDelegaServizioStato() {
	}

	public Integer getDelstatoId() {
		return this.delstatoId;
	}

	public void setDelstatoId(Integer delstatoId) {
		this.delstatoId = delstatoId;
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

	public String getDelstatoCod() {
		return this.delstatoCod;
	}

	public void setDelstatoCod(String delstatoCod) {
		this.delstatoCod = delstatoCod;
	}

	public String getDelstatoDesc() {
		return this.delstatoDesc;
	}

	public void setDelstatoDesc(String delstatoDesc) {
		this.delstatoDesc = delstatoDesc;
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

	public List<DeleSDelegaServizio> getDeleSDelegaServizios() {
		return this.deleSDelegaServizios;
	}

	public void setDeleSDelegaServizios(List<DeleSDelegaServizio> deleSDelegaServizios) {
		this.deleSDelegaServizios = deleSDelegaServizios;
	}

	public DeleSDelegaServizio addDeleSDelegaServizio(DeleSDelegaServizio deleSDelegaServizio) {
		getDeleSDelegaServizios().add(deleSDelegaServizio);
		deleSDelegaServizio.setDeleDDelegaServizioStato(this);

		return deleSDelegaServizio;
	}

	public DeleSDelegaServizio removeDeleSDelegaServizio(DeleSDelegaServizio deleSDelegaServizio) {
		getDeleSDelegaServizios().remove(deleSDelegaServizio);
		deleSDelegaServizio.setDeleDDelegaServizioStato(null);

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
		deleTDelegaServizio.setDeleDDelegaServizioStato(this);

		return deleTDelegaServizio;
	}

	public DeleTDelegaServizio removeDeleTDelegaServizio(DeleTDelegaServizio deleTDelegaServizio) {
		getDeleTDelegaServizios().remove(deleTDelegaServizio);
		deleTDelegaServizio.setDeleDDelegaServizioStato(null);

		return deleTDelegaServizio;
	}

}