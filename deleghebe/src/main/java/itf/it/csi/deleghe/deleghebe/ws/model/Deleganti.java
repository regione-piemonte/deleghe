/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.ws.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per deleganti complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="deleganti">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="delegante" type="{http://deleghebe.csi.it/}cittadinoDeleghe" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deleganti", propOrder = {
    "delegante"
})
public class Deleganti {

    protected DelegaCittadino delegante;

    /**
     * Recupera il valore della propriet� delegante.
     * 
     * @return
     *     possible object is
     *     {@link DelegaCittadino }
     *     
     */
    public DelegaCittadino getDelegante() {
        return delegante;
    }

    /**
     * Imposta il valore della propriet� delegante.
     * 
     * @param value
     *     allowed object is
     *     {@link DelegaCittadino }
     *     
     */
    public void setDelegante(DelegaCittadino value) {
        this.delegante = value;
    }

}
