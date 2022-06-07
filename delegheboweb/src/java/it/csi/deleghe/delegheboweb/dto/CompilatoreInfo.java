/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.dto;

public class CompilatoreInfo {

   private String compilatore;
   private String asl;


   public void setCompilatore(String compilatore) {
      this.compilatore = compilatore;
   }

   public void setAsl(String asl) {
      this.asl = asl;
   }

   public String getCompilatore() {
      return compilatore;
   }

   public String getAsl() {
      return asl;
   }
}
