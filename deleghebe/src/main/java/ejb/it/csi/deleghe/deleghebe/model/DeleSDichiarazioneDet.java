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
 * The persistent class for the dele_s_dichiarazione_det database table.
 * 
 */
@Entity
@Table(name="dele_s_dichiarazione_det")
@NamedQuery(name="DeleSDichiarazioneDet.findAll", query="SELECT d FROM DeleSDichiarazioneDet d")
public class DeleSDichiarazioneDet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DELE_S_DICHIARAZIONE_DET_DICDETSID_GENERATOR", sequenceName="DELE_S_DICHIARAZIONE_DET_DICDETS_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DELE_S_DICHIARAZIONE_DET_DICDETSID_GENERATOR")
	@Column(name="dicdets_id")
	private Integer dicdetsId;

	@Column(name="cit_id_1")
	private Integer citId1;

	@Column(name="cit_id_2")
	private Integer citId2;

	@Column(name="data_cancellazione")
	private Date dataCancellazione;

	@Column(name="data_creazione")
	private Date dataCreazione;

	@Column(name="data_modifica")
	private Date dataModifica;

	@Column(name="dic_id")
	private Integer dicId;

	@Column(name="dic_ruolo_id_1")
	private Integer dicRuoloId1;

	@Column(name="dic_ruolo_id_2")
	private Integer dicRuoloId2;

	@Column(name="login_operazione")
	private String loginOperazione;

	@Column(name="note_revoca_blocco")
	private String noteRevocaBlocco;

	@Column(name="validita_fine")
	private Date validitaFine;

	@Column(name="validita_inizio")
	private Date validitaInizio;

	//bi-directional many-to-one association to DeleDDichiarazioneDetStato
	@ManyToOne
	@JoinColumn(name="dicdet_stato_id")
	private DeleDDichiarazioneDetStato deleDDichiarazioneDetStato;

	//bi-directional many-to-one association to DeleDRuoloOp
	@ManyToOne
	@JoinColumn(name="ruoloop_id")
	private DeleDRuoloOp deleDRuoloOp;

	//bi-directional many-to-one association to DeleTDichiarazioneDet
	@ManyToOne
	@JoinColumn(name="dicdet_id")
	private DeleTDichiarazioneDet deleTDichiarazioneDet;

	@Column(name="nota_motivazione")
	private String notaMotivazione;

	//bi-directional many-to-one association to DeleDDelegaTipo
	@ManyToOne
	@JoinColumn(name="enum_id")
	private DeleDEnumerazione deleDEnumerazione;

	public DeleSDichiarazioneDet() {
	}

	public Integer getDicdetsId() {
		return this.dicdetsId;
	}

	public void setDicdetsId(Integer dicdetsId) {
		this.dicdetsId = dicdetsId;
	}

	public Integer getCitId1() {
		return this.citId1;
	}

	public void setCitId1(Integer citId1) {
		this.citId1 = citId1;
	}

	public Integer getCitId2() {
		return this.citId2;
	}

	public void setCitId2(Integer citId2) {
		this.citId2 = citId2;
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

	public Integer getDicId() {
		return this.dicId;
	}

	public void setDicId(Integer dicId) {
		this.dicId = dicId;
	}

	public Integer getDicRuoloId1() {
		return this.dicRuoloId1;
	}

	public void setDicRuoloId1(Integer dicRuoloId1) {
		this.dicRuoloId1 = dicRuoloId1;
	}

	public Integer getDicRuoloId2() {
		return this.dicRuoloId2;
	}

	public void setDicRuoloId2(Integer dicRuoloId2) {
		this.dicRuoloId2 = dicRuoloId2;
	}

	public String getLoginOperazione() {
		return this.loginOperazione;
	}

	public void setLoginOperazione(String loginOperazione) {
		this.loginOperazione = loginOperazione;
	}

	public String getNoteRevocaBlocco() {
		return this.noteRevocaBlocco;
	}

	public void setNoteRevocaBlocco(String noteRevocaBlocco) {
		this.noteRevocaBlocco = noteRevocaBlocco;
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

	public DeleDDichiarazioneDetStato getDeleDDichiarazioneDetStato() {
		return this.deleDDichiarazioneDetStato;
	}

	public void setDeleDDichiarazioneDetStato(DeleDDichiarazioneDetStato deleDDichiarazioneDetStato) {
		this.deleDDichiarazioneDetStato = deleDDichiarazioneDetStato;
	}

	public DeleDRuoloOp getDeleDRuoloOp() {
		return this.deleDRuoloOp;
	}

	public void setDeleDRuoloOp(DeleDRuoloOp deleDRuoloOp) {
		this.deleDRuoloOp = deleDRuoloOp;
	}

	public DeleTDichiarazioneDet getDeleTDichiarazioneDet() {
		return this.deleTDichiarazioneDet;
	}

	public void setDeleTDichiarazioneDet(DeleTDichiarazioneDet deleTDichiarazioneDet) {
		this.deleTDichiarazioneDet = deleTDichiarazioneDet;
	}

	public String getNotaMotivazione() {
		return notaMotivazione;
	}

	public void setNotaMotivazione(String notaMotivazione) {
		this.notaMotivazione = notaMotivazione;
	}

	public DeleDEnumerazione getDeleDEnumerazione() {
		return deleDEnumerazione;
	}

	public void setDeleDEnumerazione(DeleDEnumerazione deleDEnumerazione) {
		this.deleDEnumerazione = deleDEnumerazione;
	}
}