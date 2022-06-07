/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.model;


import java.io.Serializable;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the cons_d_informativa database table.
 * 
 */
@Entity
@Table(name="dele_d_cons_informativa")
@NamedQuery(name="DeleDConsInformativa.findAll", query="SELECT c FROM DeleDConsInformativa c")
public class DeleDConsInformativa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DELE_D_CONS_INFORMATIVA_DINFORMATIVAID_GENERATOR", sequenceName="DELE_D_CONS_INFORMATIVA_D_INFORMATIVA_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DELE_D_CONS_INFORMATIVA_DINFORMATIVAID_GENERATOR")
	@Column(name="d_informativa_id")
	private Integer dInformativaId;

	@Column(name="data_cancellazione")
	private Date dataCancellazione;

	@Column(name="data_creazione")
	private Date dataCreazione;

	@Column(name="data_decorrenza")
	private Date dataDecorrenza;

	@Column(name="data_modifica")
	private Date dataModifica;

	@Column(name="data_scadenza")
	private Date dataScadenza;

	@Column(name="login_operazione")
	private String loginOperazione;

	@Column(name="pdf_informativa")
	private String pdfInformativa;

	@Column(name="ruoloop_id")
	private Integer ruoloopId;
	
	@Column(name="desc_informativa")
	private String descInformativa;
	
	@Column(name="html_informativa")
	private String htmlInformativa;

	public DeleDConsInformativa() {
	}

	public Integer getDInformativaId() {
		return this.dInformativaId;
	}

	public void setDInformativaId(Integer dInformativaId) {
		this.dInformativaId = dInformativaId;
	}

	public Date getDataCancellazione() {
		return this.dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public Date getDataCreazione() {
		return this.dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public Date getDataDecorrenza() {
		return this.dataDecorrenza;
	}

	public void setDataDecorrenza(Date dataDecorrenza) {
		this.dataDecorrenza = dataDecorrenza;
	}

	public Date getDataModifica() {
		return this.dataModifica;
	}

	public void setDataModifica(Date dataModifica) {
		this.dataModifica = dataModifica;
	}

	public Date getDataScadenza() {
		return this.dataScadenza;
	}

	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public String getLoginOperazione() {
		return this.loginOperazione;
	}

	public void setLoginOperazione(String loginOperazione) {
		this.loginOperazione = loginOperazione;
	}

	public String getPdfInformativa() {
		return this.pdfInformativa;
	}

	public void setPdfInformativa(String pdfInformativa) {
		this.pdfInformativa = pdfInformativa;
	}

	public Integer getRuoloopId() {
		return this.ruoloopId;
	}

	public void setRuoloopId(Integer ruoloopId) {
		this.ruoloopId = ruoloopId;
	}

	public String getHtmlInformativa() {
		return htmlInformativa;
	}

	public void setHtmlInformativa(String htmlInformativa) {
		this.htmlInformativa = htmlInformativa;
	}

	public String getDescInformativa() {
		return descInformativa;
	}

	public void setDescInformativa(String descInformativa) {
		this.descInformativa = descInformativa;
	}
}
