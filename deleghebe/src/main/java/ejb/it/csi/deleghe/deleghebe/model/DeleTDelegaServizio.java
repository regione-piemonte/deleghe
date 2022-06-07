/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
 * The persistent class for the dele_t_delega_servizio database table.
 * 
 */
@Entity
@Table(name="dele_t_delega_servizio")
@NamedQuery(name="DeleTDelegaServizio.findAll", query="SELECT d FROM DeleTDelegaServizio d")
public class DeleTDelegaServizio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DELE_T_DELEGA_SERVIZIO_DELID_GENERATOR", sequenceName="DELE_T_DELEGA_SERVIZIO_DEL_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DELE_T_DELEGA_SERVIZIO_DELID_GENERATOR")
	@Column(name="del_id")
	private Integer delId;

	@Column(name="data_cancellazione")
	private Date dataCancellazione;

	@Column(name="data_creazione")
	private Date dataCreazione;

	@Column(name="data_modifica")
	private Date dataModifica;

	@Column(name="del_datadecorrenza")
	private Date delDatadecorrenza;

	@Column(name="del_datarevoca")
	private Date delDatarevoca;

	@Column(name="del_datarinuncia")
	private Date delDatarinuncia;

	@Column(name="del_datascadenza")
	private Date delDatascadenza;

	@Column(name="login_operazione")
	private String loginOperazione;

	@Column(name = "uuid")
	@org.hibernate.annotations.Type(type="pg-uuid")
	private UUID uuid;

	//bi-directional many-to-one association to DeleSDelegaServizio
	@OneToMany(mappedBy="deleTDelegaServizio")
	private List<DeleSDelegaServizio> deleSDelegaServizios;

	//bi-directional many-to-one association to CsiLogAudit
	@ManyToOne
	@JoinColumn(name="audit_id")
	private CsiLogAudit csiLogAudit;

	//bi-directional many-to-one association to DeleDDelegaServizioStato
	@ManyToOne
	@JoinColumn(name="delstato_id")
	private DeleDDelegaServizioStato deleDDelegaServizioStato;

	//bi-directional many-to-one association to DeleDRuoloOp
	@ManyToOne
	@JoinColumn(name="ruoloop_id")
	private DeleDRuoloOp deleDRuoloOp;

	//bi-directional many-to-one association to DeleDServizio
	@ManyToOne
	@JoinColumn(name="ser_id")
	private DeleDServizio deleDServizio;

	//bi-directional many-to-one association to DeleTDelega
	@ManyToOne
	@JoinColumn(name="dlga_id")
	private DeleTDelega deleTDelega;

	//bi-directional many-to-one association to DeleDGrado
	@ManyToOne
	@JoinColumn(name="del_grado_id ")
	private DeleDGrado deleDGrado;

	public DeleTDelegaServizio() {
	}

	public Integer getDelId() {
		return this.delId;
	}

	public void setDelId(Integer delId) {
		this.delId = delId;
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

	public Date getDelDatadecorrenza() {
		return this.delDatadecorrenza;
	}

	public void setDelDatadecorrenza(Date delDatadecorrenza) {
		this.delDatadecorrenza = delDatadecorrenza;
	}

	public Date getDelDatarevoca() {
		return this.delDatarevoca;
	}

	public void setDelDatarevoca(Date delDatarevoca) {
		this.delDatarevoca = delDatarevoca;
	}

	public Date getDelDatarinuncia() {
		return this.delDatarinuncia;
	}

	public void setDelDatarinuncia(Date delDatarinuncia) {
		this.delDatarinuncia = delDatarinuncia;
	}

	public Date getDelDatascadenza() {
		return this.delDatascadenza;
	}

	public void setDelDatascadenza(Date delDatascadenza) {
		this.delDatascadenza = delDatascadenza;
	}

	public String getLoginOperazione() {
		return this.loginOperazione;
	}

	public void setLoginOperazione(String loginOperazione) {
		this.loginOperazione = loginOperazione;
	}

	public UUID getUuid() {
		return this.uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public List<DeleSDelegaServizio> getDeleSDelegaServizios() {
		return this.deleSDelegaServizios;
	}

	public void setDeleSDelegaServizios(List<DeleSDelegaServizio> deleSDelegaServizios) {
		this.deleSDelegaServizios = deleSDelegaServizios;
	}

	public DeleSDelegaServizio addDeleSDelegaServizio(DeleSDelegaServizio deleSDelegaServizio) {
		getDeleSDelegaServizios().add(deleSDelegaServizio);
		deleSDelegaServizio.setDeleTDelegaServizio(this);

		return deleSDelegaServizio;
	}

	public DeleSDelegaServizio removeDeleSDelegaServizio(DeleSDelegaServizio deleSDelegaServizio) {
		getDeleSDelegaServizios().remove(deleSDelegaServizio);
		deleSDelegaServizio.setDeleTDelegaServizio(null);

		return deleSDelegaServizio;
	}

	public CsiLogAudit getCsiLogAudit() {
		return this.csiLogAudit;
	}

	public void setCsiLogAudit(CsiLogAudit csiLogAudit) {
		this.csiLogAudit = csiLogAudit;
	}

	public DeleDDelegaServizioStato getDeleDDelegaServizioStato() {
		return this.deleDDelegaServizioStato;
	}

	public void setDeleDDelegaServizioStato(DeleDDelegaServizioStato deleDDelegaServizioStato) {
		this.deleDDelegaServizioStato = deleDDelegaServizioStato;
	}

	public DeleDRuoloOp getDeleDRuoloOp() {
		return this.deleDRuoloOp;
	}

	public void setDeleDRuoloOp(DeleDRuoloOp deleDRuoloOp) {
		this.deleDRuoloOp = deleDRuoloOp;
	}

	public DeleDServizio getDeleDServizio() {
		return this.deleDServizio;
	}

	public void setDeleDServizio(DeleDServizio deleDServizio) {
		this.deleDServizio = deleDServizio;
	}

	public DeleTDelega getDeleTDelega() {
		return this.deleTDelega;
	}

	public void setDeleTDelega(DeleTDelega deleTDelega) {
		this.deleTDelega = deleTDelega;
	}

	public DeleDGrado getDeleDGrado() {
		return deleDGrado;
	}

	public void setDeleDGrado(DeleDGrado deleDGrado) {
		this.deleDGrado = deleDGrado;
	}
}