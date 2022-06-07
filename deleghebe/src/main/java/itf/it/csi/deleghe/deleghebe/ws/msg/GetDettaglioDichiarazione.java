/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.deleghe.deleghebe.ws.msg;

import javax.xml.bind.annotation.XmlType;

import it.csi.deleghe.deleghebe.ws.model.Dichiarazione;



@XmlType
public class GetDettaglioDichiarazione extends ServiceRequest {

    private Dichiarazione dichiarazione;

	public Dichiarazione getDichiarazione() {
		return dichiarazione;
	}

	public void setDichiarazione(Dichiarazione dichiarazione) {
		this.dichiarazione = dichiarazione;
	}

    
}
