/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.ws.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per cittadino complex type.
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class Operatore extends Entita {
	
	
	private Integer opId;
	private Date dataCancellazione;
	private Date dataCreazione;
	private Date dataModifica;
	private String loginOperazione;
	private String opCf;
	private String opCognome;
	private String opNome;
	
	
	public Integer getOpId() {
		return opId;
	}
	public void setOpId(Integer opId) {
		this.opId = opId;
	}
	public Date getDataCancellazione() {
		return dataCancellazione;
	}
	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}
	public Date getDataCreazione() {
		return dataCreazione;
	}
	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}
	public Date getDataModifica() {
		return dataModifica;
	}
	public void setDataModifica(Date dataModifica) {
		this.dataModifica = dataModifica;
	}
	public String getLoginOperazione() {
		return loginOperazione;
	}
	public void setLoginOperazione(String loginOperazione) {
		this.loginOperazione = loginOperazione;
	}
	public String getOpCf() {
		return opCf;
	}
	public void setOpCf(String opCf) {
		this.opCf = opCf;
	}
	public String getOpCognome() {
		return opCognome;
	}
	public void setOpCognome(String opCognome) {
		this.opCognome = opCognome;
	}
	public String getOpNome() {
		return opNome;
	}
	public void setOpNome(String opNome) {
		this.opNome = opNome;
	}

    
    
   
   

}
