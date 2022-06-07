/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the dele_d_delega_tipo database table.
 */
@Entity
@Table(name = "dele_d_delega_tipo")
@NamedQuery(name = "DeleDDelegaTipo.findAll", query = "SELECT d FROM DeleDDelegaTipo d")
public class DeleDDelegaTipo implements Serializable {

   @Id
   @SequenceGenerator(name="DELE_D_DELEGA_TIPO_DELTIPID_GENERATOR", sequenceName="DELE_D_DELEGA_TIPO_DEL_TIP_ID__SEQ", allocationSize=1)
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DELE_D_DELEGA_TIPO_DELTIPID_GENERATOR")
   @Column(name="del_tip_id", unique=true, nullable=false)
   private Integer delTipId;

   @Column(name="del_tip_cod", nullable=false)
   private String delTipCod;

   @Column(name="del_tip_desc", nullable=false)
   private String delTipDesc;

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

   //bi-directional many-to-one association to DeleTDelegaServizio
   @OneToMany(mappedBy="deleDDelegaTipo")
   private List<DeleTDelega> deleTDelega;

   //bi-directional many-to-one association to DeleTDelegaServizio
   @OneToMany(mappedBy="deleDDelegaTipo")
   private List<DeleSDelega> deleSDelega;

   public DeleDDelegaTipo() {
   }

   public Integer getDelTipId() {
      return delTipId;
   }

   public void setDelTipId(Integer delTipId) {
      this.delTipId = delTipId;
   }

   public String getDelTipCod() {
      return delTipCod;
   }

   public void setDelTipCod(String delTipCod) {
      this.delTipCod = delTipCod;
   }

   public String getDelTipDesc() {
      return delTipDesc;
   }

   public void setDelTipDesc(String delTipDesc) {
      this.delTipDesc = delTipDesc;
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

   public List<DeleTDelega> getDeleTDelega() {
      return deleTDelega;
   }

   public void setDeleTDelega(List<DeleTDelega> deleTDelega) {
      this.deleTDelega = deleTDelega;
   }

   public List<DeleSDelega> getDeleSDelega() {
      return deleSDelega;
   }

   public void setDeleSDelega(List<DeleSDelega> deleSDelega) {
      this.deleSDelega = deleSDelega;
   }
}
