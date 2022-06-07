/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.deleghe.deleghebe.ws.model;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per cittadinoDeleghe complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="cittadinoDeleghe">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codiceFiscale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cognome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataDiNascita" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="sesso" type="{http://deleghebe.csi.it/}sesso" minOccurs="0"/>
 *         &lt;element name="idAura" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="deleghe" type="{http://deleghebe.csi.it/}serviziDeleghe" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "delegaCittadino"

,propOrder = {
	"uuid",
	"codiceFiscale",
    "cognome",
    "nome",
    "dataDiNascita",
    "sesso",
    "idAura",
    "luogoNascita",
    "stato",
    "delegatoInput",
    "deleghe",
	 "tipoDelega"
}

		)
public class DelegaCittadino {
	
	@XmlElement(name = "UUID", required = false)
	protected String uuid;
	@XmlElement(required = true)
	protected String codiceFiscale;
	protected String cognome;
	protected String nome;
	@XmlSchemaType(name = "dateTime")
	protected Date dataDiNascita;
	@XmlSchemaType(name = "string")
	protected Sesso sesso;
	protected Long idAura;
	protected String luogoNascita;
	@XmlElement(required = true)
	protected String stato;
	
	protected Delegato delegatoInput;
	

	@XmlElementWrapper
	@XmlElement(name="delega")
	protected List<DelegaServizio> deleghe;

	protected String tipoDelega;

	@XmlTransient
	public List<DelegaServizio> getDeleghe() {
		return deleghe;
	}

	public void setDeleghe(List<DelegaServizio> deleghe) {
		this.deleghe = deleghe;
	}
	
	@XmlTransient
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@XmlTransient
	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
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

	public Date getDataDiNascita() {
		return dataDiNascita;
	}

	public void setDataDiNascita(Date dataDiNascita) {
		this.dataDiNascita = dataDiNascita;
	}

	public Sesso getSesso() {
		return sesso;
	}

	public void setSesso(Sesso sesso) {
		this.sesso = sesso;
	}

	public Long getIdAura() {
		return idAura;
	}

	public void setIdAura(Long idAura) {
		this.idAura = idAura;
	}

	public String getLuogoNascita() {
		return luogoNascita;
	}

	public void setLuogoNascita(String luogoNascita) {
		this.luogoNascita = luogoNascita;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}
  
	public Delegato getDelegatoInput() {
		return delegatoInput;
	}

	public void setDelegatoInput(Delegato delegatoInput) {
		this.delegatoInput = delegatoInput;
	}

	public String getTipoDelega() {
		return tipoDelega;
	}

	public void setTipoDelega(String tipoDelega) {
		this.tipoDelega = tipoDelega;
	}
}
