/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the dele_s_cittadino database table.
 *
 */
@Entity
@Table(name="dele_s_delega")
@NamedQuery(name="DeleSDelega.findAll", query="SELECT d FROM DeleSDelega d")
public class DeleSDelega implements Serializable {

   @Id
   @SequenceGenerator(name="DELE_S_DELEGA_DLGASID_GENERATOR", sequenceName="DELE_S_DELEGA_DLGAS_ID_SEQ", allocationSize=1)
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DELE_S_DELEGA_DLGASID_GENERATOR")
   @Column(name="dlgas_id")
   private String dlgasId;

   @Column(name="validita_inizio")
   private java.util.Date validitaInizio;

   @Column(name="validita_fine")
   private java.util.Date validitaFine;

   @Column(name="data_creazione")
   private java.util.Date dataCreazione;

   @Column(name="data_modifica")
   private java.util.Date dataModifica;

   @Column(name="data_cancellazione")
   private java.util.Date dataCancellazione;

   @Column(name="login_operazione")
   private String loginOperazione;

   @Column(name="presavisione")
   private Boolean presavisione;

   @Column(name="presavisione_data")
   private Date presavisioneData;

   @Column(name="compilatore_cf")
   private String compilatoreCf;

   @Column(name="nota_motivazione")
   private String notaMotivazione;

   //bi-directional many-to-one association to DeleDRuoloOp
   @ManyToOne
   @JoinColumn(name="dlga_id")
   private DeleTDelega deleTDelega;


   //bi-directional many-to-one association to DeleTCittadino
   @ManyToOne(cascade = CascadeType.ALL)
   @JoinColumn(name="cit_id_delegante")
   private DeleTCittadino deleTCittadino1;

   //bi-directional many-to-one association to DeleTCittadino
   @ManyToOne(cascade = CascadeType.ALL)
   @JoinColumn(name="cit_id_delegato")
   private DeleTCittadino deleTCittadino2;

   //bi-directional many-to-one association to DeleDRuoloOp
   @ManyToOne
   @JoinColumn(name="ruoloop_id")
   private DeleDRuoloOp deleDRuoloOp;

   //bi-directional many-to-one association to DeleTDichiarazioneDet
   @ManyToOne
   @JoinColumn(name="dicdet_id")
   private DeleTDichiarazioneDet deleTDichiarazioneDet;

   //bi-directional many-to-one association to DeleDDelegaStato
   @ManyToOne
   @JoinColumn(name="del_stato_id")
   private DeleDDelegaStato deleDDelegaStato;

   //bi-directional many-to-one association to DeleDDelegaTipo
   @ManyToOne
   @JoinColumn(name="del_tip_id")
   private DeleDDelegaTipo deleDDelegaTipo;

   //bi-directional many-to-one association to DeleDDelegaTipo
   @ManyToOne
   @JoinColumn(name="enum_id")
   private DeleDEnumerazione deleDEnumerazione;

   public DeleSDelega() {
   }

   public String getDlgasId() {
      return dlgasId;
   }

   public void setDlgasId(String dlgasId) {
      this.dlgasId = dlgasId;
   }

   public Date getValiditaInizio() {
      return validitaInizio;
   }

   public void setValiditaInizio(Date validitaInizio) {
      this.validitaInizio = validitaInizio;
   }

   public Date getValiditaFine() {
      return validitaFine;
   }

   public void setValiditaFine(Date validitaFine) {
      this.validitaFine = validitaFine;
   }

   public Date getDataCreazione() {
      return dataCreazione;
   }

   public void setDataCreazione(Date dataCreazione) {
      this.dataCreazione = dataCreazione;
   }

   public Date getDataModifica() {
      return dataModifica;
   }

   public void setDataModifica(Date dataModifica) {
      this.dataModifica = dataModifica;
   }

   public Date getDataCancellazione() {
      return dataCancellazione;
   }

   public void setDataCancellazione(Date dataCancellazione) {
      this.dataCancellazione = dataCancellazione;
   }

   public String getLoginOperazione() {
      return loginOperazione;
   }

   public void setLoginOperazione(String loginOperazione) {
      this.loginOperazione = loginOperazione;
   }

   public Boolean getPresavisione() {
      return presavisione;
   }

   public void setPresavisione(Boolean presavisione) {
      this.presavisione = presavisione;
   }

   public Date getPresavisioneData() {
      return presavisioneData;
   }

   public void setPresavisioneData(Date presavisioneData) {
      this.presavisioneData = presavisioneData;
   }

   public String getCompilatoreCf() {
      return compilatoreCf;
   }

   public void setCompilatoreCf(String compilatoreCf) {
      this.compilatoreCf = compilatoreCf;
   }

   public String getNotaMotivazione() {
      return notaMotivazione;
   }

   public void setNotaMotivazione(String notaMotivazione) {
      this.notaMotivazione = notaMotivazione;
   }

   public DeleTDelega getDeleTDelega() {
      return deleTDelega;
   }

   public void setDeleTDelega(DeleTDelega deleTDelega) {
      this.deleTDelega = deleTDelega;
   }

   public DeleTCittadino getDeleTCittadino1() {
      return deleTCittadino1;
   }

   public void setDeleTCittadino1(DeleTCittadino deleTCittadino1) {
      this.deleTCittadino1 = deleTCittadino1;
   }

   public DeleTCittadino getDeleTCittadino2() {
      return deleTCittadino2;
   }

   public void setDeleTCittadino2(DeleTCittadino deleTCittadino2) {
      this.deleTCittadino2 = deleTCittadino2;
   }

   public DeleDRuoloOp getDeleDRuoloOp() {
      return deleDRuoloOp;
   }

   public void setDeleDRuoloOp(DeleDRuoloOp deleDRuoloOp) {
      this.deleDRuoloOp = deleDRuoloOp;
   }

   public DeleTDichiarazioneDet getDeleTDichiarazioneDet() {
      return deleTDichiarazioneDet;
   }

   public void setDeleTDichiarazioneDet(DeleTDichiarazioneDet deleTDichiarazioneDet) {
      this.deleTDichiarazioneDet = deleTDichiarazioneDet;
   }

   public DeleDDelegaStato getDeleDDelegaStato() {
      return deleDDelegaStato;
   }

   public void setDeleDDelegaStato(DeleDDelegaStato deleDDelegaStato) {
      this.deleDDelegaStato = deleDDelegaStato;
   }

   public DeleDDelegaTipo getDeleDDelegaTipo() {
      return deleDDelegaTipo;
   }

   public void setDeleDDelegaTipo(DeleDDelegaTipo deleDDelegaTipo) {
      this.deleDDelegaTipo = deleDDelegaTipo;
   }

   public DeleDEnumerazione getDeleDEnumerazione() {
      return deleDEnumerazione;
   }

   public void setDeleDEnumerazione(DeleDEnumerazione deleDEnumerazione) {
      this.deleDEnumerazione = deleDEnumerazione;
   }

}
