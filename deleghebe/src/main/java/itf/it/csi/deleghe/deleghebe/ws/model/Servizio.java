/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.ws.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "servizio"
,propOrder = {
	"serId",
	"descrizioneEstesa",
    "arruolabile",
    "delegabile",
    "notificabile",
    "obbligatorioArruolamento",
    "minore",
    "url",
    "validitaServizio",
    "numeroGiorniDelegabili",
    "dataInizioValidita",
	"dataFineValidita",
	"codSerPadre",
	"fraseDebole",
	"fraseForte"
})
public class Servizio extends Codifica {
	
	private Integer serId;
	private String  descrizioneEstesa;
	private Boolean arruolabile;
	private Boolean delegabile;
	private Boolean notificabile;
	private Boolean obbligatorioArruolamento;
	private Boolean minore;
	private String  url;
	private String  validitaServizio;
	private Integer numeroGiorniDelegabili;
	private Date dataInizioValidita;
	private Date dataFineValidita;
	//NUOVO CAMPO
	private String  codSerPadre;
	//NUOVO CAMPO
	private String  fraseDebole;
	//NUOVO CAMPO
	private String  fraseForte;
	
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getDescrizioneEstesa() {
		return descrizioneEstesa;
	}
	public void setDescrizioneEstesa(String descrizioneEstesa) {
		this.descrizioneEstesa = descrizioneEstesa;
	}
	public Boolean getNotificabile() {
		return notificabile;
	}
	public void setNotificabile(Boolean notificabile) {
		this.notificabile = notificabile;
	}
	public Boolean getDelegabile() {
		return delegabile;
	}
	public void setDelegabile(Boolean delegabile) {
		this.delegabile = delegabile;
	}
	public Boolean getArruolabile() {
		return arruolabile;
	}
	public void setArruolabile(Boolean arruolabile) {
		this.arruolabile = arruolabile;
	}
	public Boolean getObbligatorioArruolamento() {
		return obbligatorioArruolamento;
	}
	public void setObbligatorioArruolamento(Boolean obbligatorioArruolamento) {
		this.obbligatorioArruolamento = obbligatorioArruolamento;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getNumeroGiorniDelegabili() {
		return numeroGiorniDelegabili;
	}
	public void setNumeroGiorniDelegabili(Integer numeroGiorniValidita) {
		this.numeroGiorniDelegabili = numeroGiorniValidita;
	}
	public Date getDataInizioValidita() {
		return dataInizioValidita;
	}
	public void setDataInizioValidita(Date dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}
	public Date getDataFineValidita() {
		return dataFineValidita;
	}
	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}
	public Boolean getMinore() {
		return minore;
	}
	public void setMinore(Boolean minore) {
		this.minore = minore;
	}
	public Integer getSerId() {
		return serId;
	}
	public void setSerId(Integer serId) {
		this.serId = serId;
	}
	public String getValiditaServizio() {
		return validitaServizio;
	}
	public void setValiditaServizio(String validitaServizio) {
		this.validitaServizio = validitaServizio;
	}
	public String getCodSerPadre() {
		return codSerPadre;
	}
	public void setCodSerPadre(String codSerPadre) {
		this.codSerPadre = codSerPadre;
	}
	public String getFraseDebole() {
		return fraseDebole;
	}
	public void setFraseDebole(String fraseDebole) {
		this.fraseDebole = fraseDebole;
	}
	public String getFraseForte() {
		return fraseForte;
	}
	public void setFraseForte(String fraseForte) {
		this.fraseForte = fraseForte;
	}
	

}
