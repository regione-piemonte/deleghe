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
 * The persistent class for the dele_d_dichiarazione_det_stato database table.
 * 
 */
@Entity
@Table(name="dele_d_dichiarazione_det_stato")
@NamedQuery(name="DeleDDichiarazioneDetStato.findAll", query="SELECT d FROM DeleDDichiarazioneDetStato d")
public class DeleDDichiarazioneDetStato implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DELE_D_DICHIARAZIONE_DET_STATO_DICDETSTATOID_GENERATOR", sequenceName="DELE_D_DICHIARAZIONE_DET_STATO_DICDET_STATO_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DELE_D_DICHIARAZIONE_DET_STATO_DICDETSTATOID_GENERATOR")
	@Column(name="dicdet_stato_id")
	private Integer dicdetStatoId;

	@Column(name="data_cancellazione")
	private Date dataCancellazione;

	@Column(name="data_creazione")
	private Date dataCreazione;

	@Column(name="data_modifica")
	private Date dataModifica;

	@Column(name="dicdet_stato_cod")
	private String dicdetStatoCod;

	@Column(name="dicdet_stato_desc")
	private String dicdetStatoDesc;

	@Column(name="login_operazione")
	private String loginOperazione;

	//bi-directional many-to-one association to DeleDRuoloOp
	@ManyToOne
	@JoinColumn(name="ruoloop_id")
	private DeleDRuoloOp deleDRuoloOp;

	//bi-directional many-to-one association to DeleSDichiarazioneDet
	@OneToMany(mappedBy="deleDDichiarazioneDetStato")
	private List<DeleSDichiarazioneDet> deleSDichiarazioneDets;

	//bi-directional many-to-one association to DeleTDichiarazioneDet
	@OneToMany(mappedBy="deleDDichiarazioneDetStato")
	private List<DeleTDichiarazioneDet> deleTDichiarazioneDets;

	public DeleDDichiarazioneDetStato() {
	}

	public Integer getDicdetStatoId() {
		return this.dicdetStatoId;
	}

	public void setDicdetStatoId(Integer dicdetStatoId) {
		this.dicdetStatoId = dicdetStatoId;
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

	public String getDicdetStatoCod() {
		return this.dicdetStatoCod;
	}

	public void setDicdetStatoCod(String dicdetStatoCod) {
		this.dicdetStatoCod = dicdetStatoCod;
	}

	public String getDicdetStatoDesc() {
		return this.dicdetStatoDesc;
	}

	public void setDicdetStatoDesc(String dicdetStatoDesc) {
		this.dicdetStatoDesc = dicdetStatoDesc;
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

	public List<DeleSDichiarazioneDet> getDeleSDichiarazioneDets() {
		return this.deleSDichiarazioneDets;
	}

	public void setDeleSDichiarazioneDets(List<DeleSDichiarazioneDet> deleSDichiarazioneDets) {
		this.deleSDichiarazioneDets = deleSDichiarazioneDets;
	}

	public DeleSDichiarazioneDet addDeleSDichiarazioneDet(DeleSDichiarazioneDet deleSDichiarazioneDet) {
		getDeleSDichiarazioneDets().add(deleSDichiarazioneDet);
		deleSDichiarazioneDet.setDeleDDichiarazioneDetStato(this);

		return deleSDichiarazioneDet;
	}

	public DeleSDichiarazioneDet removeDeleSDichiarazioneDet(DeleSDichiarazioneDet deleSDichiarazioneDet) {
		getDeleSDichiarazioneDets().remove(deleSDichiarazioneDet);
		deleSDichiarazioneDet.setDeleDDichiarazioneDetStato(null);

		return deleSDichiarazioneDet;
	}

	public List<DeleTDichiarazioneDet> getDeleTDichiarazioneDets() {
		return this.deleTDichiarazioneDets;
	}

	public void setDeleTDichiarazioneDets(List<DeleTDichiarazioneDet> deleTDichiarazioneDets) {
		this.deleTDichiarazioneDets = deleTDichiarazioneDets;
	}

	public DeleTDichiarazioneDet addDeleTDichiarazioneDet(DeleTDichiarazioneDet deleTDichiarazioneDet) {
		getDeleTDichiarazioneDets().add(deleTDichiarazioneDet);
		deleTDichiarazioneDet.setDeleDDichiarazioneDetStato(this);

		return deleTDichiarazioneDet;
	}

	public DeleTDichiarazioneDet removeDeleTDichiarazioneDet(DeleTDichiarazioneDet deleTDichiarazioneDet) {
		getDeleTDichiarazioneDets().remove(deleTDichiarazioneDet);
		deleTDichiarazioneDet.setDeleDDichiarazioneDetStato(null);

		return deleTDichiarazioneDet;
	}

}