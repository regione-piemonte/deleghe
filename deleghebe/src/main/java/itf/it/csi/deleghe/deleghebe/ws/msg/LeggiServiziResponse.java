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

@XmlType
public class LeggiServiziResponse extends ServiceResponse {
	
	@XmlElementWrapper
	@XmlElement(name="servizio")
	private List<Servizio> elencoServizio;
	
	public List<Servizio> getElencoServizio() {
		return elencoServizio;
	}

	public void setElencoServizio(List<Servizio> elencoServizio) {
		this.elencoServizio = elencoServizio;
	}
	
	

	


}
