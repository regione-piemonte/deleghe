/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.ws.msg;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import it.csi.deleghe.deleghebe.ws.model.TipoDelega;

@XmlType
public class LeggiTipiDelegaResponse extends ServiceResponse {
	
	@XmlElementWrapper
	@XmlElement(name="tipoDelega")
	private List<TipoDelega> elencoTipoDelega;

	public List<TipoDelega> getElencoTipoDelega() {
		return elencoTipoDelega;
	}

	public void setElencoTipoDelega(List<TipoDelega> elencoTipoDelega) {
		this.elencoTipoDelega = elencoTipoDelega;
	}

	
}
