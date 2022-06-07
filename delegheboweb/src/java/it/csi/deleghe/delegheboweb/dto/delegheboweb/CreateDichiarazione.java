/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.dto.delegheboweb;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;
import java.util.Objects;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CreateDichiarazione {

   private Dichiarazione dichiarazione = null;
   private Date dataInizio = null;
   private Date dataFine = null;

   @ApiModelProperty(value = "")
   @JsonProperty("data_inizio")
   public Date getDataInizio() {
      return dataInizio;
   }
   public void setDataInizio(Date dataInizio) {
      this.dataInizio = dataInizio;
   }

   @ApiModelProperty(value = "")
   @JsonProperty("data_fine")
   public Date getDataFine() {
      return dataFine;
   }
   public void setDataFine(Date dataFine) {
      this.dataFine = dataFine;
   }

   @ApiModelProperty(value = "")
   @JsonProperty("dichiarazione")
   public Dichiarazione getDichiarazione() {
      return dichiarazione;
   }
   public void setDichiarazione(Dichiarazione dichiarazione) {
      this.dichiarazione = dichiarazione;
   }


   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      }
      if (o == null || getClass() != o.getClass()) {
         return false;
      }
      CreateDichiarazione createDichiarazione = (CreateDichiarazione) o;
      return Objects.equals(dichiarazione, createDichiarazione.dichiarazione) &&
            Objects.equals(dataFine, createDichiarazione.dataFine) &&
            Objects.equals(dataInizio, createDichiarazione.dataInizio);
   }

   @Override
   public int hashCode() {
      return Objects.hash(dataInizio, dataFine, dataFine);
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("class Dichiarazione {\n");

      sb.append("    dichiarazione: ").append(toIndentedString(dichiarazione)).append("\n");
      sb.append("    dataInizio: ").append(toIndentedString(dataInizio)).append("\n");
      sb.append("    dataFine: ").append(toIndentedString(dataFine)).append("\n");
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
