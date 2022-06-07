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
public class DelegaFilter   {
  
  private StringCrit codiceFiscale = null;
  private StringCrit statoDelega = null;
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
  @JsonProperty("stato_delega")
  public StringCrit getStatoDelega() {
    return statoDelega;
  }
  public void setStatoDelega(StringCrit statoDelega) {
    this.statoDelega = statoDelega;
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
    DelegaFilter delegaFilter = (DelegaFilter) o;
    return Objects.equals(codiceFiscale, delegaFilter.codiceFiscale) &&
        Objects.equals(statoDelega, delegaFilter.statoDelega) &&
        Objects.equals(codiceServizio, delegaFilter.codiceServizio);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codiceFiscale, statoDelega, codiceServizio);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DelegaFilter {\n");
    
    sb.append("    codiceFiscale: ").append(toIndentedString(codiceFiscale)).append("\n");
    sb.append("    statoDelega: ").append(toIndentedString(statoDelega)).append("\n");
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

