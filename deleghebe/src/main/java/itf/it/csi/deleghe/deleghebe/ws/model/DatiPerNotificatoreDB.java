/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.ws.model;

import java.util.Date;

public class DatiPerNotificatoreDB {	

	private String nomeDelegato;
	private String cognomeDelegato;
	private String nomeDelegante;
	private String cognomeDelegante;
	private Date dataDiScadenza;
	private String codiceServizio;
	private String cfDelegato;
	private String cfDelegante;
	private Integer dlgaId;
	
	public String getNomeDelegato() {
		return nomeDelegato;
	}
	public void setNomeDelegato(String nomeDelegato) {
		this.nomeDelegato = nomeDelegato;
	}
	public String getCognomeDelegato() {
		return cognomeDelegato;
	}
	public void setCognomeDelegato(String cognomeDelegato) {
		this.cognomeDelegato = cognomeDelegato;
	}
	public String getNomeDelegante() {
		return nomeDelegante;
	}
	public void setNomeDelegante(String nomeDelegante) {
		this.nomeDelegante = nomeDelegante;
	}
	public String getCognomeDelegante() {
		return cognomeDelegante;
	}
	public void setCognomeDelegante(String cognomeDelegante) {
		this.cognomeDelegante = cognomeDelegante;
	}
	public Date getDataDiScadenza() {
		return dataDiScadenza;
	}
	public void setDataDiScadenza(Date dataDiScadenza) {
		this.dataDiScadenza = dataDiScadenza;
	}
	public String getCodiceServizio() {
		return codiceServizio;
	}
	public void setCodiceServizio(String codiceServizio) {
		this.codiceServizio = codiceServizio;
	}
	public String getCfDelegato() {
		return cfDelegato;
	}
	public void setCfDelegato(String cfDelegato) {
		this.cfDelegato = cfDelegato;
	}
	public String getCfDelegante() {
		return cfDelegante;
	}
	public void setCfDelegante(String cfDelegante) {
		this.cfDelegante = cfDelegante;
	}
	public Integer getDlgaId() {
		return dlgaId;
	}
	public void setDlgaId(Integer dlgaId) {
		this.dlgaId = dlgaId;
	}

	

}
