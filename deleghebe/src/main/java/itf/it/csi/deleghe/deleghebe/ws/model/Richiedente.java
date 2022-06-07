/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.deleghe.deleghebe.ws.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="servizio" type="{http://deleghebe.csi.it/}applicazioneRichiedente"/>
 *         &lt;element name="codiceFiscale" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "servizio",
    "codiceFiscale"
})
public class Richiedente {

    @XmlElement(required = true)
    protected ApplicazioneRichiedente servizio;
    @XmlElement(required = true)
    protected String codiceFiscale;
    
    
    public Richiedente() {
		// TODO Auto-generated constructor stub
	}
    
    
    public Richiedente(String idRequest, String codiceServizio, String codiceFiscale) {
		ApplicazioneRichiedente applicazioneRichiedente = new ApplicazioneRichiedente();
		applicazioneRichiedente.setIdRequest(idRequest);
		applicazioneRichiedente.setCodice(codiceServizio);
		this.setServizio(applicazioneRichiedente);
		this.setCodiceFiscale(codiceFiscale);
	}

    /**
     * Recupera il valore della propriet� servizio.
     * 
     * @return
     *     possible object is
     *     {@link ApplicazioneRichiedente }
     *     
     */
    public ApplicazioneRichiedente getServizio() {
        return servizio;
    }

    /**
     * Imposta il valore della propriet� servizio.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicazioneRichiedente }
     *     
     */
    public void setServizio(ApplicazioneRichiedente value) {
        this.servizio = value;
    }

    /**
     * Recupera il valore della propriet� codiceFiscale.
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
     * Imposta il valore della propriet� codiceFiscale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceFiscale(String value) {
        this.codiceFiscale = value;
    }

}
