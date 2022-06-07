/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.deleghe.deleghebe.ws.msg;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import it.csi.deleghe.deleghebe.ws.model.Cittadino;

@XmlType
public class RicercaCittadiniResponse extends ServiceResponse {
	
	@XmlElementWrapper
	@XmlElement(name="cittadino")
	private List<Cittadino> cittadini;

	public List<Cittadino> getCittadini() {
		return cittadini;
	}

	public void setCittadini(List<Cittadino> cittadini) {
		this.cittadini = cittadini;
	}

	

}
