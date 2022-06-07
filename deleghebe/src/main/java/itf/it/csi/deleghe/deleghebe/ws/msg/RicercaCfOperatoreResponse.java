/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.deleghe.deleghebe.ws.msg;

import javax.xml.bind.annotation.XmlType;

import it.csi.deleghe.deleghebe.ws.model.Operatore;

@XmlType
public class RicercaCfOperatoreResponse extends ServiceResponse {

	private Operatore operatore;
	private boolean exists;
	private String asl;

	public Operatore getOperatore() {
		return operatore;
	}

	public void setOperatore(Operatore operatore) {
		this.operatore = operatore;
	}

	public boolean isExists() {
		return exists;
	}

	public void setExists(boolean exists) {
		this.exists = exists;
	}

	public String getAsl() {
		return asl;
	}

	public void setAsl(String asl) {
		this.asl = asl;
	}

}
