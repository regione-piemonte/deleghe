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

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DelegaValidaFilter   {
  
  private StringCrit codiceFiscale = null;
  private StringCrit codiceServizio = null;

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("codice_fiscale")
  public StringCrit getCodiceFiscale() {
    return codiceFiscale;
  }
  public void setCodiceFiscale(StringCrit codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("codice_servizio")
  public StringCrit getCodiceServizio() {
    return codiceServizio;
  }
  public void setCodiceServizio(StringCrit codiceServizio) {
    this.codiceServizio = codiceServizio;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DelegaValidaFilter delegaValidaFilter = (DelegaValidaFilter) o;
    return Objects.equals(codiceFiscale, delegaValidaFilter.codiceFiscale) &&
        Objects.equals(codiceServizio, delegaValidaFilter.codiceServizio);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codiceFiscale, codiceServizio);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DelegaValidaFilter {\n");
    
    sb.append("    codiceFiscale: ").append(toIndentedString(codiceFiscale)).append("\n");
    sb.append("    codiceServizio: ").append(toIndentedString(codiceServizio)).append("\n");
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

