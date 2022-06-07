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
 * The persistent class for the dele_d_delega_stato database table.
 */
@Entity
@Table(name = "dele_d_delega_stato")
@NamedQuery(name = "DeleDDelegaStato.findAll", query = "SELECT d FROM DeleDDelegaStato d")
public class DeleDDelegaStato implements Serializable {

   @Id
   @SequenceGenerator(name="DELE_D_DELEGA_STATO_DELSTATOID_GENERATOR", sequenceName="DELE_D_DELEGA_STATO_DEL_STATO_ID__SEQ", allocationSize=1)
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DELE_D_DELEGA_STATO_DELSTATOID_GENERATOR")
   @Column(name="del_stato_id", unique=true, nullable=false)
   private Integer delStatoId;

   @Column(name="del_stato_cod", nullable=false)
   private String delStatoCod;

   @Column(name="del_stato_desc", nullable=false)
   private String delStatoDesc;

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

   //bi-directional many-to-one association to DeleSDelega
   @OneToMany(mappedBy="deleDDelegaStato")
   private List<DeleSDelega> deleSDelegas;

   //bi-directional many-to-one association to DeleSDelega
   @OneToMany(mappedBy="deleDDelegaStato")
   private List<DeleTDelega> deleTDelegas;

   public DeleDDelegaStato() {
   }

   public Integer getDelStatoId() {
      return delStatoId;
   }

   public void setDelStatoId(Integer delStatoId) {
      this.delStatoId = delStatoId;
   }

   public String getDelStatoCod() {
      return delStatoCod;
   }

   public void setDelStatoCod(String delStatoCod) {
      this.delStatoCod = delStatoCod;
   }

   public String getDelStatoDesc() {
      return delStatoDesc;
   }

   public void setDelStatoDesc(String delStatoDesc) {
      this.delStatoDesc = delStatoDesc;
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

   public List<DeleSDelega> getDeleSDelegas() {
      return deleSDelegas;
   }

   public void setDeleSDelegas(List<DeleSDelega> deleSDelegas) {
      this.deleSDelegas = deleSDelegas;
   }

   public List<DeleTDelega> getDeleTDelegas() {
      return deleTDelegas;
   }

   public void setDeleTDelegas(List<DeleTDelega> deleTDelegas) {
      this.deleTDelegas = deleTDelegas;
   }

}
