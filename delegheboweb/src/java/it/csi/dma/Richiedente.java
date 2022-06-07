/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per richiedente complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="richiedente">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codiceAzienda" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiceCollocazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiceFiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cognome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descrizioneAzienda" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descrizioneCollocazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ruolo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "richiedente", propOrder = {
    "codiceAzienda",
    "codiceCollocazione",
    "codiceFiscale",
    "cognome",
    "descrizioneAzienda",
    "descrizioneCollocazione",
    "nome",
    "ruolo"
})
public class Richiedente {

    protected String codiceAzienda;
    protected String codiceCollocazione;
    protected String codiceFiscale;
    protected String cognome;
    protected String descrizioneAzienda;
    protected String descrizioneCollocazione;
    protected String nome;
    protected String ruolo;

    /**
     * Recupera il valore della proprietà codiceAzienda.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceAzienda() {
        return codiceAzienda;
    }

    /**
     * Imposta il valore della proprietà codiceAzienda.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceAzienda(String value) {
        this.codiceAzienda = value;
    }

    /**
     * Recupera il valore della proprietà codiceCollocazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceCollocazione() {
        return codiceCollocazione;
    }

    /**
     * Imposta il valore della proprietà codiceCollocazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceCollocazione(String value) {
        this.codiceCollocazione = value;
    }

    /**
     * Recupera il valore della proprietà codiceFiscale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    /**
     * Imposta il valore della proprietà codiceFiscale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceFiscale(String value) {
        this.codiceFiscale = value;
    }

    /**
     * Recupera il valore della proprietà cognome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Imposta il valore della proprietà cognome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCognome(String value) {
        this.cognome = value;
    }

    /**
     * Recupera il valore della proprietà descrizioneAzienda.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneAzienda() {
        return descrizioneAzienda;
    }

    /**
     * Imposta il valore della proprietà descrizioneAzienda.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneAzienda(String value) {
        this.descrizioneAzienda = value;
    }

    /**
     * Recupera il valore della proprietà descrizioneCollocazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneCollocazione() {
        return descrizioneCollocazione;
    }

    /**
     * Imposta il valore della proprietà descrizioneCollocazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneCollocazione(String value) {
        this.descrizioneCollocazione = value;
    }

    /**
     * Recupera il valore della proprietà nome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il valore della proprietà nome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNome(String value) {
        this.nome = value;
    }

    /**
     * Recupera il valore della proprietà ruolo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRuolo() {
        return ruolo;
    }

    /**
     * Imposta il valore della proprietà ruolo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRuolo(String value) {
        this.ruolo = value;
    }

}
