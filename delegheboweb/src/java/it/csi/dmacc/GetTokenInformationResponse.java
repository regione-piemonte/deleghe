/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dmacc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import it.csi.dma.ParametriLogin;
import it.csi.dmac.Richiedente;


/**
 * <p>Classe Java per getTokenInformationResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="getTokenInformationResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://dmacc.csi.it/}serviceResponse">
 *       &lt;sequence>
 *         &lt;element ref="{http://dmac.csi.it/}richiedente" minOccurs="0"/>
 *         &lt;element ref="{http://dma.csi.it/}codiceFiscaleAssistito" minOccurs="0"/>
 *         &lt;element ref="{http://dma.csi.it/}parametriLogin" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getTokenInformationResponse", propOrder = {
    "richiedente",
    "codiceFiscaleAssistito",
    "parametriLogin"
})
public class GetTokenInformationResponse
    extends ServiceResponse
{

    @XmlElement(namespace = "http://dmac.csi.it/")
    protected Richiedente richiedente;
    @XmlElement(namespace = "http://dma.csi.it/")
    protected String codiceFiscaleAssistito;
    @XmlElement(namespace = "http://dma.csi.it/")
    protected ParametriLogin parametriLogin;

    /**
     * Recupera il valore della proprietà richiedente.
     * 
     * @return
     *     possible object is
     *     {@link Richiedente }
     *     
     */
    public Richiedente getRichiedente() {
        return richiedente;
    }

    /**
     * Imposta il valore della proprietà richiedente.
     * 
     * @param value
     *     allowed object is
     *     {@link Richiedente }
     *     
     */
    public void setRichiedente(Richiedente value) {
        this.richiedente = value;
    }

    /**
     * Recupera il valore della proprietà codiceFiscaleAssistito.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceFiscaleAssistito() {
        return codiceFiscaleAssistito;
    }

    /**
     * Imposta il valore della proprietà codiceFiscaleAssistito.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceFiscaleAssistito(String value) {
        this.codiceFiscaleAssistito = value;
    }

    /**
     * Recupera il valore della proprietà parametriLogin.
     * 
     * @return
     *     possible object is
     *     {@link ParametriLogin }
     *     
     */
    public ParametriLogin getParametriLogin() {
        return parametriLogin;
    }

    /**
     * Imposta il valore della proprietà parametriLogin.
     * 
     * @param value
     *     allowed object is
     *     {@link ParametriLogin }
     *     
     */
    public void setParametriLogin(ParametriLogin value) {
        this.parametriLogin = value;
    }

}
