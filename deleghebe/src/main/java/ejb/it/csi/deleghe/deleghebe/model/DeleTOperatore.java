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
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the dele_t_operatore database table.
 * 
 */
@Entity
@Table(name="dele_t_operatore")
@NamedQuery(name="DeleTOperatore.findAll", query="SELECT d FROM DeleTOperatore d")
public class DeleTOperatore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DELE_T_OPERATORE_OPID_GENERATOR", sequenceName="DELE_T_OPERATORE_OP_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DELE_T_OPERATORE_OPID_GENERATOR")
	@Column(name="op_id")
	private Integer opId;

	@Column(name="data_cancellazione")
	private Date dataCancellazione;

	@Column(name="data_creazione")
	private Date dataCreazione;

	@Column(name="data_modifica")
	private Date dataModifica;

	@Column(name="login_operazione")
	private String loginOperazione;

	@Column(name="op_cf")
	private String opCf;

	@Column(name="op_cognome")
	private String opCognome;

	@Column(name="op_nome")
	private String opNome;

	@Column(name="validita_fine")
	private Date validitaFine;

	@Column(name="validita_inizio")
	private Date validitaInizio;

	//bi-directional many-to-one association to DeleROperatoreAsl
	@OneToMany(mappedBy="deleTOperatore")
	private List<DeleROperatoreAsl> deleROperatoreAsls;

	//bi-directional many-to-one association to DeleTCriteriDiRicerca
	@OneToMany(mappedBy="deleTOperatore")
	private List<DeleTCriteriDiRicerca> deleTCriteriDiRicercas;

	public DeleTOperatore() {
	}

	public Integer getOpId() {
		return this.opId;
	}

	public void setOpId(Integer opId) {
		this.opId = opId;
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

	public String getOpCf() {
		return this.opCf;
	}

	public void setOpCf(String opCf) {
		this.opCf = opCf;
	}

	public String getOpCognome() {
		return this.opCognome;
	}

	public void setOpCognome(String opCognome) {
		this.opCognome = opCognome;
	}

	public String getOpNome() {
		return this.opNome;
	}

	public void setOpNome(String opNome) {
		this.opNome = opNome;
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

	public List<DeleROperatoreAsl> getDeleROperatoreAsls() {
		return this.deleROperatoreAsls;
	}

	public void setDeleROperatoreAsls(List<DeleROperatoreAsl> deleROperatoreAsls) {
		this.deleROperatoreAsls = deleROperatoreAsls;
	}

	public DeleROperatoreAsl addDeleROperatoreAsl(DeleROperatoreAsl deleROperatoreAsl) {
		getDeleROperatoreAsls().add(deleROperatoreAsl);
		deleROperatoreAsl.setDeleTOperatore(this);

		return deleROperatoreAsl;
	}

	public DeleROperatoreAsl removeDeleROperatoreAsl(DeleROperatoreAsl deleROperatoreAsl) {
		getDeleROperatoreAsls().remove(deleROperatoreAsl);
		deleROperatoreAsl.setDeleTOperatore(null);

		return deleROperatoreAsl;
	}

	public List<DeleTCriteriDiRicerca> getDeleTCriteriDiRicercas() {
		return deleTCriteriDiRicercas;
	}

	public void setDeleTCriteriDiRicercas(List<DeleTCriteriDiRicerca> deleTCriteriDiRicercas) {
		this.deleTCriteriDiRicercas = deleTCriteriDiRicercas;
	}
}