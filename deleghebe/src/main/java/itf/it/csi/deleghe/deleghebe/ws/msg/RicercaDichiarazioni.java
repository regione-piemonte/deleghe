/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.deleghe.deleghebe.ws.msg;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import it.csi.deleghe.deleghebe.ws.model.Dichiarazione;



@XmlType
public class RicercaDichiarazioni extends ServiceRequest {

    private Dichiarazione dichiarazione;


    @XmlElementWrapper
    @XmlElement(name="modoDichiarazione", required = false)
    protected List<String> modiDichiarazione; //per filtro di ricerca


	@XmlElementWrapper
    @XmlElement(name="tipoDichiarazione", required = false)
    protected List<String> tipiDichiarazione; //per filtro di ricerca

    @XmlElementWrapper
    @XmlElement(name="statoDichiarazione", required = false)
    protected List<String> statiDichiarazione; //per filtro di ricerca

	public Dichiarazione getDichiarazione() {
		return dichiarazione;
	}

	private Date dataInserimentoDa;

	private Date dataInserimentoA;

	private String ruoloCittadino;

	private String cittadinoCF;

	public void setDichiarazione(Dichiarazione dichiarazione) {
		this.dichiarazione = dichiarazione;
	}

	@XmlTransient
	public List<String> getModiDichiarazione() {
		return modiDichiarazione;
	}

	public void setModiDichiarazione(List<String> modiDichiarazione) {
		this.modiDichiarazione = modiDichiarazione;
	}

	@XmlTransient
	public List<String> getTipiDichiarazione() {
		return tipiDichiarazione;
	}

	public void setTipiDichiarazione(List<String> tipiDichiarazione) {
		this.tipiDichiarazione = tipiDichiarazione;
	}

	@XmlTransient
	public List<String> getStatiDichiarazione() {
		return statiDichiarazione;
	}

	public void setStatiDichiarazione(List<String> statiDichiarazione) {
		this.statiDichiarazione = statiDichiarazione;
	}


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

	public String getRuoloCittadino() {
		return ruoloCittadino;
	}

	public void setRuoloCittadino(String ruoloCittadino) {
		this.ruoloCittadino = ruoloCittadino;
	}

	public String getCittadinoCF() {
		return cittadinoCF;
	}

	public void setCittadinoCF(String cittadinoCF) {
		this.cittadinoCF = cittadinoCF;
	}
}
