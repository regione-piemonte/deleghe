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
 * The persistent class for the dele_d_grado database table.
 */
@Entity
@Table(name = "dele_d_grado")
@NamedQuery(name = "DeleDGrado.findAll", query = "SELECT d FROM DeleDGrado d")
public class DeleDGrado implements Serializable {

   @Id
   @SequenceGenerator(name="DELE_D_GRADO_DELGRADOID_GENERATOR", sequenceName="DELE_D_GRADO_DEL_GRADO_ID_SEQ", allocationSize=1)
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DELE_D_GRADO_DELGRADOID_GENERATOR")
   @Column(name="del_grado_id", unique=true, nullable=false)
   private Integer delGradoId;

   @Column(name="del_grado_cod", nullable=false)
   private String delGradoCod;

   @Column(name="del_grado_desc", nullable=false)
   private String delGradoDesc;

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
   @OneToMany(mappedBy="deleDGrado")
   private List<DeleTDelegaServizio> deleTDelegaServizios;

   //bi-directional many-to-one association to DeleRServizioDelegaTipoGrado
   @OneToMany(mappedBy="deleDGrado")
   private List<DeleRServizioDelegaTipoGrado> deleRServizioDelegaTipoGrados;

   //bi-directional many-to-one association to DeleSDelegaServizio
   @OneToMany(mappedBy="deleDGrado")
   private List<DeleSDelegaServizio> deleSDelegaServizios;

   public DeleDGrado() {
   }

   public Integer getDelGradoId() {
      return delGradoId;
   }

   public void setDelGradoId(Integer delGradoId) {
      this.delGradoId = delGradoId;
   }

   public String getDelGradoCod() {
      return delGradoCod;
   }

   public void setDelGradoCod(String delGradoCod) {
      this.delGradoCod = delGradoCod;
   }

   public String getDelGradoDesc() {
      return delGradoDesc;
   }

   public void setDelGradoDesc(String delGradoDesc) {
      this.delGradoDesc = delGradoDesc;
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

   public List<DeleTDelegaServizio> getDeleTDelegaServizios() {
      return deleTDelegaServizios;
   }

   public void setDeleTDelegaServizios(List<DeleTDelegaServizio> deleTDelegaServizios) {
      this.deleTDelegaServizios = deleTDelegaServizios;
   }

   public List<DeleRServizioDelegaTipoGrado> getDeleRServizioDelegaTipoGrados() {
      return deleRServizioDelegaTipoGrados;
   }

   public void setDeleRServizioDelegaTipoGrados(List<DeleRServizioDelegaTipoGrado> deleRServizioDelegaTipoGrados) {
      this.deleRServizioDelegaTipoGrados = deleRServizioDelegaTipoGrados;
   }

   public List<DeleSDelegaServizio> getDeleSDelegaServizios() {
      return deleSDelegaServizios;
   }

   public void setDeleSDelegaServizios(List<DeleSDelegaServizio> deleSDelegaServizios) {
      this.deleSDelegaServizios = deleSDelegaServizios;
   }
}
