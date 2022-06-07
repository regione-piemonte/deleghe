/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dmacc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per getTokenInformationRequest complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="getTokenInformationRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://dma.csi.it/}token" minOccurs="0"/>
 *         &lt;element ref="{http://dma.csi.it/}applicazione" minOccurs="0"/>
 *         &lt;element ref="{http://dma.csi.it/}ipBrowser" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getTokenInformationRequest", propOrder = {
    "token",
    "applicazione",
    "ipBrowser"
})
public class GetTokenInformationRequest {

    @XmlElement(namespace = "http://dma.csi.it/")
    protected String token;
    @XmlElement(namespace = "http://dma.csi.it/")
    protected String applicazione;
    @XmlElement(namespace = "http://dma.csi.it/")
    protected String ipBrowser;

    /**
     * Recupera il valore della proprietà token.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToken() {
        return token;
    }

    /**
     * Imposta il valore della proprietà token.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToken(String value) {
        this.token = value;
    }

    /**
     * Recupera il valore della proprietà applicazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplicazione() {
        return applicazione;
    }

    /**
     * Imposta il valore della proprietà applicazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplicazione(String value) {
        this.applicazione = value;
    }

    /**
     * Recupera il valore della proprietà ipBrowser.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIpBrowser() {
        return ipBrowser;
    }

    /**
     * Imposta il valore della proprietà ipBrowser.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIpBrowser(String value) {
        this.ipBrowser = value;
    }

}
