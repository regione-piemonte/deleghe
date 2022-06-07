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
 * The persistent class for the dele_t_asl database table.
 * 
 */
@Entity
@Table(name="dele_t_asl")
@NamedQuery(name="DeleTAsl.findAll", query="SELECT d FROM DeleTAsl d")
public class DeleTAsl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DELE_T_ASL_ASLID_GENERATOR", sequenceName="DELE_T_ASL_ASL_ID_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DELE_T_ASL_ASLID_GENERATOR")
	@Column(name="asl_id")
	private Integer aslId;

	@Column(name="asl_code")
	private String aslCode;

	@Column(name="asl_desc")
	private String aslDesc;

	@Column(name="data_cancellazione")
	private Date dataCancellazione;

	@Column(name="data_creazione")
	private Date dataCreazione;

	@Column(name="data_modifica")
	private Date dataModifica;

	@Column(name="login_operazione")
	private String loginOperazione;

	@Column(name="validita_fine")
	private Date validitaFine;

	@Column(name="validita_inizio")
	private Date validitaInizio;

	//bi-directional many-to-one association to DeleROperatoreAsl
	@OneToMany(mappedBy="deleTAsl")
	private List<DeleROperatoreAsl> deleROperatoreAsls;

	//bi-directional many-to-one association to DeleTCittadino
	@OneToMany(mappedBy="deleTAsl")
	private List<DeleTCittadino> deleTCittadinos;

	//bi-directional many-to-one association to DeleTCittadino
	@OneToMany(mappedBy="deleTAsl")
	private List<DeleSCittadino> deleSCittadinos;

	public DeleTAsl() {
	}

	public Integer getAslId() {
		return this.aslId;
	}

	public void setAslId(Integer aslId) {
		this.aslId = aslId;
	}

	public String getAslCode() {
		return this.aslCode;
	}

	public void setAslCode(String aslCode) {
		this.aslCode = aslCode;
	}

	public String getAslDesc() {
		return this.aslDesc;
	}

	public void setAslDesc(String aslDesc) {
		this.aslDesc = aslDesc;
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
		deleROperatoreAsl.setDeleTAsl(this);

		return deleROperatoreAsl;
	}

	public DeleROperatoreAsl removeDeleROperatoreAsl(DeleROperatoreAsl deleROperatoreAsl) {
		getDeleROperatoreAsls().remove(deleROperatoreAsl);
		deleROperatoreAsl.setDeleTAsl(null);

		return deleROperatoreAsl;
	}

	public List<DeleTCittadino> getDeleTCittadinos() {
		return deleTCittadinos;
	}

	public void setDeleTCittadinos(List<DeleTCittadino> deleTCittadinos) {
		this.deleTCittadinos = deleTCittadinos;
	}

	public List<DeleSCittadino> getDeleSCittadinos() {
		return deleSCittadinos;
	}

	public void setDeleSCittadinos(List<DeleSCittadino> deleSCittadinos) {
		this.deleSCittadinos = deleSCittadinos;
	}

}