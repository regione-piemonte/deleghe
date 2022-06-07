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
 * The persistent class for the dele_s_cittadino database table.
 * 
 */
@Entity
@Table(name="dele_s_cittadino")
@NamedQuery(name="DeleSCittadino.findAll", query="SELECT d FROM DeleSCittadino d")
public class DeleSCittadino implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DELE_S_CITTADINO_CITSID_GENERATOR", sequenceName="DELE_S_CITTADINO_CITS_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DELE_S_CITTADINO_CITSID_GENERATOR")
	@Column(name="cits_id")
	private String citsId;

	@Column(name="audit_id")
	private Integer auditId;

	@Column(name="cit_asl")
	private String citAsl;

	@Column(name="cit_auraid")
	private String citAuraid;

	@Column(name="cit_cf")
	private String citCf;

	@Column(name="cit_cognome")
	private String citCognome;

	@Column(name="cit_nascita_comune")
	private String citNascitaComune;

	@Column(name="cit_nascita_data")
	private Date citNascitaData;

	@Column(name="cit_nome")
	private String citNome;

	@Column(name="cit_sesso")
	private String citSesso;

	@Column(name="data_cancellazione")
	private Date dataCancellazione;

	@Column(name="data_creazione")
	private Date dataCreazione;

	@Column(name="data_modifica")
	private Date dataModifica;

	@Column(name="login_operazione")
	private String loginOperazione;


	@Column(name="verificato_asl")
	private Boolean verificatoAsl;

	//bi-directional many-to-one association to DeleDCittadinoDatiStato
	@ManyToOne
	@JoinColumn(name="citdstato_id")
	private DeleDCittadinoDatiStato deleDCittadinoDatiStato;

	//bi-directional many-to-one association to DeleDRuoloOp
	@ManyToOne
	@JoinColumn(name="ruoloop_id")
	private DeleDRuoloOp deleDRuoloOp;

	//bi-directional many-to-one association to DeleTCittadino
	@ManyToOne
	@JoinColumn(name="cit_id")
	private DeleTCittadino deleTCittadino;

	//bi-directional many-to-one association to DeleTAsl
	@ManyToOne
	@JoinColumn(name="cit_asl_id")
	private DeleTAsl deleTAsl;

	//bi-directional many-to-one association to DeleTDocumento
	@ManyToOne
	@JoinColumn(name="doc_id")
	private DeleTDocumento deleTDocumento;

	//bi-directional many-to-one association to DeleDCittadinoUtenzaStato
	@ManyToOne
	@JoinColumn(name="citustato_id")
	private DeleDCittadinoUtenzaStato deleDCittadinoUtenzaStato;


	public DeleSCittadino() {
	}

	public String getCitsId() {
		return this.citsId;
	}

	public void setCitsId(String citsId) {
		this.citsId = citsId;
	}

	public Integer getAuditId() {
		return this.auditId;
	}

	public void setAuditId(Integer auditId) {
		this.auditId = auditId;
	}

	public String getCitAsl() {
		return this.citAsl;
	}

	public void setCitAsl(String citAsl) {
		this.citAsl = citAsl;
	}

	public String getCitAuraid() {
		return this.citAuraid;
	}

	public void setCitAuraid(String citAuraid) {
		this.citAuraid = citAuraid;
	}

	public String getCitCf() {
		return this.citCf;
	}

	public void setCitCf(String citCf) {
		this.citCf = citCf;
	}

	public String getCitCognome() {
		return this.citCognome;
	}

	public void setCitCognome(String citCognome) {
		this.citCognome = citCognome;
	}

	public String getCitNascitaComune() {
		return this.citNascitaComune;
	}

	public void setCitNascitaComune(String citNascitaComune) {
		this.citNascitaComune = citNascitaComune;
	}

	public Date getCitNascitaData() {
		return this.citNascitaData;
	}

	public void setCitNascitaData(Date citNascitaData) {
		this.citNascitaData = citNascitaData;
	}

	public String getCitNome() {
		return this.citNome;
	}

	public void setCitNome(String citNome) {
		this.citNome = citNome;
	}

	public String getCitSesso() {
		return this.citSesso;
	}

	public void setCitSesso(String citSesso) {
		this.citSesso = citSesso;
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

	public DeleDCittadinoDatiStato getDeleDCittadinoDatiStato() {
		return this.deleDCittadinoDatiStato;
	}

	public void setDeleDCittadinoDatiStato(DeleDCittadinoDatiStato deleDCittadinoDatiStato) {
		this.deleDCittadinoDatiStato = deleDCittadinoDatiStato;
	}

	public DeleDRuoloOp getDeleDRuoloOp() {
		return this.deleDRuoloOp;
	}

	public void setDeleDRuoloOp(DeleDRuoloOp deleDRuoloOp) {
		this.deleDRuoloOp = deleDRuoloOp;
	}

	public DeleTCittadino getDeleTCittadino() {
		return this.deleTCittadino;
	}

	public void setDeleTCittadino(DeleTCittadino deleTCittadino) {
		this.deleTCittadino = deleTCittadino;
	}

	public Boolean getVerificatoAsl() {
		return verificatoAsl;
	}

	public void setVerificatoAsl(Boolean verificatoAsl) {
		this.verificatoAsl = verificatoAsl;
	}

	public DeleTAsl getDeleTAsl() {
		return deleTAsl;
	}

	public void setDeleTAsl(DeleTAsl deleTAsl) {
		this.deleTAsl = deleTAsl;
	}

	public DeleTDocumento getDeleTDocumento() {
		return deleTDocumento;
	}

	public void setDeleTDocumento(DeleTDocumento deleTDocumento) {
		this.deleTDocumento = deleTDocumento;
	}

	public DeleDCittadinoUtenzaStato getDeleDCittadinoUtenzaStato() {
		return deleDCittadinoUtenzaStato;
	}

	public void setDeleDCittadinoUtenzaStato(DeleDCittadinoUtenzaStato deleDCittadinoUtenzaStato) {
		this.deleDCittadinoUtenzaStato = deleDCittadinoUtenzaStato;
	}


}