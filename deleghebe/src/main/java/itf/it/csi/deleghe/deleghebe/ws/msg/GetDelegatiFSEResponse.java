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
import it.csi.deleghe.deleghebe.ws.model.Delegati;

/**
 * <p>Classe Java per getDelegatiFSEResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="getDelegatiFSEResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://deleghebe.csi.it/}serviceResponse">
 *       &lt;sequence>
 *         &lt;element name="delegatiFSE" type="{http://deleghebe.csi.it/}delegati" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getDelegatiFSEResponse", namespace = "http://deleghebe.csi.it/", propOrder = {
    "delegatiFSE"
})
public class GetDelegatiFSEResponse extends ServiceResponse{
	
	@XmlElementWrapper
	@XmlElement(name="delegatoFSE")
    protected List<String> delegatiFSE;

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
    public List<String> getDelegatiFSE() {
        if (delegatiFSE == null) {
        	delegatiFSE = new ArrayList<String>();
        }
        return this.delegatiFSE;
    }

	public void setDelegatiFSE(List<String> delegatiFSE) {
		this.delegatiFSE = delegatiFSE;
	}
    

}
