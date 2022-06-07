/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.ws.msg;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import it.csi.deleghe.deleghebe.ws.model.Delega;

@XmlType
public class RicercaDeleghe extends ServiceRequest {
	private Delega delega;
	
	@XmlElementWrapper
    @XmlElement(name="codiceServizio", required = false)
    protected List<String> codiciServizio; //per filtro di ricerca
    
    @XmlElementWrapper
    @XmlElement(name="statoDelega", required=false)
    protected List<String> statiDelega; //per filtro di ricerca
    


	public Delega getDelega() {
		return delega;
	}

	public void setDelega(Delega delega) {
		this.delega = delega;
	}

	@XmlTransient
	public List<String> getCodiciServizio() {
		return codiciServizio;
	}

	public void setCodiciServizio(List<String> codiciServizio) {
		this.codiciServizio = codiciServizio;
	}

	@XmlTransient
	public List<String> getStatiDelega() {
		return statiDelega;
	}

	public void setStatiDelega(List<String> statiDelega) {
		this.statiDelega = statiDelega;
	}
	
	
}
