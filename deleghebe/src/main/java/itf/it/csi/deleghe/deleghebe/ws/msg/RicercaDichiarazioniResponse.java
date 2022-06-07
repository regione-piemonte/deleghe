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

import it.csi.deleghe.deleghebe.ws.model.Dichiarazione;



@XmlType
public class RicercaDichiarazioniResponse extends ServiceResponse {

	@XmlElementWrapper
	@XmlElement(name="dichiarazione")
	private List<Dichiarazione> dichiarazioni;

	@XmlTransient
	public List<Dichiarazione> getDichiarazioni() {
		return dichiarazioni;
	}

	public void setDichiarazioni(List<Dichiarazione> dichiarazioni) {
		this.dichiarazioni = dichiarazioni;
	}

	

}
