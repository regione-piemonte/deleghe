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
 * The persistent class for the dele_d_documento_tipo database table.
 * 
 */
@Entity
@Table(name="dele_d_documento_tipo")
@NamedQuery(name="DeleDDocumentoTipo.findAll", query="SELECT d FROM DeleDDocumentoTipo d")
public class DeleDDocumentoTipo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DELE_D_DOCUMENTO_TIPO_DOCTIPOID_GENERATOR", sequenceName="DELE_D_DOCUMENTO_TIPO_DOC_TIPO_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DELE_D_DOCUMENTO_TIPO_DOCTIPOID_GENERATOR")
	@Column(name="doc_tipo_id")
	private Integer docTipoId;

	@Column(name="data_cancellazione")
	private Date dataCancellazione;

	@Column(name="data_creazione")
	private Date dataCreazione;

	@Column(name="data_modifica")
	private Date dataModifica;

	@Column(name="doc_tipo_cod")
	private String docTipoCod;

	@Column(name="doc_tipo_desc")
	private String docTipoDesc;

	@Column(name="login_operazione")
	private String loginOperazione;

	//bi-directional many-to-one association to DeleDRuoloOp
	@ManyToOne
	@JoinColumn(name="ruoloop_id")
	private DeleDRuoloOp deleDRuoloOp;

	//bi-directional many-to-one association to DeleTDocumento
	@OneToMany(mappedBy="deleDDocumentoTipo")
	private List<DeleTDocumento> deleTDocumentos;

	public DeleDDocumentoTipo() {
	}

	public Integer getDocTipoId() {
		return this.docTipoId;
	}

	public void setDocTipoId(Integer docTipoId) {
		this.docTipoId = docTipoId;
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

	public String getDocTipoCod() {
		return this.docTipoCod;
	}

	public void setDocTipoCod(String docTipoCod) {
		this.docTipoCod = docTipoCod;
	}

	public String getDocTipoDesc() {
		return this.docTipoDesc;
	}

	public void setDocTipoDesc(String docTipoDesc) {
		this.docTipoDesc = docTipoDesc;
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

	public List<DeleTDocumento> getDeleTDocumentos() {
		return this.deleTDocumentos;
	}

	public void setDeleTDocumentos(List<DeleTDocumento> deleTDocumentos) {
		this.deleTDocumentos = deleTDocumentos;
	}

	public DeleTDocumento addDeleTDocumento(DeleTDocumento deleTDocumento) {
		getDeleTDocumentos().add(deleTDocumento);
		deleTDocumento.setDeleDDocumentoTipo(this);

		return deleTDocumento;
	}

	public DeleTDocumento removeDeleTDocumento(DeleTDocumento deleTDocumento) {
		getDeleTDocumentos().remove(deleTDocumento);
		deleTDocumento.setDeleDDocumentoTipo(null);

		return deleTDocumento;
	}

}