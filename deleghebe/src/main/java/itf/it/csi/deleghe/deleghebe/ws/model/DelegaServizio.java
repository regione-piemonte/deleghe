/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.deleghe.deleghebe.ws.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per servizioDelega complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="servizioDelega">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="UUID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codiceServizio" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataInizioDelega" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="dataFineDelega" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="statoDelega" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tipoDelega" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="gradoDelega" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "delegaServizio", propOrder = {
    "uuid",
    "codiceServizio",
    "dataInizioDelega",
    "dataFineDelega",
    "statoDelega",
    "dataRevoca",
    "dataRinuncia",
    "tipoDelega",
    "gradoDelega"
})
public class DelegaServizio {


	
	
    @XmlElement(name = "UUID", required = false)
    protected String uuid;
    @XmlElement(required = true)
    protected String codiceServizio;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected Date dataInizioDelega;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected Date dataFineDelega;
    @XmlElement(required = true)
    protected String statoDelega;
    @XmlSchemaType(name = "dateTime")
    protected Date dataRevoca;
    @XmlSchemaType(name = "dateTime")
    protected Date dataRinuncia;

    @XmlElement
    protected String tipoDelega;
    @XmlElement
    protected String gradoDelega;

	/**
     * Recupera il valore della propriet� uuid.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUUID() {
        return uuid;
    }

    /**
     * Imposta il valore della propriet� uuid.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUUID(String value) {
        this.uuid = value;
    }

    /**
     * Recupera il valore della propriet� codiceServizio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceServizio() {
        return codiceServizio;
    }

    /**
     * Imposta il valore della propriet� codiceServizio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceServizio(String value) {
        this.codiceServizio = value;
    }

    /**
     * Recupera il valore della propriet� dataInizioDelega.
     * 
     * @return
     *     possible object is
     *     {@link Date }
     *     
     */
    public Date getDataInizioDelega() {
        return dataInizioDelega;
    }

    /**
     * Imposta il valore della propriet� dataInizioDelega.
     * 
     * @param value
     *     allowed object is
     *     {@link Date }
     *     
     */
    public void setDataInizioDelega(Date value) {
        this.dataInizioDelega = value;
    }

    /**
     * Recupera il valore della propriet� dataFineDelega.
     * 
     * @return
     *     possible object is
     *     {@link Date }
     *     
     */
    public Date getDataFineDelega() {
        return dataFineDelega;
    }

    /**
     * Imposta il valore della propriet� dataFineDelega.
     * 
     * @param value
     *     allowed object is
     *     {@link Date }
     *     
     */
    public void setDataFineDelega(Date value) {
        this.dataFineDelega = value;
    }

    /**
     * Recupera il valore della propriet� statoDelega.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatoDelega() {
        return statoDelega;
    }

    /**
     * Imposta il valore della propriet� statoDelega.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatoDelega(String value) {
        this.statoDelega = value;
    }
    
    public Date getDataRevoca() {
		return dataRevoca;
	}

	public void setDataRevoca(Date dataRevoca) {
		this.dataRevoca = dataRevoca;
	}

	public Date getDataRinuncia() {
		return dataRinuncia;
	}

	public void setDataRinuncia(Date dataRinuncia) {
		this.dataRinuncia = dataRinuncia;
	}

    public String getTipoDelega() {
        return tipoDelega;
    }

    public void setTipoDelega(String tipoDelega) {
        this.tipoDelega = tipoDelega;
    }

    public String getGradoDelega() {
        return gradoDelega;
    }

    public void setGradoDelega(String gradoDelega) {
        this.gradoDelega = gradoDelega;
    }
}
