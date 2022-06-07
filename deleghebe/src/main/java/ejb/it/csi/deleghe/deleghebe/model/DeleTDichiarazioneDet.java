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
 * The persistent class for the dele_t_dichiarazione_det database table.
 * 
 */
@Entity
@Table(name="dele_t_dichiarazione_det")
@NamedQuery(name="DeleTDichiarazioneDet.findAll", query="SELECT d FROM DeleTDichiarazioneDet d")
public class DeleTDichiarazioneDet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DELE_T_DICHIARAZIONE_DET_DICDETID_GENERATOR", sequenceName="DELE_T_DICHIARAZIONE_DET_DICDET_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DELE_T_DICHIARAZIONE_DET_DICDETID_GENERATOR")
	@Column(name="dicdet_id")
	private Integer dicdetId;

	@Column(name="data_cancellazione")
	private Date dataCancellazione;

	@Column(name="data_creazione")
	private Date dataCreazione;

	@Column(name="data_modifica")
	private Date dataModifica;

	@Column(name="login_operazione")
	private String loginOperazione;

	@Column(name="note_revoca_blocco")
	private String noteRevocaBlocco;

	private Boolean presavisione;

	@Column(name="presavisione_data")
	private Date presavisioneData;
	
	//bi-directional many-to-one association to DeleSDichiarazioneDet
	@OneToMany(mappedBy="deleTDichiarazioneDet")
	private List<DeleSDichiarazioneDet> deleSDichiarazioneDets;

	//bi-directional many-to-one association to DeleTDelega
	@OneToMany(mappedBy="deleTDichiarazioneDet")
	private List<DeleTDelega> deleTDelegas;

	//bi-directional many-to-one association to DeleDDichiarazioneDetStato
	@ManyToOne
	@JoinColumn(name="dicdet_stato_id")
	private DeleDDichiarazioneDetStato deleDDichiarazioneDetStato;

	//bi-directional many-to-one association to DeleDDichiarazioneRuolo
	@ManyToOne
	@JoinColumn(name="dic_ruolo_id_1")
	private DeleDDichiarazioneRuolo deleDDichiarazioneRuolo1;

	//bi-directional many-to-one association to DeleDDichiarazioneRuolo
	@ManyToOne
	@JoinColumn(name="dic_ruolo_id_2")
	private DeleDDichiarazioneRuolo deleDDichiarazioneRuolo2;

	//bi-directional many-to-one association to DeleDRuoloOp
	@ManyToOne
	@JoinColumn(name="ruoloop_id")
	private DeleDRuoloOp deleDRuoloOp;

	//bi-directional many-to-one association to DeleTCittadino
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="cit_id_1")
	private DeleTCittadino deleTCittadino1;

	//bi-directional many-to-one association to DeleTCittadino
	@ManyToOne(cascade =CascadeType.ALL)
	@JoinColumn(name="cit_id_2")
	private DeleTCittadino deleTCittadino2;

	//bi-directional many-to-one association to DeleTDichiarazione
	@ManyToOne
	@JoinColumn(name="dic_id")
	private DeleTDichiarazione deleTDichiarazione;

	//bi-directional many-to-one association to DeleTDocumento
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="doc_id")
	private DeleTDocumento deleTDocumento;
	
	@Column(name = "uuid")
	@org.hibernate.annotations.Type(type="pg-uuid")
	private UUID uuid;

	//bi-directional many-to-one association to DeleTDelega
	@OneToMany(mappedBy="deleTDichiarazioneDet")
	private List<DeleSDelega> deleSDelegas;

	@Column(name="nota_motivazione")
	private String notaMotivazione;

	//bi-directional many-to-one association to DeleDDelegaTipo
	@ManyToOne
	@JoinColumn(name="enum_id")
	private DeleDEnumerazione deleDEnumerazione;

	public DeleTDichiarazioneDet() {
	}

	public Integer getDicdetId() {
		return this.dicdetId;
	}

	public void setDicdetId(Integer dicdetId) {
		this.dicdetId = dicdetId;
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

	public String getNoteRevocaBlocco() {
		return this.noteRevocaBlocco;
	}

	public void setNoteRevocaBlocco(String noteRevocaBlocco) {
		this.noteRevocaBlocco = noteRevocaBlocco;
	}

	public Boolean getPresavisione() {
		return this.presavisione;
	}

	public void setPresavisione(Boolean presavisione) {
		this.presavisione = presavisione;
	}

	public Date getPresavisioneData() {
		return this.presavisioneData;
	}

	public void setPresavisioneData(Date presavisioneData) {
		this.presavisioneData = presavisioneData;
	}
	public List<DeleSDichiarazioneDet> getDeleSDichiarazioneDets() {
		return this.deleSDichiarazioneDets;
	}

	public void setDeleSDichiarazioneDets(List<DeleSDichiarazioneDet> deleSDichiarazioneDets) {
		this.deleSDichiarazioneDets = deleSDichiarazioneDets;
	}

	public DeleSDichiarazioneDet addDeleSDichiarazioneDet(DeleSDichiarazioneDet deleSDichiarazioneDet) {
		getDeleSDichiarazioneDets().add(deleSDichiarazioneDet);
		deleSDichiarazioneDet.setDeleTDichiarazioneDet(this);

		return deleSDichiarazioneDet;
	}

	public DeleSDichiarazioneDet removeDeleSDichiarazioneDet(DeleSDichiarazioneDet deleSDichiarazioneDet) {
		getDeleSDichiarazioneDets().remove(deleSDichiarazioneDet);
		deleSDichiarazioneDet.setDeleTDichiarazioneDet(null);

		return deleSDichiarazioneDet;
	}

	public List<DeleTDelega> getDeleTDelegas() {
		return this.deleTDelegas;
	}

	public void setDeleTDelegas(List<DeleTDelega> deleTDelegas) {
		this.deleTDelegas = deleTDelegas;
	}

	public DeleTDelega addDeleTDelega(DeleTDelega deleTDelega) {
		getDeleTDelegas().add(deleTDelega);
		deleTDelega.setDeleTDichiarazioneDet(this);

		return deleTDelega;
	}

	public DeleTDelega removeDeleTDelega(DeleTDelega deleTDelega) {
		getDeleTDelegas().remove(deleTDelega);
		deleTDelega.setDeleTDichiarazioneDet(null);

		return deleTDelega;
	}

	public DeleDDichiarazioneDetStato getDeleDDichiarazioneDetStato() {
		return this.deleDDichiarazioneDetStato;
	}

	public void setDeleDDichiarazioneDetStato(DeleDDichiarazioneDetStato deleDDichiarazioneDetStato) {
		this.deleDDichiarazioneDetStato = deleDDichiarazioneDetStato;
	}

	public DeleDDichiarazioneRuolo getDeleDDichiarazioneRuolo1() {
		return this.deleDDichiarazioneRuolo1;
	}

	public void setDeleDDichiarazioneRuolo1(DeleDDichiarazioneRuolo deleDDichiarazioneRuolo1) {
		this.deleDDichiarazioneRuolo1 = deleDDichiarazioneRuolo1;
	}

	public DeleDDichiarazioneRuolo getDeleDDichiarazioneRuolo2() {
		return this.deleDDichiarazioneRuolo2;
	}

	public void setDeleDDichiarazioneRuolo2(DeleDDichiarazioneRuolo deleDDichiarazioneRuolo2) {
		this.deleDDichiarazioneRuolo2 = deleDDichiarazioneRuolo2;
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

	public DeleTDichiarazione getDeleTDichiarazione() {
		return this.deleTDichiarazione;
	}

	public void setDeleTDichiarazione(DeleTDichiarazione deleTDichiarazione) {
		this.deleTDichiarazione = deleTDichiarazione;
	}

	public DeleTDocumento getDeleTDocumento() {
		return this.deleTDocumento;
	}

	public void setDeleTDocumento(DeleTDocumento deleTDocumento) {
		this.deleTDocumento = deleTDocumento;
	}
	
	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public List<DeleSDelega> getDeleSDelegas() {
		return deleSDelegas;
	}

	public void setDeleSDelegas(List<DeleSDelega> deleSDelegas) {
		this.deleSDelegas = deleSDelegas;
	}

	public String getNotaMotivazione() {
		return notaMotivazione;
	}

	public void setNotaMotivazione(String notaMotivazione) {
		this.notaMotivazione = notaMotivazione;
	}

	public DeleDEnumerazione getDeleDEnumerazione() {
		return deleDEnumerazione;
	}

	public void setDeleDEnumerazione(DeleDEnumerazione deleDEnumerazione) {
		this.deleDEnumerazione = deleDEnumerazione;
	}
}