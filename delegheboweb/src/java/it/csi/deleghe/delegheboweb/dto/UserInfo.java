/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.dto;

public class UserInfo {
	
	private String cognome;
	
	private String nome;
	
	private String codFisc;
	
	private String codASL;
	
	private String idAura;

	public String getIdAura() {
		return idAura;
	}

	public void setIdAura(String idAura) {
		this.idAura = idAura;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodFisc() {
		return codFisc;
	}

	public void setCodFisc(String codFisc) {
		this.codFisc = codFisc;
	}

	public String getCodASL() {
		return codASL;
	}

	public void setCodASL(String codASL) {
		this.codASL = codASL;
	}
}
