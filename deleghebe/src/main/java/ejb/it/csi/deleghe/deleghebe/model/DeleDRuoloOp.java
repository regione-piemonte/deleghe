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
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the dele_d_ruolo_op database table.
 * 
 */
@Entity
@Table(name="dele_d_ruolo_op")
@NamedQuery(name="DeleDRuoloOp.findAll", query="SELECT d FROM DeleDRuoloOp d")
public class DeleDRuoloOp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DELE_D_RUOLO_OP_RUOLOOPID_GENERATOR", sequenceName="DELE_D_RUOLO_OP_RUOLOOP_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DELE_D_RUOLO_OP_RUOLOOPID_GENERATOR")
	@Column(name="ruoloop_id")
	private Integer ruoloopId;

	@Column(name="data_cancellazione")
	private Date dataCancellazione;

	@Column(name="data_creazione")
	private Date dataCreazione;

	@Column(name="data_modifica")
	private Date dataModifica;

	@Column(name="login_operazione")
	private String loginOperazione;

	@Column(name="ruoloop_aslall")
	private Boolean ruoloopAslall;

	@Column(name="ruoloop_cod")
	private String ruoloopCod;

	@Column(name="ruoloop_desc")
	private String ruoloopDesc;

	//bi-directional many-to-one association to DServizio
	@OneToMany(mappedBy="deleDRuoloOp")
	private List<DServizio> DServizios;

	//bi-directional many-to-one association to DeleDCittadinoDatiStato
	@OneToMany(mappedBy="deleDRuoloOp")
	private List<DeleDCittadinoDatiStato> deleDCittadinoDatiStatos;

	//bi-directional many-to-one association to DeleDDelegaServizioStato
	@OneToMany(mappedBy="deleDRuoloOp")
	private List<DeleDDelegaServizioStato> deleDDelegaServizioStatos;

	//bi-directional many-to-one association to DeleDDichiarazioneDetStato
	@OneToMany(mappedBy="deleDRuoloOp")
	private List<DeleDDichiarazioneDetStato> deleDDichiarazioneDetStatos;

	//bi-directional many-to-one association to DeleDDichiarazioneModo
	@OneToMany(mappedBy="deleDRuoloOp")
	private List<DeleDDichiarazioneModo> deleDDichiarazioneModos;

	//bi-directional many-to-one association to DeleDDichiarazioneRuolo
	@OneToMany(mappedBy="deleDRuoloOp")
	private List<DeleDDichiarazioneRuolo> deleDDichiarazioneRuolos;

	//bi-directional many-to-one association to DeleDDichiarazioneStato
	@OneToMany(mappedBy="deleDRuoloOp")
	private List<DeleDDichiarazioneStato> deleDDichiarazioneStatos;

	//bi-directional many-to-one association to DeleDDichiarazioneTipo
	@OneToMany(mappedBy="deleDRuoloOp")
	private List<DeleDDichiarazioneTipo> deleDDichiarazioneTipos;

	//bi-directional many-to-one association to DeleDDocumentoTipo
	@OneToMany(mappedBy="deleDRuoloOp")
	private List<DeleDDocumentoTipo> deleDDocumentoTipos;

	//bi-directional many-to-one association to DeleDErrore
	@OneToMany(mappedBy="deleDRuoloOp")
	private List<DeleDErrore> deleDErrores;

	//bi-directional many-to-one association to DeleDParametro
	@OneToMany(mappedBy="deleDRuoloOp")
	private List<DeleDParametro> deleDParametros;

	//bi-directional many-to-one association to DeleDServizio
	@OneToMany(mappedBy="deleDRuoloOp")
	private List<DeleDServizio> deleDServizios;

	//bi-directional many-to-one association to DeleROperatoreAsl
	@OneToMany(mappedBy="deleDRuoloOp")
	private List<DeleROperatoreAsl> deleROperatoreAsls;

	//bi-directional many-to-one association to DeleSCittadino
	@OneToMany(mappedBy="deleDRuoloOp")
	private List<DeleSCittadino> deleSCittadinos;

	//bi-directional many-to-one association to DeleSDelegaServizio
	@OneToMany(mappedBy="deleDRuoloOp")
	private List<DeleSDelegaServizio> deleSDelegaServizios;

	//bi-directional many-to-one association to DeleSDichiarazione
	@OneToMany(mappedBy="deleDRuoloOp")
	private List<DeleSDichiarazione> deleSDichiaraziones;

	//bi-directional many-to-one association to DeleSDichiarazioneDet
	@OneToMany(mappedBy="deleDRuoloOp")
	private List<DeleSDichiarazioneDet> deleSDichiarazioneDets;

	//bi-directional many-to-one association to DeleTCittadino
	@OneToMany(mappedBy="deleDRuoloOp")
	private List<DeleTCittadino> deleTCittadinos;

	//bi-directional many-to-one association to DeleTDelega
	@OneToMany(mappedBy="deleDRuoloOp")
	private List<DeleTDelega> deleTDelegas;

	//bi-directional many-to-one association to DeleTDelegaServizio
	@OneToMany(mappedBy="deleDRuoloOp")
	private List<DeleTDelegaServizio> deleTDelegaServizios;

	//bi-directional many-to-one association to DeleTDelegato
	@OneToMany(mappedBy="deleDRuoloOp")
	private List<DeleTDelegato> deleTDelegatos;

	//bi-directional many-to-one association to DeleTDichiarazione
	@OneToMany(mappedBy="deleDRuoloOp")
	private List<DeleTDichiarazione> deleTDichiaraziones;

	//bi-directional many-to-one association to DeleTDichiarazioneDet
	@OneToMany(mappedBy="deleDRuoloOp")
	private List<DeleTDichiarazioneDet> deleTDichiarazioneDets;

	//bi-directional many-to-one association to DeleTDocumento
	@OneToMany(mappedBy="deleDRuoloOp")
	private List<DeleTDocumento> deleTDocumentos;

	//bi-directional many-to-one association to DeleTServizioParametro
	@OneToMany(mappedBy="deleDRuoloOp")
	private List<DeleTServizioParametro> deleTServizioParametros;

	//bi-directional many-to-one association to DeleDDelegaTipo
	@OneToMany(mappedBy="deleDRuoloOp")
	private List<DeleDDelegaTipo> deleDDelegaTipos;

	//bi-directional many-to-one association to DeleDGrado
	@OneToMany(mappedBy="deleDRuoloOp")
	private List<DeleDGrado> deleDGrados;

	//bi-directional many-to-one association to DeleRServizioDelegaTipoGrado
	@OneToMany(mappedBy="deleDRuoloOp")
	private List<DeleRServizioDelegaTipoGrado> deleRServizioDelegaTipoGrados;

	//bi-directional many-to-one association to DeleDCittadinoDatiStato
	@OneToMany(mappedBy="deleDRuoloOp")
	private List<DeleDCittadinoUtenzaStato> deleDCittadinoUtenzaStato;

	//bi-directional many-to-one association to DeleDDelegaStato
	@OneToMany(mappedBy="deleDRuoloOp")
	private List<DeleDDelegaStato> deleDDelegaStatos;

	//bi-directional many-to-one association to DeleSDelega
	@OneToMany(mappedBy="deleDRuoloOp")
	private List<DeleSDelega> deleSDelegas;

	//bi-directional many-to-one association to DeleDEnumerazione
	@OneToMany(mappedBy="deleDRuoloOp")
	private List<DeleDEnumerazione> deleDEnumeraziones;

	public DeleDRuoloOp() {
	}

	public Integer getRuoloopId() {
		return this.ruoloopId;
	}

	public void setRuoloopId(Integer ruoloopId) {
		this.ruoloopId = ruoloopId;
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

	public Boolean getRuoloopAslall() {
		return this.ruoloopAslall;
	}

	public void setRuoloopAslall(Boolean ruoloopAslall) {
		this.ruoloopAslall = ruoloopAslall;
	}

	public String getRuoloopCod() {
		return this.ruoloopCod;
	}

	public void setRuoloopCod(String ruoloopCod) {
		this.ruoloopCod = ruoloopCod;
	}

	public String getRuoloopDesc() {
		return this.ruoloopDesc;
	}

	public void setRuoloopDesc(String ruoloopDesc) {
		this.ruoloopDesc = ruoloopDesc;
	}

	public List<DServizio> getDServizios() {
		return this.DServizios;
	}

	public void setDServizios(List<DServizio> DServizios) {
		this.DServizios = DServizios;
	}

	public DServizio addDServizio(DServizio DServizio) {
		getDServizios().add(DServizio);
		DServizio.setDeleDRuoloOp(this);

		return DServizio;
	}

	public DServizio removeDServizio(DServizio DServizio) {
		getDServizios().remove(DServizio);
		DServizio.setDeleDRuoloOp(null);

		return DServizio;
	}

	public List<DeleDCittadinoDatiStato> getDeleDCittadinoDatiStatos() {
		return this.deleDCittadinoDatiStatos;
	}

	public void setDeleDCittadinoDatiStatos(List<DeleDCittadinoDatiStato> deleDCittadinoDatiStatos) {
		this.deleDCittadinoDatiStatos = deleDCittadinoDatiStatos;
	}

	public DeleDCittadinoDatiStato addDeleDCittadinoDatiStato(DeleDCittadinoDatiStato deleDCittadinoDatiStato) {
		getDeleDCittadinoDatiStatos().add(deleDCittadinoDatiStato);
		deleDCittadinoDatiStato.setDeleDRuoloOp(this);

		return deleDCittadinoDatiStato;
	}

	public DeleDCittadinoDatiStato removeDeleDCittadinoDatiStato(DeleDCittadinoDatiStato deleDCittadinoDatiStato) {
		getDeleDCittadinoDatiStatos().remove(deleDCittadinoDatiStato);
		deleDCittadinoDatiStato.setDeleDRuoloOp(null);

		return deleDCittadinoDatiStato;
	}


	public List<DeleDCittadinoUtenzaStato> getDeleDCittadinoUtenzaStatos() {
		return deleDCittadinoUtenzaStato;
	}

	public void setDeleDCittadinoUtenzaStato(List<DeleDCittadinoUtenzaStato> deleDCittadinoUtenzaStato) {
		this.deleDCittadinoUtenzaStato = deleDCittadinoUtenzaStato;
	}

	public DeleDCittadinoUtenzaStato addDeleDCittadinoUtenzaStato(DeleDCittadinoUtenzaStato deleDCittadinoUtenzaStato) {
		getDeleDCittadinoUtenzaStatos().add(deleDCittadinoUtenzaStato);
		deleDCittadinoUtenzaStato.setDeleDRuoloOp(this);

		return deleDCittadinoUtenzaStato;
	}

	public DeleDCittadinoUtenzaStato removeDeleDCittadinoUtenzaStato(DeleDCittadinoUtenzaStato deleDCittadinoUtenzaStato) {
		getDeleDCittadinoUtenzaStatos().remove(deleDCittadinoUtenzaStato);
		deleDCittadinoUtenzaStato.setDeleDRuoloOp(null);

		return deleDCittadinoUtenzaStato;
	}


	public List<DeleDDelegaServizioStato> getDeleDDelegaServizioStatos() {
		return this.deleDDelegaServizioStatos;
	}

	public void setDeleDDelegaServizioStatos(List<DeleDDelegaServizioStato> deleDDelegaServizioStatos) {
		this.deleDDelegaServizioStatos = deleDDelegaServizioStatos;
	}

	public DeleDDelegaServizioStato addDeleDDelegaServizioStato(DeleDDelegaServizioStato deleDDelegaServizioStato) {
		getDeleDDelegaServizioStatos().add(deleDDelegaServizioStato);
		deleDDelegaServizioStato.setDeleDRuoloOp(this);

		return deleDDelegaServizioStato;
	}

	public DeleDDelegaServizioStato removeDeleDDelegaServizioStato(DeleDDelegaServizioStato deleDDelegaServizioStato) {
		getDeleDDelegaServizioStatos().remove(deleDDelegaServizioStato);
		deleDDelegaServizioStato.setDeleDRuoloOp(null);

		return deleDDelegaServizioStato;
	}

	public List<DeleDDichiarazioneDetStato> getDeleDDichiarazioneDetStatos() {
		return this.deleDDichiarazioneDetStatos;
	}

	public void setDeleDDichiarazioneDetStatos(List<DeleDDichiarazioneDetStato> deleDDichiarazioneDetStatos) {
		this.deleDDichiarazioneDetStatos = deleDDichiarazioneDetStatos;
	}

	public DeleDDichiarazioneDetStato addDeleDDichiarazioneDetStato(DeleDDichiarazioneDetStato deleDDichiarazioneDetStato) {
		getDeleDDichiarazioneDetStatos().add(deleDDichiarazioneDetStato);
		deleDDichiarazioneDetStato.setDeleDRuoloOp(this);

		return deleDDichiarazioneDetStato;
	}

	public DeleDDichiarazioneDetStato removeDeleDDichiarazioneDetStato(DeleDDichiarazioneDetStato deleDDichiarazioneDetStato) {
		getDeleDDichiarazioneDetStatos().remove(deleDDichiarazioneDetStato);
		deleDDichiarazioneDetStato.setDeleDRuoloOp(null);

		return deleDDichiarazioneDetStato;
	}

	public List<DeleDDichiarazioneModo> getDeleDDichiarazioneModos() {
		return this.deleDDichiarazioneModos;
	}

	public void setDeleDDichiarazioneModos(List<DeleDDichiarazioneModo> deleDDichiarazioneModos) {
		this.deleDDichiarazioneModos = deleDDichiarazioneModos;
	}

	public DeleDDichiarazioneModo addDeleDDichiarazioneModo(DeleDDichiarazioneModo deleDDichiarazioneModo) {
		getDeleDDichiarazioneModos().add(deleDDichiarazioneModo);
		deleDDichiarazioneModo.setDeleDRuoloOp(this);

		return deleDDichiarazioneModo;
	}

	public DeleDDichiarazioneModo removeDeleDDichiarazioneModo(DeleDDichiarazioneModo deleDDichiarazioneModo) {
		getDeleDDichiarazioneModos().remove(deleDDichiarazioneModo);
		deleDDichiarazioneModo.setDeleDRuoloOp(null);

		return deleDDichiarazioneModo;
	}

	public List<DeleDDichiarazioneRuolo> getDeleDDichiarazioneRuolos() {
		return this.deleDDichiarazioneRuolos;
	}

	public void setDeleDDichiarazioneRuolos(List<DeleDDichiarazioneRuolo> deleDDichiarazioneRuolos) {
		this.deleDDichiarazioneRuolos = deleDDichiarazioneRuolos;
	}

	public DeleDDichiarazioneRuolo addDeleDDichiarazioneRuolo(DeleDDichiarazioneRuolo deleDDichiarazioneRuolo) {
		getDeleDDichiarazioneRuolos().add(deleDDichiarazioneRuolo);
		deleDDichiarazioneRuolo.setDeleDRuoloOp(this);

		return deleDDichiarazioneRuolo;
	}

	public DeleDDichiarazioneRuolo removeDeleDDichiarazioneRuolo(DeleDDichiarazioneRuolo deleDDichiarazioneRuolo) {
		getDeleDDichiarazioneRuolos().remove(deleDDichiarazioneRuolo);
		deleDDichiarazioneRuolo.setDeleDRuoloOp(null);

		return deleDDichiarazioneRuolo;
	}

	public List<DeleDDichiarazioneStato> getDeleDDichiarazioneStatos() {
		return this.deleDDichiarazioneStatos;
	}

	public void setDeleDDichiarazioneStatos(List<DeleDDichiarazioneStato> deleDDichiarazioneStatos) {
		this.deleDDichiarazioneStatos = deleDDichiarazioneStatos;
	}

	public DeleDDichiarazioneStato addDeleDDichiarazioneStato(DeleDDichiarazioneStato deleDDichiarazioneStato) {
		getDeleDDichiarazioneStatos().add(deleDDichiarazioneStato);
		deleDDichiarazioneStato.setDeleDRuoloOp(this);

		return deleDDichiarazioneStato;
	}

	public DeleDDichiarazioneStato removeDeleDDichiarazioneStato(DeleDDichiarazioneStato deleDDichiarazioneStato) {
		getDeleDDichiarazioneStatos().remove(deleDDichiarazioneStato);
		deleDDichiarazioneStato.setDeleDRuoloOp(null);

		return deleDDichiarazioneStato;
	}

	public List<DeleDDichiarazioneTipo> getDeleDDichiarazioneTipos() {
		return this.deleDDichiarazioneTipos;
	}

	public void setDeleDDichiarazioneTipos(List<DeleDDichiarazioneTipo> deleDDichiarazioneTipos) {
		this.deleDDichiarazioneTipos = deleDDichiarazioneTipos;
	}

	public DeleDDichiarazioneTipo addDeleDDichiarazioneTipo(DeleDDichiarazioneTipo deleDDichiarazioneTipo) {
		getDeleDDichiarazioneTipos().add(deleDDichiarazioneTipo);
		deleDDichiarazioneTipo.setDeleDRuoloOp(this);

		return deleDDichiarazioneTipo;
	}

	public DeleDDichiarazioneTipo removeDeleDDichiarazioneTipo(DeleDDichiarazioneTipo deleDDichiarazioneTipo) {
		getDeleDDichiarazioneTipos().remove(deleDDichiarazioneTipo);
		deleDDichiarazioneTipo.setDeleDRuoloOp(null);

		return deleDDichiarazioneTipo;
	}

	public List<DeleDDocumentoTipo> getDeleDDocumentoTipos() {
		return this.deleDDocumentoTipos;
	}

	public void setDeleDDocumentoTipos(List<DeleDDocumentoTipo> deleDDocumentoTipos) {
		this.deleDDocumentoTipos = deleDDocumentoTipos;
	}

	public DeleDDocumentoTipo addDeleDDocumentoTipo(DeleDDocumentoTipo deleDDocumentoTipo) {
		getDeleDDocumentoTipos().add(deleDDocumentoTipo);
		deleDDocumentoTipo.setDeleDRuoloOp(this);

		return deleDDocumentoTipo;
	}

	public DeleDDocumentoTipo removeDeleDDocumentoTipo(DeleDDocumentoTipo deleDDocumentoTipo) {
		getDeleDDocumentoTipos().remove(deleDDocumentoTipo);
		deleDDocumentoTipo.setDeleDRuoloOp(null);

		return deleDDocumentoTipo;
	}

	public List<DeleDErrore> getDeleDErrores() {
		return this.deleDErrores;
	}

	public void setDeleDErrores(List<DeleDErrore> deleDErrores) {
		this.deleDErrores = deleDErrores;
	}

	public DeleDErrore addDeleDErrore(DeleDErrore deleDErrore) {
		getDeleDErrores().add(deleDErrore);
		deleDErrore.setDeleDRuoloOp(this);

		return deleDErrore;
	}

	public DeleDErrore removeDeleDErrore(DeleDErrore deleDErrore) {
		getDeleDErrores().remove(deleDErrore);
		deleDErrore.setDeleDRuoloOp(null);

		return deleDErrore;
	}

	public List<DeleDParametro> getDeleDParametros() {
		return this.deleDParametros;
	}

	public void setDeleDParametros(List<DeleDParametro> deleDParametros) {
		this.deleDParametros = deleDParametros;
	}

	public DeleDParametro addDeleDParametro(DeleDParametro deleDParametro) {
		getDeleDParametros().add(deleDParametro);
		deleDParametro.setDeleDRuoloOp(this);

		return deleDParametro;
	}

	public DeleDParametro removeDeleDParametro(DeleDParametro deleDParametro) {
		getDeleDParametros().remove(deleDParametro);
		deleDParametro.setDeleDRuoloOp(null);

		return deleDParametro;
	}

	public List<DeleDServizio> getDeleDServizios() {
		return this.deleDServizios;
	}

	public void setDeleDServizios(List<DeleDServizio> deleDServizios) {
		this.deleDServizios = deleDServizios;
	}

	public DeleDServizio addDeleDServizio(DeleDServizio deleDServizio) {
		getDeleDServizios().add(deleDServizio);
		deleDServizio.setDeleDRuoloOp(this);

		return deleDServizio;
	}

	public DeleDServizio removeDeleDServizio(DeleDServizio deleDServizio) {
		getDeleDServizios().remove(deleDServizio);
		deleDServizio.setDeleDRuoloOp(null);

		return deleDServizio;
	}

	public List<DeleROperatoreAsl> getDeleROperatoreAsls() {
		return this.deleROperatoreAsls;
	}

	public void setDeleROperatoreAsls(List<DeleROperatoreAsl> deleROperatoreAsls) {
		this.deleROperatoreAsls = deleROperatoreAsls;
	}

	public DeleROperatoreAsl addDeleROperatoreAsl(DeleROperatoreAsl deleROperatoreAsl) {
		getDeleROperatoreAsls().add(deleROperatoreAsl);
		deleROperatoreAsl.setDeleDRuoloOp(this);

		return deleROperatoreAsl;
	}

	public DeleROperatoreAsl removeDeleROperatoreAsl(DeleROperatoreAsl deleROperatoreAsl) {
		getDeleROperatoreAsls().remove(deleROperatoreAsl);
		deleROperatoreAsl.setDeleDRuoloOp(null);

		return deleROperatoreAsl;
	}

	public List<DeleSCittadino> getDeleSCittadinos() {
		return this.deleSCittadinos;
	}

	public void setDeleSCittadinos(List<DeleSCittadino> deleSCittadinos) {
		this.deleSCittadinos = deleSCittadinos;
	}

	public DeleSCittadino addDeleSCittadino(DeleSCittadino deleSCittadino) {
		getDeleSCittadinos().add(deleSCittadino);
		deleSCittadino.setDeleDRuoloOp(this);

		return deleSCittadino;
	}

	public DeleSCittadino removeDeleSCittadino(DeleSCittadino deleSCittadino) {
		getDeleSCittadinos().remove(deleSCittadino);
		deleSCittadino.setDeleDRuoloOp(null);

		return deleSCittadino;
	}

	public List<DeleSDelegaServizio> getDeleSDelegaServizios() {
		return this.deleSDelegaServizios;
	}

	public void setDeleSDelegaServizios(List<DeleSDelegaServizio> deleSDelegaServizios) {
		this.deleSDelegaServizios = deleSDelegaServizios;
	}

	public DeleSDelegaServizio addDeleSDelegaServizio(DeleSDelegaServizio deleSDelegaServizio) {
		getDeleSDelegaServizios().add(deleSDelegaServizio);
		deleSDelegaServizio.setDeleDRuoloOp(this);

		return deleSDelegaServizio;
	}

	public DeleSDelegaServizio removeDeleSDelegaServizio(DeleSDelegaServizio deleSDelegaServizio) {
		getDeleSDelegaServizios().remove(deleSDelegaServizio);
		deleSDelegaServizio.setDeleDRuoloOp(null);

		return deleSDelegaServizio;
	}

	public List<DeleSDichiarazione> getDeleSDichiaraziones() {
		return this.deleSDichiaraziones;
	}

	public void setDeleSDichiaraziones(List<DeleSDichiarazione> deleSDichiaraziones) {
		this.deleSDichiaraziones = deleSDichiaraziones;
	}

	public DeleSDichiarazione addDeleSDichiarazione(DeleSDichiarazione deleSDichiarazione) {
		getDeleSDichiaraziones().add(deleSDichiarazione);
		deleSDichiarazione.setDeleDRuoloOp(this);

		return deleSDichiarazione;
	}

	public DeleSDichiarazione removeDeleSDichiarazione(DeleSDichiarazione deleSDichiarazione) {
		getDeleSDichiaraziones().remove(deleSDichiarazione);
		deleSDichiarazione.setDeleDRuoloOp(null);

		return deleSDichiarazione;
	}

	public List<DeleSDichiarazioneDet> getDeleSDichiarazioneDets() {
		return this.deleSDichiarazioneDets;
	}

	public void setDeleSDichiarazioneDets(List<DeleSDichiarazioneDet> deleSDichiarazioneDets) {
		this.deleSDichiarazioneDets = deleSDichiarazioneDets;
	}

	public DeleSDichiarazioneDet addDeleSDichiarazioneDet(DeleSDichiarazioneDet deleSDichiarazioneDet) {
		getDeleSDichiarazioneDets().add(deleSDichiarazioneDet);
		deleSDichiarazioneDet.setDeleDRuoloOp(this);

		return deleSDichiarazioneDet;
	}

	public DeleSDichiarazioneDet removeDeleSDichiarazioneDet(DeleSDichiarazioneDet deleSDichiarazioneDet) {
		getDeleSDichiarazioneDets().remove(deleSDichiarazioneDet);
		deleSDichiarazioneDet.setDeleDRuoloOp(null);

		return deleSDichiarazioneDet;
	}

	public List<DeleTCittadino> getDeleTCittadinos() {
		return this.deleTCittadinos;
	}

	public void setDeleTCittadinos(List<DeleTCittadino> deleTCittadinos) {
		this.deleTCittadinos = deleTCittadinos;
	}

	public DeleTCittadino addDeleTCittadino(DeleTCittadino deleTCittadino) {
		getDeleTCittadinos().add(deleTCittadino);
		deleTCittadino.setDeleDRuoloOp(this);

		return deleTCittadino;
	}

	public DeleTCittadino removeDeleTCittadino(DeleTCittadino deleTCittadino) {
		getDeleTCittadinos().remove(deleTCittadino);
		deleTCittadino.setDeleDRuoloOp(null);

		return deleTCittadino;
	}

	public List<DeleTDelega> getDeleTDelegas() {
		return this.deleTDelegas;
	}

	public void setDeleTDelegas(List<DeleTDelega> deleTDelegas) {
		this.deleTDelegas = deleTDelegas;
	}

	public DeleTDelega addDeleTDelega(DeleTDelega deleTDelega) {
		getDeleTDelegas().add(deleTDelega);
		deleTDelega.setDeleDRuoloOp(this);

		return deleTDelega;
	}

	public DeleTDelega removeDeleTDelega(DeleTDelega deleTDelega) {
		getDeleTDelegas().remove(deleTDelega);
		deleTDelega.setDeleDRuoloOp(null);

		return deleTDelega;
	}

	public List<DeleTDelegaServizio> getDeleTDelegaServizios() {
		return this.deleTDelegaServizios;
	}

	public void setDeleTDelegaServizios(List<DeleTDelegaServizio> deleTDelegaServizios) {
		this.deleTDelegaServizios = deleTDelegaServizios;
	}

	public DeleTDelegaServizio addDeleTDelegaServizio(DeleTDelegaServizio deleTDelegaServizio) {
		getDeleTDelegaServizios().add(deleTDelegaServizio);
		deleTDelegaServizio.setDeleDRuoloOp(this);

		return deleTDelegaServizio;
	}

	public DeleTDelegaServizio removeDeleTDelegaServizio(DeleTDelegaServizio deleTDelegaServizio) {
		getDeleTDelegaServizios().remove(deleTDelegaServizio);
		deleTDelegaServizio.setDeleDRuoloOp(null);

		return deleTDelegaServizio;
	}

	public List<DeleTDelegato> getDeleTDelegatos() {
		return this.deleTDelegatos;
	}

	public void setDeleTDelegatos(List<DeleTDelegato> deleTDelegatos) {
		this.deleTDelegatos = deleTDelegatos;
	}

	public DeleTDelegato addDeleTDelegato(DeleTDelegato deleTDelegato) {
		getDeleTDelegatos().add(deleTDelegato);
		deleTDelegato.setDeleDRuoloOp(this);

		return deleTDelegato;
	}

	public DeleTDelegato removeDeleTDelegato(DeleTDelegato deleTDelegato) {
		getDeleTDelegatos().remove(deleTDelegato);
		deleTDelegato.setDeleDRuoloOp(null);

		return deleTDelegato;
	}

	public List<DeleTDichiarazione> getDeleTDichiaraziones() {
		return this.deleTDichiaraziones;
	}

	public void setDeleTDichiaraziones(List<DeleTDichiarazione> deleTDichiaraziones) {
		this.deleTDichiaraziones = deleTDichiaraziones;
	}

	public DeleTDichiarazione addDeleTDichiarazione(DeleTDichiarazione deleTDichiarazione) {
		getDeleTDichiaraziones().add(deleTDichiarazione);
		deleTDichiarazione.setDeleDRuoloOp(this);

		return deleTDichiarazione;
	}

	public DeleTDichiarazione removeDeleTDichiarazione(DeleTDichiarazione deleTDichiarazione) {
		getDeleTDichiaraziones().remove(deleTDichiarazione);
		deleTDichiarazione.setDeleDRuoloOp(null);

		return deleTDichiarazione;
	}

	public List<DeleTDichiarazioneDet> getDeleTDichiarazioneDets() {
		return this.deleTDichiarazioneDets;
	}

	public void setDeleTDichiarazioneDets(List<DeleTDichiarazioneDet> deleTDichiarazioneDets) {
		this.deleTDichiarazioneDets = deleTDichiarazioneDets;
	}

	public DeleTDichiarazioneDet addDeleTDichiarazioneDet(DeleTDichiarazioneDet deleTDichiarazioneDet) {
		getDeleTDichiarazioneDets().add(deleTDichiarazioneDet);
		deleTDichiarazioneDet.setDeleDRuoloOp(this);

		return deleTDichiarazioneDet;
	}

	public DeleTDichiarazioneDet removeDeleTDichiarazioneDet(DeleTDichiarazioneDet deleTDichiarazioneDet) {
		getDeleTDichiarazioneDets().remove(deleTDichiarazioneDet);
		deleTDichiarazioneDet.setDeleDRuoloOp(null);

		return deleTDichiarazioneDet;
	}

	public List<DeleTDocumento> getDeleTDocumentos() {
		return this.deleTDocumentos;
	}

	public void setDeleTDocumentos(List<DeleTDocumento> deleTDocumentos) {
		this.deleTDocumentos = deleTDocumentos;
	}

	public DeleTDocumento addDeleTDocumento(DeleTDocumento deleTDocumento) {
		getDeleTDocumentos().add(deleTDocumento);
		deleTDocumento.setDeleDRuoloOp(this);

		return deleTDocumento;
	}

	public DeleTDocumento removeDeleTDocumento(DeleTDocumento deleTDocumento) {
		getDeleTDocumentos().remove(deleTDocumento);
		deleTDocumento.setDeleDRuoloOp(null);

		return deleTDocumento;
	}

	public List<DeleTServizioParametro> getDeleTServizioParametros() {
		return this.deleTServizioParametros;
	}

	public void setDeleTServizioParametros(List<DeleTServizioParametro> deleTServizioParametros) {
		this.deleTServizioParametros = deleTServizioParametros;
	}

	public DeleTServizioParametro addDeleTServizioParametro(DeleTServizioParametro deleTServizioParametro) {
		getDeleTServizioParametros().add(deleTServizioParametro);
		deleTServizioParametro.setDeleDRuoloOp(this);

		return deleTServizioParametro;
	}

	public DeleTServizioParametro removeDeleTServizioParametro(DeleTServizioParametro deleTServizioParametro) {
		getDeleTServizioParametros().remove(deleTServizioParametro);
		deleTServizioParametro.setDeleDRuoloOp(null);

		return deleTServizioParametro;
	}

	public List<DeleDDelegaTipo> getDeleDDelegaTipos() {
		return deleDDelegaTipos;
	}

	public void setDeleDDelegaTipos(List<DeleDDelegaTipo> deleDDelegaTipos) {
		this.deleDDelegaTipos = deleDDelegaTipos;
	}

	public DeleDDelegaTipo addDeleDDelegaTipo(DeleDDelegaTipo deleDDelegaTipo) {
		getDeleDDelegaTipos().add(deleDDelegaTipo);
		deleDDelegaTipo.setDeleDRuoloOp(this);

		return deleDDelegaTipo;
	}

	public DeleDDelegaTipo removeDeleDDelegaTipo(DeleDDelegaTipo deleDDelegaTipo) {
		getDeleDDelegaTipos().remove(deleDDelegaTipo);
		deleDDelegaTipo.setDeleDRuoloOp(null);

		return deleDDelegaTipo;
	}	
	
	
	public List<DeleDGrado> getDeleDGrados() {
		return deleDGrados;
	}

	public void setDeleDGrados(List<DeleDGrado> deleDGrados) {
		this.deleDGrados = deleDGrados;
	}

	public DeleDGrado addDeleDGrado(DeleDGrado deleDGrado) {
		getDeleDGrados().add(deleDGrado);
		deleDGrado.setDeleDRuoloOp(this);

		return deleDGrado;
	}

	public DeleDGrado removeDeleDGrado(DeleDGrado deleDGrado) {
		getDeleDGrados().remove(deleDGrado);
		deleDGrado.setDeleDRuoloOp(null);

		return deleDGrado;
	}


	public List<DeleRServizioDelegaTipoGrado> getDeleRServizioDelegaTipoGrados() {
		return deleRServizioDelegaTipoGrados;
	}

	public void setDeleRServizioDelegaTipoGrados(List<DeleRServizioDelegaTipoGrado> deleRServizioDelegaTipoGrados) {
		this.deleRServizioDelegaTipoGrados = deleRServizioDelegaTipoGrados;
	}

	public DeleRServizioDelegaTipoGrado addDeleRServizioDelegaTipoGrado(DeleRServizioDelegaTipoGrado deleRServizioDelegaTipoGrado) {
		getDeleRServizioDelegaTipoGrados().add(deleRServizioDelegaTipoGrado);
		deleRServizioDelegaTipoGrado.setDeleDRuoloOp(this);

		return deleRServizioDelegaTipoGrado;
	}

	public DeleRServizioDelegaTipoGrado removeDeleRServizioDelegaTipoGrado(DeleRServizioDelegaTipoGrado deleDGrado) {
		getDeleRServizioDelegaTipoGrados().remove(deleDGrado);
		deleDGrado.setDeleDRuoloOp(null);

		return deleDGrado;
	}

	public List<DeleDDelegaStato> getDeleDDelegaStatos() {
		return deleDDelegaStatos;
	}

	public void setDeleDDelegaStatos(List<DeleDDelegaStato> deleDDelegaStatos) {
		this.deleDDelegaStatos = deleDDelegaStatos;
	}

	public DeleDDelegaStato addDeleDDelegaStato(DeleDDelegaStato deleDDelegaStato) {
		getDeleDDelegaStatos().add(deleDDelegaStato);
		deleDDelegaStato.setDeleDRuoloOp(this);

		return deleDDelegaStato;
	}

	public DeleDDelegaStato removeDeleDDelegaStato(DeleDDelegaStato deleDDelegaStato) {
		getDeleDDelegaStatos().remove(deleDDelegaStato);
		deleDDelegaStato.setDeleDRuoloOp(null);

		return deleDDelegaStato;
	}

	public List<DeleSDelega> getDeleSDelegas() {
		return deleSDelegas;
	}

	public void setDeleSDelegas(List<DeleSDelega> deleSDelegas) {
		this.deleSDelegas = deleSDelegas;
	}

	public DeleSDelega addDeleSDelega(DeleSDelega deleSDelegas) {
		getDeleSDelegas().add(deleSDelegas);
		deleSDelegas.setDeleDRuoloOp(this);

		return deleSDelegas;
	}

	public DeleSDelega removeDeleSDelega(DeleSDelega deleSDelegas) {
		getDeleSDelegas().remove(deleSDelegas);
		deleSDelegas.setDeleDRuoloOp(null);

		return deleSDelegas;
	}

	public List<DeleDCittadinoUtenzaStato> getDeleDCittadinoUtenzaStato() {
		return deleDCittadinoUtenzaStato;
	}

	public List<DeleDEnumerazione> getDeleDEnumeraziones() {
		return deleDEnumeraziones;
	}

	public void setDeleDEnumeraziones(List<DeleDEnumerazione> deleDEnumeraziones) {
		this.deleDEnumeraziones = deleDEnumeraziones;
	}

}