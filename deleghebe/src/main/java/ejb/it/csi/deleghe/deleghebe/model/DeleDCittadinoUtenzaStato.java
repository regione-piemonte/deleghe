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
 * The persistent class for the dele_d_cittadino_utenza_stato database table.
 *
 */
@Entity
@Table(name="dele_d_cittadino_utenza_stato")
@NamedQuery(name="DeleDCittadinoUtenzaStato.findAll", query="SELECT d FROM DeleDCittadinoUtenzaStato d")
public class DeleDCittadinoUtenzaStato implements Serializable {

   @Id
   @SequenceGenerator(name="DELE_D_CITTADINO_UTENZA_STATO_CITUSTATOID_GENERATOR", sequenceName="DELE_D_CITTADINO_UTENZA_STATO_CITUSTATO_ID_SEQ", allocationSize=1)
   @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DELE_D_CITTADINO_UTENZA_STATO_CITUSTATOID_GENERATOR")
   @Column(name="citustato_id")
   private Integer citustatoId;

   @Column(name="citustato_cod")
   private String citustatoCod;

   @Column(name="citustato_desc")
   private String citustatoDesc;

   @Column(name="data_creazione")
   private Date dataCreazione;

   @Column(name="data_modifica")
   private Date dataModifica;

   @Column(name="data_cancellazione")
   private Date dataCancellazione;

   @Column(name="login_operazione")
   private String loginOperazione;

   //bi-directional many-to-one association to DeleDRuoloOp
   @ManyToOne
   @JoinColumn(name="ruoloop_id")
   private DeleDRuoloOp deleDRuoloOp;

   //bi-directional many-to-one association to DeleTDichiarazioneDet
   @OneToMany(mappedBy="deleDCittadinoUtenzaStato")
   private List<DeleTCittadino> deleTCittadinos;

   //bi-directional many-to-one association to DeleTDichiarazioneDet
   @OneToMany(mappedBy="deleDCittadinoUtenzaStato")
   private List<DeleSCittadino> deleSCittadinos;

   public DeleDCittadinoUtenzaStato() {
   }

   public Integer getCitustatoId() {
      return citustatoId;
   }

   public void setCitustatoId(Integer citustatoId) {
      this.citustatoId = citustatoId;
   }

   public String getCitustatoCod() {
      return citustatoCod;
   }

   public void setCitustatoCod(String citustatoCod) {
      this.citustatoCod = citustatoCod;
   }

   public String getCitustatoDesc() {
      return citustatoDesc;
   }

   public void setCitustatoDesc(String citustatoDesc) {
      this.citustatoDesc = citustatoDesc;
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

   public List<DeleTCittadino> getDeleTCittadinos() {
      return deleTCittadinos;
   }

   public void setDeleTCittadinos(List<DeleTCittadino> deleTCittadinos) {
      this.deleTCittadinos = deleTCittadinos;
   }

   public List<DeleSCittadino> getDeleSCittadinos() {
      return deleSCittadinos;
   }

   public void setDeleSCittadinos(List<DeleSCittadino> deleSCittadinos) {
      this.deleSCittadinos = deleSCittadinos;
   }
}
