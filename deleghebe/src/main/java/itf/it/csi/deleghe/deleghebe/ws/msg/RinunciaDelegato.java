/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.ws.msg;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import it.csi.deleghe.deleghebe.ws.model.Cittadino;


/**
 * <p>Classe Java per rinunciaDelegato complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="rinunciaDelegato">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="richiedente" type="{http://deleghebe.csi.it/}richiedente"/>
 *         &lt;element name="cittadino" type="{http://deleghebe.csi.it/}cittadino"/>
 *         &lt;element name="cittDelServ" type="{http://deleghebe.csi.it/}cittDelServ"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rinunciaDelegato", namespace = "http://deleghebe.csi.it/", propOrder = {
    "cittadinoDelegato",
    "cittadinoDelegante",
    "uuidDelegaServizii"
})
public class RinunciaDelegato extends ServiceRequest {

    @XmlElement(namespace = "", required = true)
    protected Cittadino cittadinoDelegato;
   
    
    @XmlElement(namespace = "", required = true)
    protected Cittadino cittadinoDelegante;

    @XmlElementWrapper
    @XmlElement(name="uuidDelegaServizio", required = true)
    protected List<String> uuidDelegaServizii;

    
    /**
     * Recupera il valore della propriet� cittadino.
     * 
     * @return
     *     possible object is
     *     {@link Cittadino }
     *     
     */
    public Cittadino getCittadinoDelegato() {
        return cittadinoDelegato;
    }

    /**
     * Imposta il valore della propriet� cittadino.
     * 
     * @param value
     *     allowed object is
     *     {@link Cittadino }
     *     
     */
    public void setCittadinoDelegato(Cittadino value) {
        this.cittadinoDelegato = value;
    }

	public Cittadino getCittadinoDelegante() {
		return cittadinoDelegante;
	}

	public void setCittadinoDelegante(Cittadino cittadinoDelegante) {
		this.cittadinoDelegante = cittadinoDelegante;
	}


    public List<String> getUuidDelegaServizii() {
		return uuidDelegaServizii;
	}

	public void setUuidDelegaServizii(List<String> uuidDelegaServizii) {
		this.uuidDelegaServizii = uuidDelegaServizii;
	}

	

    

}
