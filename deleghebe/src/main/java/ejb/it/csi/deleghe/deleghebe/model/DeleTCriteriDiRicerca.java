/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.model;

import java.io.Serializable;
import java.sql.Timestamp;

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
 * The persistent class for the dele_t_criteri_di_ricerca database table.
 * 
 */
@Entity
@Table(name="dele_t_criteri_di_ricerca")
@NamedQuery(name="DeleTCriteriDiRicerca.findAll", query="SELECT d FROM DeleTCriteriDiRicerca d")
public class DeleTCriteriDiRicerca implements Serializable {
	private static final long serialVersionUID = 1L;


    @Id
    @SequenceGenerator(name="DELE_T_CRITERI_DI_RICERCA_RCID_GENERATOR", sequenceName="DELE_T_CRITERI_DI_RICERCA_RC_ID_SEQ", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DELE_T_CRITERI_DI_RICERCA_RCID_GENERATOR")
    @Column(name="cr_id", unique=true, nullable=false)
    private Integer rcId;

	@Column(name="codice_fiscale_delegante")
	private String codiceFiscaleDelegante;

	@Column(name="codice_fiscale_delegato")
	private String codiceFiscaleDelegato;

	@Column(name="data_cancellazione")
	private Timestamp dataCancellazione;

	@Column(name="data_creazione")
	private Timestamp dataCreazione;

	@Column(name="data_inserimento_a")
	private Timestamp dataInserimentoA;

	@Column(name="data_inserimento_da")
	private Timestamp dataInserimentoDa;

	@Column(name="data_modifica")
	private Timestamp dataModifica;

    //bi-directional many-to-one association to DeleDRuoloOp
    @ManyToOne
    @JoinColumn(name="dic_ruolo_id ")
    private DeleDDichiarazioneRuolo deleDDichiarazioneRuolo;

	@Column(name="login_operazione")
	private String loginOperazione;

	//bi-directional many-to-one association to DeleDDichiarazioneStato
	@ManyToOne
	@JoinColumn(name="dic_stato_id")
	private DeleDDichiarazioneStato deleDDichiarazioneStato;

	//bi-directional many-to-one association to DeleDDichiarazioneTipo
	@ManyToOne
	@JoinColumn(name="dic_tipo_id")
	private DeleDDichiarazioneTipo deleDDichiarazioneTipo;

	//bi-directional many-to-one association to DeleTOperatore
	@ManyToOne
	@JoinColumn(name="op_id")
	private DeleTOperatore deleTOperatore;

	public DeleTCriteriDiRicerca() {
	}

	public String getCodiceFiscaleDelegante() {
		return this.codiceFiscaleDelegante;
	}

	public void setCodiceFiscaleDelegante(String codiceFiscaleDelegante) {
		this.codiceFiscaleDelegante = codiceFiscaleDelegante;
	}

	public String getCodiceFiscaleDelegato() {
		return this.codiceFiscaleDelegato;
	}

	public void setCodiceFiscaleDelegato(String codiceFiscaleDelegato) {
		this.codiceFiscaleDelegato = codiceFiscaleDelegato;
	}

	public Timestamp getDataCancellazione() {
		return this.dataCancellazione;
	}

	public void setDataCancellazione(Timestamp dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public Timestamp getDataCreazione() {
		return this.dataCreazione;
	}

	public void setDataCreazione(Timestamp dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public Timestamp getDataInserimentoA() {
		return this.dataInserimentoA;
	}

	public void setDataInserimentoA(Timestamp dataInserimentoA) {
		this.dataInserimentoA = dataInserimentoA;
	}

	public Timestamp getDataInserimentoDa() {
		return this.dataInserimentoDa;
	}

	public void setDataInserimentoDa(Timestamp dataInserimentoDa) {
		this.dataInserimentoDa = dataInserimentoDa;
	}

	public Timestamp getDataModifica() {
		return this.dataModifica;
	}

	public void setDataModifica(Timestamp dataModifica) {
		this.dataModifica = dataModifica;
	}


	public DeleDDichiarazioneRuolo getDeleDDichiarazioneRuolo() {
		return deleDDichiarazioneRuolo;
	}

	public void setDeleDDichiarazioneRuolo(DeleDDichiarazioneRuolo deleDDichiarazioneRuolo) {
		this.deleDDichiarazioneRuolo = deleDDichiarazioneRuolo;
	}

	public String getLoginOperazione() {
		return this.loginOperazione;
	}

	public void setLoginOperazione(String loginOperazione) {
		this.loginOperazione = loginOperazione;
	}

	public Integer getRcId() {
		return rcId;
	}

	public void setRcId(Integer rcId) {
		this.rcId = rcId;
	}

	public DeleDDichiarazioneStato getDeleDDichiarazioneStato() {
		return deleDDichiarazioneStato;
	}

	public void setDeleDDichiarazioneStato(DeleDDichiarazioneStato deleDDichiarazioneStato) {
		this.deleDDichiarazioneStato = deleDDichiarazioneStato;
	}

	public DeleDDichiarazioneTipo getDeleDDichiarazioneTipo() {
		return deleDDichiarazioneTipo;
	}

	public void setDeleDDichiarazioneTipo(DeleDDichiarazioneTipo deleDDichiarazioneTipo) {
		this.deleDDichiarazioneTipo = deleDDichiarazioneTipo;
	}

	public DeleTOperatore getDeleTOperatore() {
		return deleTOperatore;
	}

	public void setDeleTOperatore(DeleTOperatore deleTOperatore) {
		this.deleTOperatore = deleTOperatore;
	}


}