/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the dele_t_documento database table.
 * 
 */
@Entity
@Table(name="dele_t_documento")
@NamedQuery(name="DeleTDocumento.findAll", query="SELECT d FROM DeleTDocumento d")
public class DeleTDocumento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DELE_T_DOCUMENTO_DOCID_GENERATOR", sequenceName="DELE_T_DOCUMENTO_DOC_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DELE_T_DOCUMENTO_DOCID_GENERATOR")
	@Column(name="doc_id")
	private Integer docId;

	@Column(name="data_cancellazione")
	private Date dataCancellazione;

	@Column(name="data_creazione")
	private Date dataCreazione;

	@Column(name="data_modifica")
	private Date dataModifica;

	@Column(name="doc_desc")
	private String docDesc;

	@Column(name="login_operazione")
	private String loginOperazione;
	
	//NUOVI CAMPI: data_rilascio; numero_documento;autorita; dataScadenzaDoc 
	@Column(name="data_rilascio")
	private Date dataRilascio;
	
	@Column(name="numero_documento")
	private String numeroDocumento;
	
	@Column(name="autorita")
	private String autorita;
	
	@Column(name="data_scadenza_doc")
	private Date dataScadenzaDoc;

	
	//bi-directional many-to-one association to DeleTDichiarazioneDet
	@OneToMany(mappedBy="deleTDocumento")
	private List<DeleTDichiarazioneDet> deleTDichiarazioneDets;

	//bi-directional many-to-one association to DeleTCittadino
	@OneToMany(mappedBy="deleTDocumento")
	private List<DeleTCittadino> deleTCittadinos;

	//bi-directional many-to-one association to DeleSCittadino
	@OneToMany(mappedBy="deleTDocumento")
	private List<DeleSCittadino> deleSCittadinos;

	//bi-directional many-to-one association to DeleDDocumentoTipo
	@ManyToOne
	@JoinColumn(name="doc_tipo_id")
	private DeleDDocumentoTipo deleDDocumentoTipo;


	//bi-directional many-to-one association to DeleDRuoloOp
	@ManyToOne
	@JoinColumn(name="ruoloop_id")
	private DeleDRuoloOp deleDRuoloOp;

	//bi-directional many-to-one association to DeleTFile
	//@ManyToOne
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="file_id")
	private DeleTFile deleTFile;

	

	public DeleTDocumento() {
	}

	public Integer getDocId() {
		return this.docId;
	}

	public void setDocId(Integer docId) {
		this.docId = docId;
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

	public String getDocDesc() {
		return this.docDesc;
	}

	public void setDocDesc(String docDesc) {
		this.docDesc = docDesc;
	}

	public String getLoginOperazione() {
		return this.loginOperazione;
	}

	public void setLoginOperazione(String loginOperazione) {
		this.loginOperazione = loginOperazione;
	}

	public Date getDataScadenzaDoc() {
		return dataScadenzaDoc;
	}

	public void setDataScadenzaDoc(Date dataScadenzaDoc) {
		this.dataScadenzaDoc = dataScadenzaDoc;
	}

	public Date getDataRilascio() {
		return dataRilascio;
	}

	public void setDataRilascio(Date dataRilascio) {
		this.dataRilascio = dataRilascio;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getAutorita() {
		return autorita;
	}

	public void setAutorita(String autorita) {
		this.autorita = autorita;
	}

	public List<DeleTDichiarazioneDet> getDeleTDichiarazioneDets() {
		return this.deleTDichiarazioneDets;
	}

	public void setDeleTDichiarazioneDets(List<DeleTDichiarazioneDet> deleTDichiarazioneDets) {
		this.deleTDichiarazioneDets = deleTDichiarazioneDets;
	}

	public DeleTDichiarazioneDet addDeleTDichiarazioneDet(DeleTDichiarazioneDet deleTDichiarazioneDet) {
		getDeleTDichiarazioneDets().add(deleTDichiarazioneDet);
		deleTDichiarazioneDet.setDeleTDocumento(this);

		return deleTDichiarazioneDet;
	}

	public DeleTDichiarazioneDet removeDeleTDichiarazioneDet(DeleTDichiarazioneDet deleTDichiarazioneDet) {
		getDeleTDichiarazioneDets().remove(deleTDichiarazioneDet);
		deleTDichiarazioneDet.setDeleTDocumento(null);

		return deleTDichiarazioneDet;
	}


	public DeleDDocumentoTipo getDeleDDocumentoTipo() {
		return this.deleDDocumentoTipo;
	}

	public void setDeleDDocumentoTipo(DeleDDocumentoTipo deleDDocumentoTipo) {
		this.deleDDocumentoTipo = deleDDocumentoTipo;
	}

	public DeleDRuoloOp getDeleDRuoloOp() {
		return this.deleDRuoloOp;
	}

	public void setDeleDRuoloOp(DeleDRuoloOp deleDRuoloOp) {
		this.deleDRuoloOp = deleDRuoloOp;
	}

	public DeleTFile getDeleTFile() {
		return this.deleTFile;
	}

	public void setDeleTFile(DeleTFile deleTFile) {
		this.deleTFile = deleTFile;
	}

	public List<DeleTCittadino> getDeleTCittadinos() {
		return deleTCittadinos;
	}

	public void setDeleTCittadinos(List<DeleTCittadino> deleTCittadinos) {
		this.deleTCittadinos = deleTCittadinos;
	}

	public DeleTCittadino addDeleTCittadino(DeleTCittadino deleTCittadino) {
		getDeleTCittadinos().add(deleTCittadino);
		deleTCittadino.setDeleTDocumento(this);

		return deleTCittadino;
	}

	public DeleTCittadino removeDeleTCittadino(DeleTCittadino deleTCittadino) {
		getDeleTCittadinos().remove(deleTCittadino);
		deleTCittadino.setDeleTDocumento(null);

		return deleTCittadino;
	}

	public List<DeleSCittadino> getDeleSCittadinos() {
		return deleSCittadinos;
	}

	public void setDeleSCittadinos(List<DeleSCittadino> deleSCittadinos) {
		this.deleSCittadinos = deleSCittadinos;
	}

	public DeleSCittadino addDeleSCittadino(DeleSCittadino deleSCittadino) {
		getDeleSCittadinos().add(deleSCittadino);
		deleSCittadino.setDeleTDocumento(this);

		return deleSCittadino;
	}

	public DeleSCittadino removeDeleSCittadino(DeleSCittadino deleSCittadino) {
		getDeleSCittadinos().remove(deleSCittadino);
		deleSCittadino.setDeleTDocumento(null);

		return deleSCittadino;
	}

	
}