/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.ws.msg;

import javax.xml.bind.annotation.XmlType;

import it.csi.deleghe.deleghebe.ws.model.DocumentoFile;;

@XmlType
public class RicercaFileResponse extends ServiceResponse  {
	
	private DocumentoFile documentoFile;

	public DocumentoFile getDocumentoFile() {
		return documentoFile;
	}

	public void setDocumentoFile(DocumentoFile documentoFile) {
		this.documentoFile = documentoFile;
	}


}
