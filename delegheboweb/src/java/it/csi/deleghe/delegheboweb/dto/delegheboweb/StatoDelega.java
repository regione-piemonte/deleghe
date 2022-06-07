/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.dto.delegheboweb;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.annotations.ApiModelProperty;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class StatoDelega   {
  
  private String codice = null;
  private String descrizione = null;


  /**
   * codice stato delega
   **/
  
  @ApiModelProperty(value = "codice stato delega")
  @JsonProperty("codice")
  public String getCodice() {
    return codice;
  }
  public void setCodice(String codice) {
    this.codice = codice;
  }
  

  /**
   * descrizione della delega
   **/
  
  @ApiModelProperty(value = "descrizione della delega")
  @JsonProperty("descrizione")
  public String getDescrizione() {
    return descrizione;
  }
  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }

 

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StatoDelega statoDichiarazione = (StatoDelega) o;
    return Objects.equals(codice, statoDichiarazione.codice) &&
        Objects.equals(descrizione, statoDichiarazione.descrizione) ;
  }

  @Override
  public int hashCode() {
    return Objects.hash(codice, descrizione);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StatoDelega {\n");
    sb.append("    codice: ").append(toIndentedString(codice)).append("\n");
    sb.append("    descrizione: ").append(toIndentedString(descrizione)).append("\n");
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

