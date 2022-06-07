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

import it.csi.deleghe.deleghebe.ws.model.DelegaCittadino;
import it.csi.deleghe.deleghebe.ws.model.Deleganti;


/**
 * <p>Classe Java per getDelegantiResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="getDelegantiResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://deleghebe.csi.it/}serviceResponse">
 *       &lt;sequence>
 *         &lt;element name="deleganti" type="{http://deleghebe.csi.it/}deleganti" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getDelegantiResponse", namespace = "http://deleghebe.csi.it/", propOrder = {
    "deleganti"
})
public class GetDelegantiResponse
    extends ServiceResponse
{

	@XmlElementWrapper
	@XmlElement(name="delegante")
    protected List<DelegaCittadino> deleganti;

    /**
     * Gets the value of the deleganti property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the deleganti property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDeleganti().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Deleganti }
     * 
     * 
     */
    public List<DelegaCittadino> getDeleganti() {
        if (deleganti == null) {
            deleganti = new ArrayList<DelegaCittadino>();
        }
        return this.deleganti;
    }

	public void setDeleganti(List<DelegaCittadino> deleganti) {
		this.deleganti = deleganti;
	}
    
    

}
