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
 * The persistent class for the dele_d_dichiarazione_ruolo database table.
 * 
 */
@Entity
@Table(name="dele_d_dichiarazione_ruolo")
@NamedQuery(name="DeleDDichiarazioneRuolo.findAll", query="SELECT d FROM DeleDDichiarazioneRuolo d")
public class DeleDDichiarazioneRuolo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DELE_D_DICHIARAZIONE_RUOLO_DICRUOLOID_GENERATOR", sequenceName="DELE_D_DICHIARAZIONE_RUOLO_DIC_RUOLO_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DELE_D_DICHIARAZIONE_RUOLO_DICRUOLOID_GENERATOR")
	@Column(name="dic_ruolo_id")
	private Integer dicRuoloId;

	@Column(name="data_cancellazione")
	private Date dataCancellazione;

	@Column(name="data_creazione")
	private Date dataCreazione;

	@Column(name="data_modifica")
	private Date dataModifica;

	@Column(name="dic_ruolo_cod")
	private String dicRuoloCod;

	@Column(name="dic_ruolo_desc")
	private String dicRuoloDesc;

	@Column(name="login_operazione")
	private String loginOperazione;

	//bi-directional many-to-one association to DeleDRuoloOp
	@ManyToOne
	@JoinColumn(name="ruoloop_id")
	private DeleDRuoloOp deleDRuoloOp;

	//bi-directional many-to-one association to DeleTDichiarazioneDet
	@OneToMany(mappedBy="deleDDichiarazioneRuolo1")
	private List<DeleTDichiarazioneDet> deleTDichiarazioneDets1;

	//bi-directional many-to-one association to DeleTDichiarazioneDet
	@OneToMany(mappedBy="deleDDichiarazioneRuolo2")
	private List<DeleTDichiarazioneDet> deleTDichiarazioneDets2;

	//bi-directional many-to-one association to DeleTDichiarazioneDet
	@OneToMany(mappedBy="deleDDichiarazioneRuolo")
	private List<DeleTCriteriDiRicerca> deleTCriteriDiRicercas;

	public DeleDDichiarazioneRuolo() {
	}

	public Integer getDicRuoloId() {
		return this.dicRuoloId;
	}

	public void setDicRuoloId(Integer dicRuoloId) {
		this.dicRuoloId = dicRuoloId;
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

	public String getDicRuoloCod() {
		return this.dicRuoloCod;
	}

	public void setDicRuoloCod(String dicRuoloCod) {
		this.dicRuoloCod = dicRuoloCod;
	}

	public String getDicRuoloDesc() {
		return this.dicRuoloDesc;
	}

	public void setDicRuoloDesc(String dicRuoloDesc) {
		this.dicRuoloDesc = dicRuoloDesc;
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

	public List<DeleTDichiarazioneDet> getDeleTDichiarazioneDets1() {
		return this.deleTDichiarazioneDets1;
	}

	public void setDeleTDichiarazioneDets1(List<DeleTDichiarazioneDet> deleTDichiarazioneDets1) {
		this.deleTDichiarazioneDets1 = deleTDichiarazioneDets1;
	}

	public DeleTDichiarazioneDet addDeleTDichiarazioneDets1(DeleTDichiarazioneDet deleTDichiarazioneDets1) {
		getDeleTDichiarazioneDets1().add(deleTDichiarazioneDets1);
		deleTDichiarazioneDets1.setDeleDDichiarazioneRuolo1(this);

		return deleTDichiarazioneDets1;
	}

	public DeleTDichiarazioneDet removeDeleTDichiarazioneDets1(DeleTDichiarazioneDet deleTDichiarazioneDets1) {
		getDeleTDichiarazioneDets1().remove(deleTDichiarazioneDets1);
		deleTDichiarazioneDets1.setDeleDDichiarazioneRuolo1(null);

		return deleTDichiarazioneDets1;
	}

	public List<DeleTDichiarazioneDet> getDeleTDichiarazioneDets2() {
		return this.deleTDichiarazioneDets2;
	}

	public void setDeleTDichiarazioneDets2(List<DeleTDichiarazioneDet> deleTDichiarazioneDets2) {
		this.deleTDichiarazioneDets2 = deleTDichiarazioneDets2;
	}

	public DeleTDichiarazioneDet addDeleTDichiarazioneDets2(DeleTDichiarazioneDet deleTDichiarazioneDets2) {
		getDeleTDichiarazioneDets2().add(deleTDichiarazioneDets2);
		deleTDichiarazioneDets2.setDeleDDichiarazioneRuolo2(this);

		return deleTDichiarazioneDets2;
	}

	public DeleTDichiarazioneDet removeDeleTDichiarazioneDets2(DeleTDichiarazioneDet deleTDichiarazioneDets2) {
		getDeleTDichiarazioneDets2().remove(deleTDichiarazioneDets2);
		deleTDichiarazioneDets2.setDeleDDichiarazioneRuolo2(null);

		return deleTDichiarazioneDets2;
	}

	public List<DeleTCriteriDiRicerca> getDeleTCriteriDiRicercas() {
		return deleTCriteriDiRicercas;
	}

	public void setDeleTCriteriDiRicercas(List<DeleTCriteriDiRicerca> deleTCriteriDiRicercas) {
		this.deleTCriteriDiRicercas = deleTCriteriDiRicercas;
	}

}