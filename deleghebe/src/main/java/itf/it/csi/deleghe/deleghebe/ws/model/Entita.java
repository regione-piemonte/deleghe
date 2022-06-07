/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.ws.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;

public abstract class Entita {
	
	protected String loginOperazione;

	@XmlSchemaType(name = "dateTime")
	protected Date dataCreazione;
	
	@XmlSchemaType(name = "dateTime")
	protected Date dataModifica;
	
	@XmlSchemaType(name = "dateTime")
	protected Date dataCancellazione;
	
	protected RuoloOperazione ruoloOperazione;

	public String getLoginOperazione() {
		return loginOperazione;
	}

	public void setLoginOperazione(String loginOperazione) {
		this.loginOperazione = loginOperazione;
	}

	@XmlTransient
	public Date getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	@XmlTransient
	public Date getDataModifica() {
		return dataModifica;
	}

	public void setDataModifica(Date dataModifica) {
		this.dataModifica = dataModifica;
	}

	public Date getDataCancellazione() {
		return dataCancellazione;
	}

	@XmlTransient
	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public RuoloOperazione getRuoloOperazione() {
		return ruoloOperazione;
	}

	public void setRuoloOperazione(RuoloOperazione ruoloOperazione) {
		this.ruoloOperazione = ruoloOperazione;
	}
	

	
}
