/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.ws.msg;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import it.csi.deleghe.deleghebe.ws.model.Richiedente;

@XmlType(name = "serviceRequest", namespace = "http://deleghebe.csi.it/", propOrder = {
	    "richiedente"
	})
public class ServiceRequest {
	
    @XmlElement(namespace = "", required = true)
    protected Richiedente richiedente;
    
    
    
    /**
     * Recupera il valore della propriet� richiedente.
     * 
     * @return
     *     possible object is
     *     {@link Richiedente }
     *     
     */
    @XmlTransient
    public Richiedente getRichiedente() {
        return richiedente;
    }

    /**
     * Imposta il valore della propriet� richiedente.
     * 
     * @param value
     *     allowed object is
     *     {@link Richiedente }
     *     
     */
    public void setRichiedente(Richiedente value) {
        this.richiedente = value;
    }
    
    

}
