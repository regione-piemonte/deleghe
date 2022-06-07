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
 * The persistent class for the dele_d_cittadino_dati_stato database table.
 * 
 */
@Entity
@Table(name="dele_d_cittadino_dati_stato")
@NamedQuery(name="DeleDCittadinoDatiStato.findAll", query="SELECT d FROM DeleDCittadinoDatiStato d")
public class DeleDCittadinoDatiStato implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DELE_D_CITTADINO_DATI_STATO_CITDSTATOID_GENERATOR", sequenceName="DELE_D_CITTADINO_DATI_STATO_CITDSTATO_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DELE_D_CITTADINO_DATI_STATO_CITDSTATOID_GENERATOR")
	@Column(name="citdstato_id")
	private Integer citdstatoId;

	@Column(name="citdstato_cod")
	private String citdstatoCod;

	@Column(name="citdstato_desc")
	private String citdstatoDesc;

	@Column(name="data_cancellazione")
	private Date dataCancellazione;

	@Column(name="data_creazione")
	private Date dataCreazione;

	@Column(name="data_modifica")
	private Date dataModifica;

	@Column(name="login_operazione")
	private String loginOperazione;

	//bi-directional many-to-one association to DeleDRuoloOp
	@ManyToOne
	@JoinColumn(name="ruoloop_id")
	private DeleDRuoloOp deleDRuoloOp;

	//bi-directional many-to-one association to DeleSCittadino
	@OneToMany(mappedBy="deleDCittadinoDatiStato")
	private List<DeleSCittadino> deleSCittadinos;

	//bi-directional many-to-one association to DeleTCittadino
	@OneToMany(mappedBy="deleDCittadinoDatiStato")
	private List<DeleTCittadino> deleTCittadinos;

	public DeleDCittadinoDatiStato() {
	}

	public Integer getCitdstatoId() {
		return this.citdstatoId;
	}

	public void setCitdstatoId(Integer citdstatoId) {
		this.citdstatoId = citdstatoId;
	}

	public String getCitdstatoCod() {
		return this.citdstatoCod;
	}

	public void setCitdstatoCod(String citdstatoCod) {
		this.citdstatoCod = citdstatoCod;
	}

	public String getCitdstatoDesc() {
		return this.citdstatoDesc;
	}

	public void setCitdstatoDesc(String citdstatoDesc) {
		this.citdstatoDesc = citdstatoDesc;
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

	public DeleDRuoloOp getDeleDRuoloOp() {
		return this.deleDRuoloOp;
	}

	public void setDeleDRuoloOp(DeleDRuoloOp deleDRuoloOp) {
		this.deleDRuoloOp = deleDRuoloOp;
	}

	public List<DeleSCittadino> getDeleSCittadinos() {
		return this.deleSCittadinos;
	}

	public void setDeleSCittadinos(List<DeleSCittadino> deleSCittadinos) {
		this.deleSCittadinos = deleSCittadinos;
	}

	public DeleSCittadino addDeleSCittadino(DeleSCittadino deleSCittadino) {
		getDeleSCittadinos().add(deleSCittadino);
		deleSCittadino.setDeleDCittadinoDatiStato(this);

		return deleSCittadino;
	}

	public DeleSCittadino removeDeleSCittadino(DeleSCittadino deleSCittadino) {
		getDeleSCittadinos().remove(deleSCittadino);
		deleSCittadino.setDeleDCittadinoDatiStato(null);

		return deleSCittadino;
	}

	public List<DeleTCittadino> getDeleTCittadinos() {
		return this.deleTCittadinos;
	}

	public void setDeleTCittadinos(List<DeleTCittadino> deleTCittadinos) {
		this.deleTCittadinos = deleTCittadinos;
	}

	public DeleTCittadino addDeleTCittadino(DeleTCittadino deleTCittadino) {
		getDeleTCittadinos().add(deleTCittadino);
		deleTCittadino.setDeleDCittadinoDatiStato(this);

		return deleTCittadino;
	}

	public DeleTCittadino removeDeleTCittadino(DeleTCittadino deleTCittadino) {
		getDeleTCittadinos().remove(deleTCittadino);
		deleTCittadino.setDeleDCittadinoDatiStato(null);

		return deleTCittadino;
	}

}