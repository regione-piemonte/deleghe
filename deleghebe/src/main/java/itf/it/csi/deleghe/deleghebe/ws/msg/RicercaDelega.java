/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.ws.msg;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import it.csi.deleghe.deleghebe.ws.model.StatoDelega;
import it.csi.deleghe.deleghebe.ws.model.TipoDelega;

@XmlRootElement(name = "ricercaDelega")
@XmlAccessorType(XmlAccessType.FIELD)

public class RicercaDelega extends ServiceRequest {
	

	protected Date dataInserimentoDa;
	

	protected Date dataInserimentoA;
	

	protected Date dataValiditaDa;
	

	protected Date dataValiditaA;
	
	private String codiceFiscaleDelegato;
	
	private String codiceFiscaleDelegante;
	
	private Integer ciIdDelegato;
	
	private Integer citIdDelegante;
    
    @XmlElementWrapper
    @XmlElement(name="statoDelega", required=false)
    protected List<StatoDelega> statiDelega; //per filtro di ricerca
    
    @XmlElementWrapper
    @XmlElement(name="tipoDelega", required=false)
    protected List<TipoDelega> tipiDelega;

    public Date getDataInserimentoDa() {
		return dataInserimentoDa;
	}

	public void setDataInserimentoDa(Date dataInserimentoDa) {
		this.dataInserimentoDa = dataInserimentoDa;
	}

	public Date getDataInserimentoA() {
		return dataInserimentoA;
	}

	public void setDataInserimentoA(Date dataInserimentoA) {
		this.dataInserimentoA = dataInserimentoA;
	}



	public List<StatoDelega> getStatiDelega() {
		return statiDelega;
	}

	public void setStatiDelega(List<StatoDelega> statiDelega) {
		this.statiDelega = statiDelega;
	}


	public List<TipoDelega> getTipiDelega() {
		return tipiDelega;
	}

	public void setTipiDelega(List<TipoDelega> tipiDelega) {
		this.tipiDelega = tipiDelega;
	}

	public Date getDataValiditaDa() {
		return dataValiditaDa;
	}

	public void setDataValiditaDa(Date dataValiditaDa) {
		this.dataValiditaDa = dataValiditaDa;
	}

	public Date getDataValiditaA() {
		return dataValiditaA;
	}

	public void setDataValiditaA(Date dataValiditaA) {
		this.dataValiditaA = dataValiditaA;
	}

	public String getCodiceFiscaleDelegato() {
		return codiceFiscaleDelegato;
	}

	public void setCodiceFiscaleDelegato(String codiceFiscaleDelegato) {
		this.codiceFiscaleDelegato = codiceFiscaleDelegato;
	}

	public String getCodiceFiscaleDelegante() {
		return codiceFiscaleDelegante;
	}

	public void setCodiceFiscaleDelegante(String codiceFiscaleDelegante) {
		this.codiceFiscaleDelegante = codiceFiscaleDelegante;
	}

	public Integer getCiIdDelegato() {
		return ciIdDelegato;
	}

	public void setCiIdDelegato(Integer ciIdDelegato) {
		this.ciIdDelegato = ciIdDelegato;
	}

	public Integer getCitIdDelegante() {
		return citIdDelegante;
	}

	public void setCitIdDelegante(Integer citIdDelegante) {
		this.citIdDelegante = citIdDelegante;
	}
    
	
	

}
