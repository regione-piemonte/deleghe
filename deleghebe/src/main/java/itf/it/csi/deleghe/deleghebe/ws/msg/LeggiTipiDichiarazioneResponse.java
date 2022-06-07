/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.ws.msg;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import it.csi.deleghe.deleghebe.ws.model.TipoDichiarazione;

@XmlType
public class LeggiTipiDichiarazioneResponse extends ServiceResponse {
	
	@XmlElementWrapper
	@XmlElement(name="tipoDichiarazione")
	private List<TipoDichiarazione> elencoTipoDichiarazione;

	public List<TipoDichiarazione> getElencoTipoDichiarazione() {
		return elencoTipoDichiarazione;
	}

	public void setElencoTipoDichiarazione(List<TipoDichiarazione> elencoTipoDichiarazione) {
		this.elencoTipoDichiarazione = elencoTipoDichiarazione;
	}

}
