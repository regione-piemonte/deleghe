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
import it.csi.deleghe.deleghebe.ws.model.StatoDelega;

@XmlType
public class LeggiStatiDelegaResponse extends ServiceResponse {
	
	@XmlElementWrapper
	@XmlElement(name="statoDelega")
	private List<StatoDelega> elencoStatoDelega;

	public List<StatoDelega> getElencoStatoDelega() {
		return elencoStatoDelega;
	}

	public void setElencoStatoDelega(List<StatoDelega> elencoStatoDelega) {
		this.elencoStatoDelega = elencoStatoDelega;
	}

	
}
