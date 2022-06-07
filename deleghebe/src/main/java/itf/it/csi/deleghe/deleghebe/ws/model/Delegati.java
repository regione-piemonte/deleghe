/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.deleghe.deleghebe.ws.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per delegati complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="delegati">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="delegato" type="{http://deleghebe.csi.it/}cittadinoDeleghe" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "delegati", propOrder = {
    "delegato"
})
public class Delegati {

    protected DelegaCittadino delegato;

    /**
     * Recupera il valore della propriet� delegato.
     * 
     * @return
     *     possible object is
     *     {@link DelegaCittadino }
     *     
     */
    public DelegaCittadino getDelegato() {
        return delegato;
    }

    /**
     * Imposta il valore della propriet� delegato.
     * 
     * @param value
     *     allowed object is
     *     {@link DelegaCittadino }
     *     
     */
    public void setDelegato(DelegaCittadino value) {
        this.delegato = value;
    }

}
