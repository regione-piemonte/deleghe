/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.ws.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class Documento extends Entita {
	private Integer id;
	private Integer fileId;
	private String desc;
	private DocumentoTipo tipo;
	

	private Date dataRilascio;
	private String docDesc;
    private String numeroDocumento;
    private String autorita;


	protected Date dataScadenzaDoc;
	
	private DocumentoFile file;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public DocumentoTipo getDocumentoTipo() {
		return tipo;
	}

	public void setDocumentoTipo(DocumentoTipo tipo) {
		this.tipo = tipo;
	}

	public DocumentoFile getFile() {
		return file;
	}

	public void setFile(DocumentoFile file) {
		this.file = file;
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

	public String getDocDesc() {
		return docDesc;
	}

	public void setDocDesc(String docDesc) {
		this.docDesc = docDesc;
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

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	
	
}
