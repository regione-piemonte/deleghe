/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
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
 * The persistent class for the dele_t_dichiarazione database table.
 * 
 */
@Entity
@Table(name="dele_t_dichiarazione")
@NamedQuery(name="DeleTDichiarazione.findAll", query="SELECT d FROM DeleTDichiarazione d")
public class DeleTDichiarazione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DELE_T_DICHIARAZIONE_DICID_GENERATOR", sequenceName="DELE_T_DICHIARAZIONE_DIC_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DELE_T_DICHIARAZIONE_DICID_GENERATOR")
	@Column(name="dic_id")
	private Integer dicId;

	@Column(name="data_cancellazione")
	private Date dataCancellazione;

	@Column(name="data_creazione")
	private Date dataCreazione;

	@Column(name="data_modifica")
	private Date dataModifica;

	@Column(name="login_operazione")
	private String loginOperazione;
	
	//nuovi 4 campi:numero_documento; autorita; data_inizio_tutela; data_fine_tutela  
	@Column(name="numero_documento")
	private String numeroDocumento;
	
	@Column(name="autorita")
	private String autorita;
	
	@Column(name="data_inizio_tutela")
	private Date dataInizioTutela;
	
	@Column(name="data_fine_tutela")
	private Date dataFineTutela;

	//bi-directional many-to-one association to DeleSDichiarazione
	@OneToMany(mappedBy="deleTDichiarazione")
	private List<DeleSDichiarazione> deleSDichiaraziones;

	//bi-directional many-to-one association to DeleDDichiarazioneModo
	@ManyToOne
	@JoinColumn(name="dic_modo_id")
	private DeleDDichiarazioneModo deleDDichiarazioneModo;

	//bi-directional many-to-one association to DeleDDichiarazioneStato
	@ManyToOne
	@JoinColumn(name="dic_stato_id")
	private DeleDDichiarazioneStato deleDDichiarazioneStato;

	//bi-directional many-to-one association to DeleDDichiarazioneTipo
	@ManyToOne
	@JoinColumn(name="dic_tipo_id")
	private DeleDDichiarazioneTipo deleDDichiarazioneTipo;

	//bi-directional many-to-one association to DeleDRuoloOp
	@ManyToOne
	@JoinColumn(name="ruoloop_id")
	private DeleDRuoloOp deleDRuoloOp;

	//bi-directional many-to-one association to DeleTCittadino
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="cit_id")
	private DeleTCittadino deleTCittadino;

	//bi-directional many-to-one association to DeleTDichiarazioneDet
	@OneToMany(mappedBy="deleTDichiarazione",cascade = CascadeType.ALL)
	private List<DeleTDichiarazioneDet> deleTDichiarazioneDets;

	@Column(name = "uuid")
	@org.hibernate.annotations.Type(type="pg-uuid")
	private UUID uuid;

	@Column(name="compilatore_cf")
	private String compilatoreCf;

	public DeleTDichiarazione() {
	}

	public Integer getDicId() {
		return this.dicId;
	}

	public void setDicId(Integer dicId) {
		this.dicId = dicId;
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

	public List<DeleSDichiarazione> getDeleSDichiaraziones() {
		return this.deleSDichiaraziones;
	}

	public void setDeleSDichiaraziones(List<DeleSDichiarazione> deleSDichiaraziones) {
		this.deleSDichiaraziones = deleSDichiaraziones;
	}

	public DeleSDichiarazione addDeleSDichiarazione(DeleSDichiarazione deleSDichiarazione) {
		getDeleSDichiaraziones().add(deleSDichiarazione);
		deleSDichiarazione.setDeleTDichiarazione(this);

		return deleSDichiarazione;
	}

	public DeleSDichiarazione removeDeleSDichiarazione(DeleSDichiarazione deleSDichiarazione) {
		getDeleSDichiaraziones().remove(deleSDichiarazione);
		deleSDichiarazione.setDeleTDichiarazione(null);

		return deleSDichiarazione;
	}

	public DeleDDichiarazioneModo getDeleDDichiarazioneModo() {
		return this.deleDDichiarazioneModo;
	}

	public void setDeleDDichiarazioneModo(DeleDDichiarazioneModo deleDDichiarazioneModo) {
		this.deleDDichiarazioneModo = deleDDichiarazioneModo;
	}

	public DeleDDichiarazioneStato getDeleDDichiarazioneStato() {
		return this.deleDDichiarazioneStato;
	}

	public void setDeleDDichiarazioneStato(DeleDDichiarazioneStato deleDDichiarazioneStato) {
		this.deleDDichiarazioneStato = deleDDichiarazioneStato;
	}

	public DeleDDichiarazioneTipo getDeleDDichiarazioneTipo() {
		return this.deleDDichiarazioneTipo;
	}

	public void setDeleDDichiarazioneTipo(DeleDDichiarazioneTipo deleDDichiarazioneTipo) {
		this.deleDDichiarazioneTipo = deleDDichiarazioneTipo;
	}

	public DeleDRuoloOp getDeleDRuoloOp() {
		return this.deleDRuoloOp;
	}

	public void setDeleDRuoloOp(DeleDRuoloOp deleDRuoloOp) {
		this.deleDRuoloOp = deleDRuoloOp;
	}

	public DeleTCittadino getDeleTCittadino() {
		return this.deleTCittadino;
	}

	public void setDeleTCittadino(DeleTCittadino deleTCittadino) {
		this.deleTCittadino = deleTCittadino;
	}

	public List<DeleTDichiarazioneDet> getDeleTDichiarazioneDets() {
		return this.deleTDichiarazioneDets;
	}

	public void setDeleTDichiarazioneDets(List<DeleTDichiarazioneDet> deleTDichiarazioneDets) {
		this.deleTDichiarazioneDets = deleTDichiarazioneDets;
	}

	public DeleTDichiarazioneDet addDeleTDichiarazioneDet(DeleTDichiarazioneDet deleTDichiarazioneDet) {
		getDeleTDichiarazioneDets().add(deleTDichiarazioneDet);
		deleTDichiarazioneDet.setDeleTDichiarazione(this);

		return deleTDichiarazioneDet;
	}

	public DeleTDichiarazioneDet removeDeleTDichiarazioneDet(DeleTDichiarazioneDet deleTDichiarazioneDet) {
		getDeleTDichiarazioneDets().remove(deleTDichiarazioneDet);
		deleTDichiarazioneDet.setDeleTDichiarazione(null);

		return deleTDichiarazioneDet;
	}
	
	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getCompilatoreCf() {
		return compilatoreCf;
	}

	public void setCompilatoreCf(String compilatoreCf) {
		this.compilatoreCf = compilatoreCf;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getAutorita() {
		return autorita;
	}

	public void setAutorita(String autorita) {
		this.autorita = autorita;
	}

	public Date getDataInizioTutela() {
		return dataInizioTutela;
	}

	public void setDataInizioTutela(Date dataInizioTutela) {
		this.dataInizioTutela = dataInizioTutela;
	}

	public Date getDataFineTutela() {
		return dataFineTutela;
	}

	public void setDataFineTutela(Date dataFineTutela) {
		this.dataFineTutela = dataFineTutela;
	}
}