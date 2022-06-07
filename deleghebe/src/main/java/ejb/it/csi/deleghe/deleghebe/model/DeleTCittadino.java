/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.model;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

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
 * The persistent class for the dele_t_cittadino database table.
 * 
 */
@Entity
@Table(name="dele_t_cittadino")
@NamedQuery(name="DeleTCittadino.findAll", query="SELECT d FROM DeleTCittadino d")
public class DeleTCittadino implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DELE_T_CITTADINO_CITID_GENERATOR", sequenceName="DELE_T_CITTADINO_CIT_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DELE_T_CITTADINO_CITID_GENERATOR")
	@Column(name="cit_id")
	private Integer citId;

	@Column(name="cit_asl")
	private String citAsl;

	@Column(name="cit_auraid")
	private String citAuraid;

	@Column(name="cit_cf")
	private String citCf;

	@Column(name="cit_cognome")
	private String citCognome;

	@Column(name="cit_nascita_comune")
	private String citNascitaComune;

	@Column(name="cit_nascita_data")
	private Date citNascitaData;

	@Column(name="cit_nome")
	private String citNome;

	@Column(name="cit_sesso")
	private String citSesso;

	@Column(name="data_cancellazione")
	private Date dataCancellazione;

	@Column(name="data_creazione")
	private Date dataCreazione;

	@Column(name="data_modifica")
	private Date dataModifica;

	@Column(name="login_operazione")
	private String loginOperazione;

	@Column(name="verificato_asl")
	private Boolean verificatoAsl;

	//bi-directional many-to-one association to DeleSCittadino
	@OneToMany(mappedBy="deleTCittadino")
	private List<DeleSCittadino> deleSCittadinos;

	//bi-directional many-to-one association to DeleSDichiarazione
	@OneToMany(mappedBy="deleTCittadino")
	private List<DeleSDichiarazione> deleSDichiaraziones;

	//bi-directional many-to-one association to CsiLogAudit
	@ManyToOne
	@JoinColumn(name="audit_id")
	private CsiLogAudit csiLogAudit;

	//bi-directional many-to-one association to DeleDCittadinoDatiStato
	@ManyToOne
	@JoinColumn(name="citdstato_id")
	private DeleDCittadinoDatiStato deleDCittadinoDatiStato;

	//bi-directional many-to-one association to DeleDRuoloOp
	@ManyToOne
	@JoinColumn(name="ruoloop_id")
	private DeleDRuoloOp deleDRuoloOp;

	//bi-directional many-to-one association to DeleTAsl
	@ManyToOne
	@JoinColumn(name="cit_asl_id")
	private DeleTAsl deleTAsl;

	//bi-directional many-to-one association to DeleTDocumento
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="doc_id")
	private DeleTDocumento deleTDocumento;

 	//bi-directional many-to-one association to DeleDCittadinoUtenzaStato
	@ManyToOne
	@JoinColumn(name="citustato_id")
	private DeleDCittadinoUtenzaStato deleDCittadinoUtenzaStato;

	//bi-directional many-to-one association to DeleTDelega
	@OneToMany(mappedBy="deleTCittadino1")
	private List<DeleTDelega> deleTDelegas1;

	//bi-directional many-to-one association to DeleTDelega
	@OneToMany(mappedBy="deleTCittadino2")
	private List<DeleTDelega> deleTDelegas2;

	//bi-directional many-to-one association to DeleTDichiarazione
	@OneToMany(mappedBy="deleTCittadino")
	private List<DeleTDichiarazione> deleTDichiaraziones;

	//bi-directional many-to-one association to DeleTDichiarazioneDet
	@OneToMany(mappedBy="deleTCittadino1")
	private List<DeleTDichiarazioneDet> deleTDichiarazioneDets1;

	//bi-directional many-to-one association to DeleTDichiarazioneDet
	@OneToMany(mappedBy="deleTCittadino2")
	private List<DeleTDichiarazioneDet> deleTDichiarazioneDets2;

	//bi-directional many-to-one association to DeleSCittadino
	@OneToMany(mappedBy="deleTCittadino1")
	private List<DeleSDelega> deleSDelegas1;

	//bi-directional many-to-one association to DeleSCittadino
	@OneToMany(mappedBy="deleTCittadino2")
	private List<DeleSDelega> deleSDelegas2;


	public DeleTCittadino() {
	}

	public Integer getCitId() {
		return this.citId;
	}

	public void setCitId(Integer citId) {
		this.citId = citId;
	}

	public String getCitAsl() {
		return this.citAsl;
	}

	public void setCitAsl(String citAsl) {
		this.citAsl = citAsl;
	}

	public String getCitAuraid() {
		return this.citAuraid;
	}

	public void setCitAuraid(String citAuraid) {
		this.citAuraid = citAuraid;
	}

	public String getCitCf() {
		return this.citCf;
	}

	public void setCitCf(String citCf) {
		this.citCf = citCf;
	}

	public String getCitCognome() {
		return this.citCognome;
	}

	public void setCitCognome(String citCognome) {
		this.citCognome = citCognome;
	}

	public String getCitNascitaComune() {
		return this.citNascitaComune;
	}

	public void setCitNascitaComune(String citNascitaComune) {
		this.citNascitaComune = citNascitaComune;
	}

	public Date getCitNascitaData() {
		return this.citNascitaData;
	}

	public void setCitNascitaData(Date citNascitaData) {
		this.citNascitaData = citNascitaData;
	}

	public String getCitNome() {
		return this.citNome;
	}

	public void setCitNome(String citNome) {
		this.citNome = citNome;
	}

	public String getCitSesso() {
		return this.citSesso;
	}

	public void setCitSesso(String citSesso) {
		this.citSesso = citSesso;
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

	public List<DeleSCittadino> getDeleSCittadinos() {
		return this.deleSCittadinos;
	}

	public void setDeleSCittadinos(List<DeleSCittadino> deleSCittadinos) {
		this.deleSCittadinos = deleSCittadinos;
	}

	public DeleSCittadino addDeleSCittadino(DeleSCittadino deleSCittadino) {
		getDeleSCittadinos().add(deleSCittadino);
		deleSCittadino.setDeleTCittadino(this);

		return deleSCittadino;
	}

	public DeleSCittadino removeDeleSCittadino(DeleSCittadino deleSCittadino) {
		getDeleSCittadinos().remove(deleSCittadino);
		deleSCittadino.setDeleTCittadino(null);

		return deleSCittadino;
	}

	public List<DeleSDichiarazione> getDeleSDichiaraziones() {
		return this.deleSDichiaraziones;
	}

	public void setDeleSDichiaraziones(List<DeleSDichiarazione> deleSDichiaraziones) {
		this.deleSDichiaraziones = deleSDichiaraziones;
	}

	public DeleSDichiarazione addDeleSDichiarazione(DeleSDichiarazione deleSDichiarazione) {
		getDeleSDichiaraziones().add(deleSDichiarazione);
		deleSDichiarazione.setDeleTCittadino(this);

		return deleSDichiarazione;
	}

	public DeleSDichiarazione removeDeleSDichiarazione(DeleSDichiarazione deleSDichiarazione) {
		getDeleSDichiaraziones().remove(deleSDichiarazione);
		deleSDichiarazione.setDeleTCittadino(null);

		return deleSDichiarazione;
	}

	public CsiLogAudit getCsiLogAudit() {
		return this.csiLogAudit;
	}

	public void setCsiLogAudit(CsiLogAudit csiLogAudit) {
		this.csiLogAudit = csiLogAudit;
	}

	public DeleDCittadinoDatiStato getDeleDCittadinoDatiStato() {
		return this.deleDCittadinoDatiStato;
	}

	public void setDeleDCittadinoDatiStato(DeleDCittadinoDatiStato deleDCittadinoDatiStato) {
		this.deleDCittadinoDatiStato = deleDCittadinoDatiStato;
	}

	public DeleDRuoloOp getDeleDRuoloOp() {
		return this.deleDRuoloOp;
	}

	public void setDeleDRuoloOp(DeleDRuoloOp deleDRuoloOp) {
		this.deleDRuoloOp = deleDRuoloOp;
	}

	public List<DeleTDelega> getDeleTDelegas1() {
		return this.deleTDelegas1;
	}

	public void setDeleTDelegas1(List<DeleTDelega> deleTDelegas1) {
		this.deleTDelegas1 = deleTDelegas1;
	}

	public DeleTDelega addDeleTDelegas1(DeleTDelega deleTDelegas1) {
		getDeleTDelegas1().add(deleTDelegas1);
		deleTDelegas1.setDeleTCittadino1(this);

		return deleTDelegas1;
	}

	public DeleTDelega removeDeleTDelegas1(DeleTDelega deleTDelegas1) {
		getDeleTDelegas1().remove(deleTDelegas1);
		deleTDelegas1.setDeleTCittadino1(null);

		return deleTDelegas1;
	}

	public List<DeleTDelega> getDeleTDelegas2() {
		return this.deleTDelegas2;
	}

	public void setDeleTDelegas2(List<DeleTDelega> deleTDelegas2) {
		this.deleTDelegas2 = deleTDelegas2;
	}

	public DeleTDelega addDeleTDelegas2(DeleTDelega deleTDelegas2) {
		getDeleTDelegas2().add(deleTDelegas2);
		deleTDelegas2.setDeleTCittadino2(this);

		return deleTDelegas2;
	}

	public DeleTDelega removeDeleTDelegas2(DeleTDelega deleTDelegas2) {
		getDeleTDelegas2().remove(deleTDelegas2);
		deleTDelegas2.setDeleTCittadino2(null);

		return deleTDelegas2;
	}

	public List<DeleTDichiarazione> getDeleTDichiaraziones() {
		return this.deleTDichiaraziones;
	}

	public void setDeleTDichiaraziones(List<DeleTDichiarazione> deleTDichiaraziones) {
		this.deleTDichiaraziones = deleTDichiaraziones;
	}

	public DeleTDichiarazione addDeleTDichiarazione(DeleTDichiarazione deleTDichiarazione) {
		getDeleTDichiaraziones().add(deleTDichiarazione);
		deleTDichiarazione.setDeleTCittadino(this);

		return deleTDichiarazione;
	}

	public DeleTDichiarazione removeDeleTDichiarazione(DeleTDichiarazione deleTDichiarazione) {
		getDeleTDichiaraziones().remove(deleTDichiarazione);
		deleTDichiarazione.setDeleTCittadino(null);

		return deleTDichiarazione;
	}

	public List<DeleTDichiarazioneDet> getDeleTDichiarazioneDets1() {
		return this.deleTDichiarazioneDets1;
	}

	public void setDeleTDichiarazioneDets1(List<DeleTDichiarazioneDet> deleTDichiarazioneDets1) {
		this.deleTDichiarazioneDets1 = deleTDichiarazioneDets1;
	}

	public DeleTDichiarazioneDet addDeleTDichiarazioneDets1(DeleTDichiarazioneDet deleTDichiarazioneDets1) {
		getDeleTDichiarazioneDets1().add(deleTDichiarazioneDets1);
		deleTDichiarazioneDets1.setDeleTCittadino1(this);

		return deleTDichiarazioneDets1;
	}

	public DeleTDichiarazioneDet removeDeleTDichiarazioneDets1(DeleTDichiarazioneDet deleTDichiarazioneDets1) {
		getDeleTDichiarazioneDets1().remove(deleTDichiarazioneDets1);
		deleTDichiarazioneDets1.setDeleTCittadino1(null);

		return deleTDichiarazioneDets1;
	}

	public List<DeleTDichiarazioneDet> getDeleTDichiarazioneDets2() {
		return this.deleTDichiarazioneDets2;
	}

	public void setDeleTDichiarazioneDets2(List<DeleTDichiarazioneDet> deleTDichiarazioneDets2) {
		this.deleTDichiarazioneDets2 = deleTDichiarazioneDets2;
	}

	public DeleTDichiarazioneDet addDeleTDichiarazioneDets2(DeleTDichiarazioneDet deleTDichiarazioneDets2) {
		getDeleTDichiarazioneDets2().add(deleTDichiarazioneDets2);
		deleTDichiarazioneDets2.setDeleTCittadino2(this);

		return deleTDichiarazioneDets2;
	}

	public DeleTDichiarazioneDet removeDeleTDichiarazioneDets2(DeleTDichiarazioneDet deleTDichiarazioneDets2) {
		getDeleTDichiarazioneDets2().remove(deleTDichiarazioneDets2);
		deleTDichiarazioneDets2.setDeleTCittadino2(null);

		return deleTDichiarazioneDets2;
	}

	public DeleTAsl getDeleTAsl() {
		return deleTAsl;
	}

	public void setDeleTAsl(DeleTAsl deleTAsl) {
		this.deleTAsl = deleTAsl;
	}

	public Boolean getVerificatoAsl() {
		return verificatoAsl;
	}

	public void setVerificatoAsl(Boolean verificatoAsl) {
		this.verificatoAsl = verificatoAsl;
	}

	public DeleTDocumento getDeleTDocumento() {
		return deleTDocumento;
	}

	public void setDeleTDocumento(DeleTDocumento deleTDocumento) {
		this.deleTDocumento = deleTDocumento;
	}

	public DeleDCittadinoUtenzaStato getDeleDCittadinoUtenzaStato() {
		return deleDCittadinoUtenzaStato;
	}

	public void setDeleDCittadinoUtenzaStato(DeleDCittadinoUtenzaStato deleDCittadinoUtenzaStato) {
		this.deleDCittadinoUtenzaStato = deleDCittadinoUtenzaStato;
	}

	public List<DeleSDelega> getDeleSDelegas1() {
		return deleSDelegas1;
	}

	public void setDeleSDelegas1(List<DeleSDelega> deleSDelegas1) {
		this.deleSDelegas1 = deleSDelegas1;
	}

	public List<DeleSDelega> getDeleSDelegas2() {
		return deleSDelegas2;
	}

	public void setDeleSDelegas2(List<DeleSDelega> deleSDelegas2) {
		this.deleSDelegas2 = deleSDelegas2;
	}
	
	//PARTE MANCANTE
	public DeleSDelega addDeleSDelegas2(DeleSDelega deleSDelegas2) {
		getDeleSDelegas2().add(deleSDelegas2);
		deleSDelegas2.setDeleTCittadino2(this);

		return deleSDelegas2;
	}

	public DeleSDelega removeDeleSDelegas2(DeleSDelega deleSDelegas2) {
		getDeleSDelegas2().remove(deleSDelegas2);
		deleSDelegas2.setDeleTCittadino2(null);

		return deleSDelegas2;
	}

        public DeleSDelega addDeleSDelegas1(DeleSDelega deleSDelegas1) {
		getDeleSDelegas1().add(deleSDelegas1);
		deleSDelegas1.setDeleTCittadino1(this);

		return deleSDelegas1;
	}

	public DeleSDelega removeDeleSDelegas1(DeleSDelega deleSDelegas1) {
		getDeleSDelegas1().remove(deleSDelegas1);
		deleSDelegas1.setDeleTCittadino1(null);

		return deleSDelegas1;
	}

}