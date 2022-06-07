/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.ws.msg;

import javax.xml.bind.annotation.XmlType;

import it.csi.deleghe.deleghebe.ws.model.Servizio;

@XmlType
public class InserisciServizio extends ServiceRequest {
	
	private Servizio servizio;

	public Servizio getServizio() {
		return servizio;
	}

	public void setServizio(Servizio servizio) {
		this.servizio = servizio;
	}
	
	
	
	

}
