/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.deleghe.deleghebe.ws.msg;

import javax.xml.bind.annotation.XmlType;

import it.csi.deleghe.deleghebe.ws.model.Delega;



@XmlType
public class AggiornaDelegaResponse extends ServiceResponse {
	private Delega delega;

	public Delega getDelega() {
		return delega;
	}

	public void setDelega(Delega delega) {
		this.delega = delega;
	}
}
