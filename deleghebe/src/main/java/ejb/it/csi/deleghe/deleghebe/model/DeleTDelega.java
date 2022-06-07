/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.model;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
 * The persistent class for the dele_t_delega database table.
 * 
 */
@Entity
@Table(name="dele_t_delega")
@NamedQuery(name="DeleTDelega.findAll", query="SELECT d FROM DeleTDelega d")
public class DeleTDelega implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DELE_T_DELEGA_DLGAID_GENERATOR", sequenceName="DELE_T_DELEGA_DLGA_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DELE_T_DELEGA_DLGAID_GENERATOR")
	@Column(name="dlga_id", unique=true, nullable=false)
	private Integer dlgaId;

	@Column(name="data_cancellazione")
	private java.util.Date dataCancellazione;

	@Column(name="data_creazione")
	private java.util.Date dataCreazione;

	@Column(name="data_modifica")
	private java.util.Date dataModifica;

	@Column(name="login_operazione")
	private String loginOperazione;

	private Boolean presavisione;

	@Column(name="presavisione_data")
	private java.util.Date presavisioneData;

	@Column(name = "uuid")
	@org.hibernate.annotations.Type(type="pg-uuid")
	private UUID uuid;

	//bi-directional many-to-one association to DeleSDelegaServizio
	@OneToMany(mappedBy="deleTDelega")
	private List<DeleSDelegaServizio> deleSDelegaServizios;

	//bi-directional many-to-one association to DeleDRuoloOp
	@ManyToOne
	@JoinColumn(name="ruoloop_id")
	private DeleDRuoloOp deleDRuoloOp;

	//bi-directional many-to-one association to DeleTCittadino
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="cit_id_delegante")
	private DeleTCittadino deleTCittadino1;

	//bi-directional many-to-one association to DeleTCittadino
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="cit_id_delegato")
	private DeleTCittadino deleTCittadino2;

	//bi-directional many-to-one association to DeleTDichiarazioneDet
	@ManyToOne
	@JoinColumn(name="dicdet_id")
	private DeleTDichiarazioneDet deleTDichiarazioneDet;

	//bi-directional many-to-one association to DeleTDelegaServizio
	@OneToMany(mappedBy="deleTDelega",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<DeleTDelegaServizio> deleTDelegaServizios;

	//bi-directional many-to-one association to DeleTDelegato
	@OneToMany(mappedBy="deleTDelega", cascade = CascadeType.ALL)
	private List<DeleTDelegato> deleTDelegatos;

	//bi-directional many-to-one association to DeleDDelegaTipo
	@ManyToOne
	@JoinColumn(name="del_tip_id")
	private DeleDDelegaTipo deleDDelegaTipo;

	//bi-directional many-to-one association to DeleDDelegaStato
	@ManyToOne
	@JoinColumn(name="del_stato_id")
	private DeleDDelegaStato deleDDelegaStato;

	@Column(name="nota_motivazione")
	private String notaMotivazione;

	@Column(name="compilatore_cf")
	private String compilatoreCf;

	//bi-directional many-to-one association to DeleDDelegaTipo
	@ManyToOne
	@JoinColumn(name="enum_id")
	private DeleDEnumerazione deleDEnumerazione;

	//bi-directional many-to-one association to DeleSDelega
	@OneToMany(mappedBy="deleTDelega")
	private List<DeleSDelega> deleSDelegas;

	@Column(name="blocca")
	private Boolean blocca;

	public DeleTDelega() {
	}

	public Integer getDlgaId() {
		return this.dlgaId;
	}

	public void setDlgaId(Integer dlgaId) {
		this.dlgaId = dlgaId;
	}

	public java.util.Date getDataCancellazione() {
		return this.dataCancellazione;
	}

	public void setDataCancellazione(java.util.Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public java.util.Date getDataCreazione() {
		return this.dataCreazione;
	}

	public void setDataCreazione(java.util.Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public java.util.Date getDataModifica() {
		return this.dataModifica;
	}

	public void setDataModifica(java.util.Date dataModifica) {
		this.dataModifica = dataModifica;
	}

	public String getLoginOperazione() {
		return this.loginOperazione;
	}

	public void setLoginOperazione(String loginOperazione) {
		this.loginOperazione = loginOperazione;
	}

	public Boolean getPresavisione() {
		return this.presavisione;
	}

	public void setPresavisione(Boolean presavisione) {
		this.presavisione = presavisione;
	}

	public java.util.Date getPresavisioneData() {
		return this.presavisioneData;
	}

	public void setPresavisioneData(java.util.Date presavisioneData) {
		this.presavisioneData = presavisioneData;
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
		deleSDelegaServizio.setDeleTDelega(this);

		return deleSDelegaServizio;
	}

	public DeleSDelegaServizio removeDeleSDelegaServizio(DeleSDelegaServizio deleSDelegaServizio) {
		getDeleSDelegaServizios().remove(deleSDelegaServizio);
		deleSDelegaServizio.setDeleTDelega(null);

		return deleSDelegaServizio;
	}

	public DeleDRuoloOp getDeleDRuoloOp() {
		return this.deleDRuoloOp;
	}

	public void setDeleDRuoloOp(DeleDRuoloOp deleDRuoloOp) {
		this.deleDRuoloOp = deleDRuoloOp;
	}

	public DeleTCittadino getDeleTCittadino1() {
		return this.deleTCittadino1;
	}

	public void setDeleTCittadino1(DeleTCittadino deleTCittadino1) {
		this.deleTCittadino1 = deleTCittadino1;
	}

	public DeleTCittadino getDeleTCittadino2() {
		return this.deleTCittadino2;
	}

	public void setDeleTCittadino2(DeleTCittadino deleTCittadino2) {
		this.deleTCittadino2 = deleTCittadino2;
	}

	public DeleTDichiarazioneDet getDeleTDichiarazioneDet() {
		return this.deleTDichiarazioneDet;
	}

	public void setDeleTDichiarazioneDet(DeleTDichiarazioneDet deleTDichiarazioneDet) {
		this.deleTDichiarazioneDet = deleTDichiarazioneDet;
	}

	public List<DeleTDelegaServizio> getDeleTDelegaServizios() {
		return this.deleTDelegaServizios;
	}

	public void setDeleTDelegaServizios(List<DeleTDelegaServizio> deleTDelegaServizios) {
		this.deleTDelegaServizios = deleTDelegaServizios;
	}

	public DeleTDelegaServizio addDeleTDelegaServizio(DeleTDelegaServizio deleTDelegaServizio) {
		getDeleTDelegaServizios().add(deleTDelegaServizio);
		deleTDelegaServizio.setDeleTDelega(this);

		return deleTDelegaServizio;
	}

	public DeleTDelegaServizio removeDeleTDelegaServizio(DeleTDelegaServizio deleTDelegaServizio) {
		getDeleTDelegaServizios().remove(deleTDelegaServizio);
		deleTDelegaServizio.setDeleTDelega(null);

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
		deleTDelegato.setDeleTDelega(this);

		return deleTDelegato;
	}

	public DeleTDelegato removeDeleTDelegato(DeleTDelegato deleTDelegato) {
		getDeleTDelegatos().remove(deleTDelegato);
		deleTDelegato.setDeleTDelega(null);

		return deleTDelegato;
	}

	public DeleDDelegaTipo getDeleDDelegaTipo() {
		return deleDDelegaTipo;
	}

	public void setDeleDDelegaTipo(DeleDDelegaTipo deleDDelegaTipo) {
		this.deleDDelegaTipo = deleDDelegaTipo;
	}

	public DeleDDelegaStato getDeleDDelegaStato() {
		return deleDDelegaStato;
	}

	public void setDeleDDelegaStato(DeleDDelegaStato deleDDelegaStato) {
		this.deleDDelegaStato = deleDDelegaStato;
	}

	public String getNotaMotivazione() {
		return notaMotivazione;
	}

	public void setNotaMotivazione(String notaMotivazione) {
		this.notaMotivazione = notaMotivazione;
	}

	public String getCompilatoreCf() {
		return compilatoreCf;
	}

	public void setCompilatoreCf(String compilatoreCf) {
		this.compilatoreCf = compilatoreCf;
	}

	public DeleDEnumerazione getDeleDEnumerazione() {
		return deleDEnumerazione;
	}

	public void setDeleDEnumerazione(DeleDEnumerazione deleDEnumerazione) {
		this.deleDEnumerazione = deleDEnumerazione;
	}

	public List<DeleSDelega> getDeleSDelegas() {
		return deleSDelegas;
	}

	public void setDeleSDelegas(List<DeleSDelega> deleSDelegas) {
		this.deleSDelegas = deleSDelegas;
	}

	public Boolean getBlocca() {
		return blocca;
	}

	public void setBlocca(Boolean blocca) {
		this.blocca = blocca;
	}
}