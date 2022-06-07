/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.ws.msg;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "batchDelegheResponse", namespace = "http://deleghebe.csi.it/", propOrder = {
    "cntInScadenza", "cntScadute", "cntServizi"
})
public class BatchDelegheResponse
    extends ServiceResponse
{

    @XmlElement(namespace = "", required = true)
    protected Integer cntInScadenza;

    @XmlElement(namespace = "", required = true)
    protected Integer cntScadute;

    @XmlElement(namespace = "", required = true)
    protected Integer cntServizi;
    /**
     * Recupera il valore della proprieta cntScadute.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCntScadute() {
        return cntScadute;
    }

    /**
     * Imposta il valore della proprieta cntScadute.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCntScadute(Integer cntScadute) {
        this.cntScadute = cntScadute;
    }

    /**
     * Recupera il valore della proprieta cntInScadenza.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCntInScadenza() {
        return cntInScadenza;
    }

    /**
     * Imposta il valore della proprieta cntInScadenza.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCntInScadenza(Integer cntInScadenza) {
        this.cntInScadenza = cntInScadenza;
    }

    /**
     * Recupera il valore della proprieta cntServizi.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCntServizi() {
        return cntServizi;
    }

    /**
     * Imposta il valore della proprieta cntServizi.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCntServizi(Integer cntServizi) {
        this.cntServizi = cntServizi;
    }

}
