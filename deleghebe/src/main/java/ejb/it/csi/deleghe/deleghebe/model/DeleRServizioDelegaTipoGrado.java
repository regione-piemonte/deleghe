/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the dele_r_servizio_delega_tipo_grado database table.
 */
@Entity
@Table(name = "dele_r_servizio_delega_tipo_grado")
@NamedQuery(name = "DeleRServizioDelegaTipoGrado.findAll", query = "SELECT d FROM DeleRServizioDelegaTipoGrado d")
public class DeleRServizioDelegaTipoGrado implements Serializable {

   @Id
   @SequenceGenerator(name="DELE_R_SERVIZIO_DELEGA_TIPO_GRADO_SOLGRADOID_GENERATOR", sequenceName="DELE_R_SERVIZIO_DELEGA_TIPO_GRADO_SOLGRADO_ID_SEQ", allocationSize=1)
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DELE_R_SERVIZIO_DELEGA_TIPO_GRADO_SOLGRADOID_GENERATOR")
   @Column(name="solgrado_id", unique=true, nullable=false)
   private Integer solGradoId;

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

   //bi-directional many-to-one association to DeleDRuoloOp
   @ManyToOne
   @JoinColumn(name="ruoloop_id")
   private DeleDRuoloOp deleDRuoloOp;

   //bi-directional many-to-one association to DeleDGrado
   @ManyToOne
   @JoinColumn(name="del_grado_id")
   private DeleDGrado deleDGrado;

   //bi-directional many-to-one association to DeleDDelegaTipo
   @ManyToOne
   @JoinColumn(name="del_tip_id")
   private DeleDDelegaTipo deleDDelegaTipo;

   //bi-directional many-to-one association to DeleDServizio
   @ManyToOne
   @JoinColumn(name="ser_id")
   private DeleDServizio deleDServizio;

   public DeleRServizioDelegaTipoGrado() {
   }

   public Integer getSolGradoId() {
      return solGradoId;
   }

   public void setSolGradoId(Integer solGradoId) {
      this.solGradoId = solGradoId;
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

   public DeleDRuoloOp getDeleDRuoloOp() {
      return deleDRuoloOp;
   }

   public void setDeleDRuoloOp(DeleDRuoloOp deleDRuoloOp) {
      this.deleDRuoloOp = deleDRuoloOp;
   }

   public DeleDGrado getDeleDGrado() {
      return deleDGrado;
   }

   public void setDeleDGrado(DeleDGrado deleDGrado) {
      this.deleDGrado = deleDGrado;
   }

   public DeleDDelegaTipo getDeleDDelegaTipo() {
      return deleDDelegaTipo;
   }

   public void setDeleDDelegaTipo(DeleDDelegaTipo deleDDelegaTipo) {
      this.deleDDelegaTipo = deleDDelegaTipo;
   }

   public DeleDServizio getDeleDServizio() {
      return deleDServizio;
   }

   public void setDeleDServizio(DeleDServizio deleDServizio) {
      this.deleDServizio = deleDServizio;
   }
}

