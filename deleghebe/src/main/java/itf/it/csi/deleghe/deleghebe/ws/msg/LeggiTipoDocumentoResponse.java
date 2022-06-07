/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.ws.msg;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import it.csi.deleghe.deleghebe.ws.model.Servizio;
import it.csi.deleghe.deleghebe.ws.model.TipoFileDocumento;

@XmlType
public class LeggiTipoDocumentoResponse extends ServiceResponse {
	
	@XmlElementWrapper
	@XmlElement(name="tipoFileDocumento")
	private List<TipoFileDocumento> tipoFileDocumento;

	public List<TipoFileDocumento> getTipoFileDocumento() {
		return tipoFileDocumento;
	}

	public void setTipoFileDocumento(List<TipoFileDocumento> tipoFileDocumento) {
		this.tipoFileDocumento = tipoFileDocumento;
	}


}
