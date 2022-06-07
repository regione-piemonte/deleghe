/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.dto.delegheboweb;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;
import java.util.Date;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CittadinoFilter {

   private String codiceFiscale = null;
   private String nome = null;
   private String cognome = null;
   private Date dataNascita = null;
   private String comuneNascita = null;
   private String sesso = null;

   @ApiModelProperty(value = "")
   @JsonProperty("cognome")
   public String getCognome() {
      return cognome;
   }

   public void setCognome(String cognome) {
      this.cognome = cognome;
   }

   @ApiModelProperty(value = "")
   @JsonProperty("comune_nascita")
   public String getComuneNascita() {
      return comuneNascita;
   }

   public void setComuneNascita(String comuneNascita) {
      this.comuneNascita = comuneNascita;
   }

   @ApiModelProperty(value = "")
   @JsonProperty("nome")
   public String getNome() {
      return nome;
   }

   public void setNome(String nome) {
      this.nome = nome;
   }

   @ApiModelProperty(value = "")
   @JsonProperty("data_nascita")
   public Date getDataNascita() {
      return dataNascita;
   }

   public void setDataNascita(Date dataNascita) {
      this.dataNascita = dataNascita;
   }

   @ApiModelProperty(value = "")
   @JsonProperty("sesso")
   public String getSesso() {
      return sesso;
   }

   public void setSesso(String sesso) {
      this.sesso = sesso;
   }

   @ApiModelProperty(value = "")
   @JsonProperty("codice_fiscale")
   public String getCodiceFiscale() {
      return codiceFiscale;
   }

   public void setCodiceFiscale(String codiceFiscale) {
      this.codiceFiscale = codiceFiscale;
   }


   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      }
      if (o == null || getClass() != o.getClass()) {
         return false;
      }
      CittadinoFilter cittadinoFilter = (CittadinoFilter) o;
      return Objects.equals(sesso, cittadinoFilter.sesso) &&
            Objects.equals(dataNascita, cittadinoFilter.dataNascita) &&
            Objects.equals(nome, cittadinoFilter.nome) &&
            Objects.equals(comuneNascita, cittadinoFilter.comuneNascita) &&
            Objects.equals(cognome, cittadinoFilter.cognome) &&
            Objects.equals(codiceFiscale, cittadinoFilter.codiceFiscale);
   }

   @Override
   public int hashCode() {
      return Objects.hash(codiceFiscale);
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("class CittadinoFilter {\n");

      sb.append("    sesso: ").append(toIndentedString(sesso)).append("\n");
      sb.append("    dataNascita: ").append(toIndentedString(dataNascita)).append("\n");
      sb.append("    nome: ").append(toIndentedString(nome)).append("\n");
      sb.append("    comuneNascita: ").append(toIndentedString(comuneNascita)).append("\n");
      sb.append("    cognome: ").append(toIndentedString(cognome)).append("\n");
      sb.append("    codiceFiscale: ").append(toIndentedString(codiceFiscale)).append("\n");
      sb.append("}");
      return sb.toString();
   }

   /**
    * Convert the given object to string with each line indented by 4 spaces
    * (except the first line).
    */
   private String toIndentedString(Object o) {
      if (o == null) {
         return "null";
      }
      return o.toString().replace("\n", "\n    ");
   }
}

