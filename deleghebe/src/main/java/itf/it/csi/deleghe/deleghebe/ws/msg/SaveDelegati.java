/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.deleghe.deleghebe.ws.msg;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import it.csi.deleghe.deleghebe.ws.model.Cittadino;
import it.csi.deleghe.deleghebe.ws.model.DelegaCittadino;
import it.csi.deleghe.deleghebe.ws.model.Delegati;


/**
 * <p>Classe Java per saveDelegati complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="saveDelegati">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="richiedente" type="{http://deleghebe.csi.it/}richiedente"/>
 *         &lt;element name="cittadino" type="{http://deleghebe.csi.it/}cittadino"/>
 *         &lt;element name="delegati" type="{http://deleghebe.csi.it/}delegati" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saveDelegati", namespace = "http://deleghebe.csi.it/", propOrder = {
    "cittadinoDelegante",
    "delegati"
})
public class SaveDelegati extends ServiceRequest {

    @XmlElement(namespace = "", required = true)
    protected Cittadino cittadinoDelegante;
    
    
    @XmlElementWrapper
    @XmlElement(name= "delegato", namespace = "", required = true)
    protected List<DelegaCittadino> delegati;
    
//    @XmlElementWrapper
//    @XmlElement(name= "delegato", namespace = "", required = true)
//    protected List<CittadinoDeleghe> delegati;


    /**
     * Recupera il valore della propriet� cittadino.
     * 
     * @return
     *     possible object is
     *     {@link Cittadino }
     *     
     */
    public Cittadino getCittadinoDelegante() {
        return cittadinoDelegante;
    }

    /**
     * Imposta il valore della propriet� cittadino.
     * 
     * @param value
     *     allowed object is
     *     {@link Cittadino }
     *     
     */
    public void setCittadinoDelegante(Cittadino value) {
        this.cittadinoDelegante = value;
    }
    
	 /**
	  * Gets the value of the delegati property.
	  * 
	  * <p>
	  * This accessor method returns a reference to the live list,
	  * not a snapshot. Therefore any modification you make to the
	  * returned list will be present inside the JAXB object.
	  * This is why there is not a <CODE>set</CODE> method for the delegati property.
	  * 
	  * <p>
	  * For example, to add a new item, do as follows:
	  * <pre>
	  *    getDelegati().add(newItem);
	  * </pre>
	  * 
	  * 
	  * <p>
	  * Objects of the following type(s) are allowed in the list
	  * {@link Delegati }
	  * 
	  * 
	  */
	 public List<DelegaCittadino> getDelegati() {
	     if (this.delegati == null) {
	    	 this.delegati = new ArrayList<DelegaCittadino>();
	     }
	     return this.delegati;
	 }

	public void setDelegati(List<DelegaCittadino> delegati) {
		this.delegati = delegati;
	}

  

}
