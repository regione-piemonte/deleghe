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
 * The persistent class for the dele_d_dichiarazione_stato database table.
 * 
 */
@Entity
@Table(name="dele_d_dichiarazione_stato")
@NamedQuery(name="DeleDDichiarazioneStato.findAll", query="SELECT d FROM DeleDDichiarazioneStato d")
public class DeleDDichiarazioneStato implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DELE_D_DICHIARAZIONE_STATO_DICSTATOID_GENERATOR", sequenceName="DELE_D_DICHIARAZIONE_STATO_DIC_STATO_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DELE_D_DICHIARAZIONE_STATO_DICSTATOID_GENERATOR")
	@Column(name="dic_stato_id")
	private Integer dicStatoId;

	@Column(name="data_cancellazione")
	private Date dataCancellazione;

	@Column(name="data_creazione")
	private Date dataCreazione;

	@Column(name="data_modifica")
	private Date dataModifica;

	@Column(name="dic_stato_cod")
	private String dicStatoCod;

	@Column(name="dic_stato_desc")
	private String dicStatoDesc;

	@Column(name="login_operazione")
	private String loginOperazione;

	//bi-directional many-to-one association to DeleDRuoloOp
	@ManyToOne
	@JoinColumn(name="ruoloop_id")
	private DeleDRuoloOp deleDRuoloOp;

	//bi-directional many-to-one association to DeleSDichiarazione
	@OneToMany(mappedBy="deleDDichiarazioneStato")
	private List<DeleSDichiarazione> deleSDichiaraziones;

	//bi-directional many-to-one association to DeleTDichiarazione
	@OneToMany(mappedBy="deleDDichiarazioneStato")
	private List<DeleTDichiarazione> deleTDichiaraziones;
	
	//bi-directional many-to-one association to DeleTCriteriDiRicerca
	@OneToMany(mappedBy="deleDDichiarazioneStato")
	private List<DeleTCriteriDiRicerca> deleTCriteriDiRicercas;


	public DeleDDichiarazioneStato() {
	}

	public Integer getDicStatoId() {
		return this.dicStatoId;
	}

	public void setDicStatoId(Integer dicStatoId) {
		this.dicStatoId = dicStatoId;
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

	public String getDicStatoCod() {
		return this.dicStatoCod;
	}

	public void setDicStatoCod(String dicStatoCod) {
		this.dicStatoCod = dicStatoCod;
	}

	public String getDicStatoDesc() {
		return this.dicStatoDesc;
	}

	public void setDicStatoDesc(String dicStatoDesc) {
		this.dicStatoDesc = dicStatoDesc;
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

	public List<DeleSDichiarazione> getDeleSDichiaraziones() {
		return this.deleSDichiaraziones;
	}

	public void setDeleSDichiaraziones(List<DeleSDichiarazione> deleSDichiaraziones) {
		this.deleSDichiaraziones = deleSDichiaraziones;
	}

	public DeleSDichiarazione addDeleSDichiarazione(DeleSDichiarazione deleSDichiarazione) {
		getDeleSDichiaraziones().add(deleSDichiarazione);
		deleSDichiarazione.setDeleDDichiarazioneStato(this);

		return deleSDichiarazione;
	}

	public DeleSDichiarazione removeDeleSDichiarazione(DeleSDichiarazione deleSDichiarazione) {
		getDeleSDichiaraziones().remove(deleSDichiarazione);
		deleSDichiarazione.setDeleDDichiarazioneStato(null);

		return deleSDichiarazione;
	}

	public List<DeleTDichiarazione> getDeleTDichiaraziones() {
		return this.deleTDichiaraziones;
	}

	public void setDeleTDichiaraziones(List<DeleTDichiarazione> deleTDichiaraziones) {
		this.deleTDichiaraziones = deleTDichiaraziones;
	}

	public DeleTDichiarazione addDeleTDichiarazione(DeleTDichiarazione deleTDichiarazione) {
		getDeleTDichiaraziones().add(deleTDichiarazione);
		deleTDichiarazione.setDeleDDichiarazioneStato(this);

		return deleTDichiarazione;
	}

	public DeleTDichiarazione removeDeleTDichiarazione(DeleTDichiarazione deleTDichiarazione) {
		getDeleTDichiaraziones().remove(deleTDichiarazione);
		deleTDichiarazione.setDeleDDichiarazioneStato(null);

		return deleTDichiarazione;
	}

	public List<DeleTCriteriDiRicerca> getDeleTCriteriDiRicercas() {
		return deleTCriteriDiRicercas;
	}

	public void setDeleTCriteriDiRicercas(List<DeleTCriteriDiRicerca> deleTCriteriDiRicercas) {
		this.deleTCriteriDiRicercas = deleTCriteriDiRicercas;
	}
	
	

}