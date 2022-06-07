/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.ws.msg;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import it.csi.deleghe.deleghebe.ws.model.DocumentoTipo;

@XmlType
public class LeggiDescrizioneDocumentoResponse extends ServiceResponse {
	
	@XmlElementWrapper
	@XmlElement(name="documentoTipo")
	private List<DocumentoTipo> elencoDocumentoTipo;

	public List<DocumentoTipo> getElencoDocumentoTipo() {
		return elencoDocumentoTipo;
	}

	public void setElencoDocumentoTipo(List<DocumentoTipo> elencoDocumentoTipo) {
		this.elencoDocumentoTipo = elencoDocumentoTipo;
	}


}
