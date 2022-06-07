/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dmacc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import it.csi.dma.FunzionalitaAbilitate;
import it.csi.dma.ParametriLogin;
import it.csi.dma.Richiedente;


/**
 * <p>Classe Java per getTokenInformation2Response complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="getTokenInformation2Response">
 *   &lt;complexContent>
 *     &lt;extension base="{http://dmacc.csi.it/}serviceResponse">
 *       &lt;sequence>
 *         &lt;element ref="{http://dma.csi.it/}richiedente" minOccurs="0"/>
 *         &lt;element ref="{http://dma.csi.it/}parametriLogin" minOccurs="0"/>
 *         &lt;element ref="{http://dma.csi.it/}funzionalitaAbilitate" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getTokenInformation2Response", propOrder = {
    "richiedente",
    "parametriLogin",
    "funzionalitaAbilitate"
})
public class GetTokenInformation2Response
    extends ServiceResponse
{

    @XmlElement(namespace = "http://dma.csi.it/")
    protected Richiedente richiedente;
    @XmlElement(namespace = "http://dma.csi.it/")
    protected ParametriLogin parametriLogin;
    @XmlElement(namespace = "http://dma.csi.it/")
    protected FunzionalitaAbilitate funzionalitaAbilitate;

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

    /**
     * Recupera il valore della proprietà funzionalitaAbilitate.
     * 
     * @return
     *     possible object is
     *     {@link FunzionalitaAbilitate }
     *     
     */
    public FunzionalitaAbilitate getFunzionalitaAbilitate() {
        return funzionalitaAbilitate;
    }

    /**
     * Imposta il valore della proprietà funzionalitaAbilitate.
     * 
     * @param value
     *     allowed object is
     *     {@link FunzionalitaAbilitate }
     *     
     */
    public void setFunzionalitaAbilitate(FunzionalitaAbilitate value) {
        this.funzionalitaAbilitate = value;
    }

}
