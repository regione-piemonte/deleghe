/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
 * The persistent class for the csi_log_audit database table.
 * 
 */
@Entity
@Table(name="csi_log_audit")
@NamedQuery(name="CsiLogAudit.findAll", query="SELECT c FROM CsiLogAudit c")
public class CsiLogAudit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CSI_LOG_AUDIT_AUDITID_GENERATOR", sequenceName="CSI_LOG_AUDIT_AUDIT_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CSI_LOG_AUDIT_AUDITID_GENERATOR")
	@Column(name="audit_id")
	private Integer auditId;

	@Column(name="data_ora")
	private Date dataOra;

	@Column(name="id_app")
	private String idApp;

	private String idrequest;

	@Column(name="ip_address")
	private String ipAddress;

	@Column(name="key_oper")
	private String keyOper;

	@Column(name="ogg_oper")
	private String oggOper;

	private String operazione;

	private String utente;

	@Column(name = "uuid")
	@org.hibernate.annotations.Type(type="pg-uuid")
	private UUID uuid;

	//bi-directional many-to-one association to DeleSDelegaServizio
	@OneToMany(mappedBy="csiLogAudit")
	private List<DeleSDelegaServizio> deleSDelegaServizios;

	//bi-directional many-to-one association to DeleTCittadino
	@OneToMany(mappedBy="csiLogAudit")
	private List<DeleTCittadino> deleTCittadinos;

	//bi-directional many-to-one association to DeleTDelegaServizio
	@OneToMany(mappedBy="csiLogAudit")
	private List<DeleTDelegaServizio> deleTDelegaServizios;

	public CsiLogAudit() {
	}

	public Integer getAuditId() {
		return this.auditId;
	}

	public void setAuditId(Integer auditId) {
		this.auditId = auditId;
	}

	public Date getDataOra() {
		return this.dataOra;
	}

	public void setDataOra(Date dataOra) {
		this.dataOra = dataOra;
	}

	public String getIdApp() {
		return this.idApp;
	}

	public void setIdApp(String idApp) {
		this.idApp = idApp;
	}

	public String getIdrequest() {
		return this.idrequest;
	}

	public void setIdrequest(String idrequest) {
		this.idrequest = idrequest;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getKeyOper() {
		return this.keyOper;
	}

	public void setKeyOper(String keyOper) {
		this.keyOper = keyOper;
	}

	public String getOggOper() {
		return this.oggOper;
	}

	public void setOggOper(String oggOper) {
		this.oggOper = oggOper;
	}

	public String getOperazione() {
		return this.operazione;
	}

	public void setOperazione(String operazione) {
		this.operazione = operazione;
	}

	public String getUtente() {
		return this.utente;
	}

	public void setUtente(String utente) {
		this.utente = utente;
	}

	public UUID getUuid() {
		return this.uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public List<DeleSDelegaServizio> getDeleSDelegaServizios() {
		return this.deleSDelegaServizios;
	}

	public void setDeleSDelegaServizios(List<DeleSDelegaServizio> deleSDelegaServizios) {
		this.deleSDelegaServizios = deleSDelegaServizios;
	}

	public DeleSDelegaServizio addDeleSDelegaServizio(DeleSDelegaServizio deleSDelegaServizio) {
		getDeleSDelegaServizios().add(deleSDelegaServizio);
		deleSDelegaServizio.setCsiLogAudit(this);

		return deleSDelegaServizio;
	}

	public DeleSDelegaServizio removeDeleSDelegaServizio(DeleSDelegaServizio deleSDelegaServizio) {
		getDeleSDelegaServizios().remove(deleSDelegaServizio);
		deleSDelegaServizio.setCsiLogAudit(null);

		return deleSDelegaServizio;
	}

	public List<DeleTCittadino> getDeleTCittadinos() {
		return this.deleTCittadinos;
	}

	public void setDeleTCittadinos(List<DeleTCittadino> deleTCittadinos) {
		this.deleTCittadinos = deleTCittadinos;
	}

	public DeleTCittadino addDeleTCittadino(DeleTCittadino deleTCittadino) {
		getDeleTCittadinos().add(deleTCittadino);
		deleTCittadino.setCsiLogAudit(this);

		return deleTCittadino;
	}

	public DeleTCittadino removeDeleTCittadino(DeleTCittadino deleTCittadino) {
		getDeleTCittadinos().remove(deleTCittadino);
		deleTCittadino.setCsiLogAudit(null);

		return deleTCittadino;
	}

	public List<DeleTDelegaServizio> getDeleTDelegaServizios() {
		return this.deleTDelegaServizios;
	}

	public void setDeleTDelegaServizios(List<DeleTDelegaServizio> deleTDelegaServizios) {
		this.deleTDelegaServizios = deleTDelegaServizios;
	}

	public DeleTDelegaServizio addDeleTDelegaServizio(DeleTDelegaServizio deleTDelegaServizio) {
		getDeleTDelegaServizios().add(deleTDelegaServizio);
		deleTDelegaServizio.setCsiLogAudit(this);

		return deleTDelegaServizio;
	}

	public DeleTDelegaServizio removeDeleTDelegaServizio(DeleTDelegaServizio deleTDelegaServizio) {
		getDeleTDelegaServizios().remove(deleTDelegaServizio);
		deleTDelegaServizio.setCsiLogAudit(null);

		return deleTDelegaServizio;
	}

}