/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the dele_d_parametro database table.
 * 
 */
@Entity
@Table(name="dele_d_parametro")
@NamedQuery(name="DeleDParametro.findAll", query="SELECT d FROM DeleDParametro d")
public class DeleDParametro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DELE_D_PARAMETRO_PARID_GENERATOR", sequenceName="DELE_D_PARAMETRO_PAR_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DELE_D_PARAMETRO_PARID_GENERATOR")
	@Column(name="par_id")
	private Integer parId;

	@Column(name="data_cancellazione")
	private Date dataCancellazione;

	@Column(name="data_creazione")
	private Date dataCreazione;

	@Column(name="data_modifica")
	private Date dataModifica;

	@Column(name="login_operazione")
	private String loginOperazione;

	@Column(name="par_cod")
	private String parCod;

	@Column(name="par_desc")
	private String parDesc;

	@Column(name="par_valore")
	private String parValore;

	//bi-directional many-to-one association to DeleDRuoloOp
	@ManyToOne
	@JoinColumn(name="ruoloop_id")
	private DeleDRuoloOp deleDRuoloOp;

	public DeleDParametro() {
	}

	public Integer getParId() {
		return this.parId;
	}

	public void setParId(Integer parId) {
		this.parId = parId;
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

	public Date getDataModifica() {
		return this.dataModifica;
	}

	public void setDataModifica(Date dataModifica) {
		this.dataModifica = dataModifica;
	}

	public String getLoginOperazione() {
		return this.loginOperazione;
	}

	public void setLoginOperazione(String loginOperazione) {
		this.loginOperazione = loginOperazione;
	}

	public String getParCod() {
		return this.parCod;
	}

	public void setParCod(String parCod) {
		this.parCod = parCod;
	}

	public String getParDesc() {
		return this.parDesc;
	}

	public void setParDesc(String parDesc) {
		this.parDesc = parDesc;
	}

	public String getParValore() {
		return this.parValore;
	}

	public void setParValore(String parValore) {
		this.parValore = parValore;
	}

	public DeleDRuoloOp getDeleDRuoloOp() {
		return this.deleDRuoloOp;
	}

	public void setDeleDRuoloOp(DeleDRuoloOp deleDRuoloOp) {
		this.deleDRuoloOp = deleDRuoloOp;
	}

}