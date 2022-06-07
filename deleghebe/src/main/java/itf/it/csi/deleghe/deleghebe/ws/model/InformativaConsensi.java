/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.ws.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlType;

@XmlType
public class InformativaConsensi extends Entita {
	
	  private String pdfInformativa;
	  private Date dataDecorrenza;
	  private Date dataScadenza;
	  private String descInformativa;
	  private String htmlInformativa;
	  
	public String getPdfInformativa() {
		return pdfInformativa;
	}
	public void setPdfInformativa(String pdfInformativa) {
		this.pdfInformativa = pdfInformativa;
	}
	public Date getDataDecorrenza() {
		return dataDecorrenza;
	}
	public void setDataDecorrenza(Date dataDecorrenza) {
		this.dataDecorrenza = dataDecorrenza;
	}
	public Date getDataScadenza() {
		return dataScadenza;
	}
	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}
	public String getDescInformativa() {
		return descInformativa;
	}
	public void setDescInformativa(String descInformativa) {
		this.descInformativa = descInformativa;
	}
	public String getHtmlInformativa() {
		return htmlInformativa;
	}
	public void setHtmlInformativa(String htmlInformativa) {
		this.htmlInformativa = htmlInformativa;
	}

}
