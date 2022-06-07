/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.ws.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ruoloDeleghe complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ruoloDeleghe">
 *   &lt;complexContent>
 *     &lt;extension base="{http://deleghebe.csi.it/}codifica">
 *       &lt;sequence>
 *         &lt;element name="aziendaSanitaria" type="{http://deleghebe.csi.it/}aziendaSanitaria" minOccurs="0"/>
 *         &lt;element name="profilo" type="{http://deleghebe.csi.it/}profilo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ruoloDeleghe", propOrder = {
    "aziendaSanitaria",
    "profilo"
})
public class RuoloDeleghe
    extends Codifica
{

    protected AziendaSanitaria aziendaSanitaria;
    protected Profilo profilo;

    /**
     * Recupera il valore della propriet� aziendaSanitaria.
     * 
     * @return
     *     possible object is
     *     {@link AziendaSanitaria }
     *     
     */
    public AziendaSanitaria getAziendaSanitaria() {
        return aziendaSanitaria;
    }

    /**
     * Imposta il valore della propriet� aziendaSanitaria.
     * 
     * @param value
     *     allowed object is
     *     {@link AziendaSanitaria }
     *     
     */
    public void setAziendaSanitaria(AziendaSanitaria value) {
        this.aziendaSanitaria = value;
    }

    /**
     * Recupera il valore della propriet� profilo.
     * 
     * @return
     *     possible object is
     *     {@link Profilo }
     *     
     */
    public Profilo getProfilo() {
        return profilo;
    }

    /**
     * Imposta il valore della propriet� profilo.
     * 
     * @param value
     *     allowed object is
     *     {@link Profilo }
     *     
     */
    public void setProfilo(Profilo value) {
        this.profilo = value;
    }

}
