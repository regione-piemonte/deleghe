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
 * The persistent class for the dele_d_enumerazione database table.
 */
@Entity
@Table(name = "dele_d_enumerazione")
@NamedQuery(name = "DeleDEnumerazione.findAll", query = "SELECT d FROM DeleDEnumerazione d")
public class DeleDEnumerazione implements Serializable {

   @Id
   @SequenceGenerator(name="DELE_D_ENUMERAZIONE_ENUMID_GENERATOR", sequenceName="DELE_D_ENUMERAZIONE_ENUM_ID_SEQ", allocationSize=1)
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DELE_D_ENUMERAZIONE_ENUMID_GENERATOR")
   @Column(name="enum_id")
   private Integer enumId;

   @Column(name="enum_tipo")
   private String enumTipo;

   @Column(name="enum_cod")
   private String enumCod;

   @Column(name="enum_desc")
   private String enumDesc;

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
   @OneToMany(mappedBy="deleDEnumerazione")
   private List<DeleSDelega> deleSDelega;

   //bi-directional many-to-one association to DeleTDelega
   @OneToMany(mappedBy="deleDEnumerazione")
   private List<DeleTDelega> deleTDelega;

   //bi-directional many-to-one association to DeleTDichiarazioneDet
   @OneToMany(mappedBy="deleDEnumerazione")
   private List<DeleTDichiarazioneDet> deleTDichiarazioneDets;

   //bi-directional many-to-one association to DeleSDichiarazioneDet
   @OneToMany(mappedBy="deleDEnumerazione")
   private List<DeleSDichiarazioneDet> deleSDichiarazioneDets;

   public DeleDEnumerazione() {
   }

   public Integer getEnumId() {
      return enumId;
   }

   public void setEnumId(Integer enumId) {
      this.enumId = enumId;
   }

   public String getEnumTipo() {
      return enumTipo;
   }

   public void setEnumTipo(String enumTipo) {
      this.enumTipo = enumTipo;
   }

   public String getEnumCod() {
      return enumCod;
   }

   public void setEnumCod(String enumCod) {
      this.enumCod = enumCod;
   }

   public String getEnumDesc() {
      return enumDesc;
   }

   public void setEnumDesc(String enumDesc) {
      this.enumDesc = enumDesc;
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

   public List<DeleSDelega> getDeleSDelega() {
      return deleSDelega;
   }

   public void setDeleSDelega(List<DeleSDelega> deleSDelega) {
      this.deleSDelega = deleSDelega;
   }

   public List<DeleTDelega> getDeleTDelega() {
      return deleTDelega;
   }

   public void setDeleTDelega(List<DeleTDelega> deleTDelega) {
      this.deleTDelega = deleTDelega;
   }

   public List<DeleTDichiarazioneDet> getDeleTDichiarazioneDets() {
      return deleTDichiarazioneDets;
   }

   public void setDeleTDichiarazioneDets(List<DeleTDichiarazioneDet> deleTDichiarazioneDets) {
      this.deleTDichiarazioneDets = deleTDichiarazioneDets;
   }

   public List<DeleSDichiarazioneDet> getDeleSDichiarazioneDets() {
      return deleSDichiarazioneDets;
   }

   public void setDeleSDichiarazioneDets(List<DeleSDichiarazioneDet> deleSDichiarazioneDets) {
      this.deleSDichiarazioneDets = deleSDichiarazioneDets;
   }
}
