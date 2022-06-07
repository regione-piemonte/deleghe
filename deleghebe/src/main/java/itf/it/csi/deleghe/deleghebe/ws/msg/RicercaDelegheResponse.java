/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.ws.msg;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import it.csi.deleghe.deleghebe.ws.model.Delega;



@XmlType
public class RicercaDelegheResponse extends ServiceResponse {

	@XmlElementWrapper
	@XmlElement(name="delega")
	private List<Delega> deleghe;

	@XmlTransient
	public List<Delega> getDeleghe() {
		return deleghe;
	}

	public void setDeleghe(List<Delega> deleghe) {
		this.deleghe = deleghe;
	}
	
	

}
