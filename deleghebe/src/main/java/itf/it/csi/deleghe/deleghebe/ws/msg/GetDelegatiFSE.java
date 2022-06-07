/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.ws.msg;

import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import it.csi.deleghe.deleghebe.ws.model.Cittadino;
import it.csi.deleghe.deleghebe.ws.model.Richiedente;

/**
 * <p>Classe Java per getDelegatiFSE complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="getDelegatiFSE">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="richiedente" type="{http://deleghebe.csi.it/}richiedente"/>
 *         &lt;element name="cittadino" type="{http://deleghebe.csi.it/}cittadino"/>
 *         &lt;element name="delegato" type="{http://deleghebe.csi.it/}cittadino" minOccurs="0"/>
 *         &lt;element name="codiceServizio" type="{http://deleghebe.csi.it/}codifica" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getDelegatiFSE", namespace = "http://deleghebe.csi.it/" , propOrder = {
    "cittadinoDelegante",
    "cittadinoDelegato",
    "codiciServizio",
    "statiDelega"
})
public class GetDelegatiFSE extends ServiceRequest{
	
	@XmlElement(namespace = "", required = true)
    protected Cittadino cittadinoDelegante; 
    
    @XmlElement(required = false)
	protected Cittadino cittadinoDelegato; //per filtro di ricerca
    
    @XmlElementWrapper
    @XmlElement(name="codiceServizio", required = false)
    protected List<String> codiciServizio; //per filtro di ricerca
    
    @XmlElementWrapper
    @XmlElement(name="statoDelega", required=false)
    protected List<String> statiDelega; //per filtro di ricerca

    /**
     * Recupera il valore della propriet� richiedente.
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
     * Recupera il valore della propriet� delegato.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Cittadino }{@code >}
     *     
     */
    public Cittadino getCittadinoDelegato() {
        return cittadinoDelegato;
    }

    /**
     * Imposta il valore della propriet� delegato.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Cittadino }{@code >}
     *     
     */
    public void setCittadinoDelegato(Cittadino delegato) {
        this.cittadinoDelegato = delegato;
    }

	public List<String> getCodiciServizio() {
		return codiciServizio;
	}

	public void setCodiciServizio(List<String> codiciServizio) {
		this.codiciServizio = codiciServizio;
	}

	public List<String> getStatiDelega() {
		return statiDelega;
	}

	public void setStatiDelega(List<String> statiDelega) {
		this.statiDelega = statiDelega;
	}


}
