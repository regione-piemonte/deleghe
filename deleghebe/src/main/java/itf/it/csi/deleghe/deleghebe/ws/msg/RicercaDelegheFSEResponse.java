/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.ws.msg;

import java.util.List
;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


@XmlType
public class RicercaDelegheFSEResponse extends ServiceResponse{
	
	@XmlElementWrapper
	@XmlElement(name="delega")
	private List<String> deleghe;

	@XmlTransient
	public List<String> getDeleghe() {
		return deleghe;
	}

	public void setDeleghe(List<String> deleghe) {
		this.deleghe = deleghe;
	}

}
