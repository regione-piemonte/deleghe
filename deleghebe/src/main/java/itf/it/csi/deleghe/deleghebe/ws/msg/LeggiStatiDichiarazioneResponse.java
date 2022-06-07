/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.ws.msg;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;


import it.csi.deleghe.deleghebe.ws.model.StatoDichiarazione;

@XmlType
public class LeggiStatiDichiarazioneResponse extends ServiceResponse {
	
	@XmlElementWrapper
	@XmlElement(name="statoDichiarazione")
	private List<StatoDichiarazione> elencoStatoDichiarazione;

	public List<StatoDichiarazione> getElencoStatoDichiarazione() {
		return elencoStatoDichiarazione;
	}

	public void setElencoStatoDichiarazione(List<StatoDichiarazione> elencoStatoDichiarazione) {
		this.elencoStatoDichiarazione = elencoStatoDichiarazione;
	}


	

}
