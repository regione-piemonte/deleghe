/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.dto.deleghebowebLEGACY;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Objects;


@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DelegaFilter   {
  // verra' utilizzata la seguente strategia serializzazione degli attributi: [explicit-as-modeled] 
  
  private StringCrit uuid = null;
  private StringCrit codiceFiscaleDelegante = null;
  private StringCrit codiceFiscaleDelegato = null;
  private StringCrit statoDelega = null;
  private StringCrit codiceServizio = null;

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("uuid")
 
  public StringCrit getUuid() {
    return uuid;
  }
  public void setUuid(StringCrit uuid) {
    this.uuid = uuid;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("codice_fiscale_delegante")
 
  public StringCrit getCodiceFiscaleDelegante() {
    return codiceFiscaleDelegante;
  }
  public void setCodiceFiscaleDelegante(StringCrit codiceFiscaleDelegante) {
    this.codiceFiscaleDelegante = codiceFiscaleDelegante;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("codice_fiscale_delegato")
 
  public StringCrit getCodiceFiscaleDelegato() {
    return codiceFiscaleDelegato;
  }
  public void setCodiceFiscaleDelegato(StringCrit codiceFiscaleDelegato) {
    this.codiceFiscaleDelegato = codiceFiscaleDelegato;
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
    return Objects.equals(uuid, delegaFilter.uuid) &&
        Objects.equals(codiceFiscaleDelegante, delegaFilter.codiceFiscaleDelegante) &&
        Objects.equals(codiceFiscaleDelegato, delegaFilter.codiceFiscaleDelegato) &&
        Objects.equals(statoDelega, delegaFilter.statoDelega) &&
        Objects.equals(codiceServizio, delegaFilter.codiceServizio);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid, codiceFiscaleDelegante, codiceFiscaleDelegato, statoDelega, codiceServizio);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DelegaFilter {\n");
    
    sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
    sb.append("    codiceFiscaleDelegante: ").append(toIndentedString(codiceFiscaleDelegante)).append("\n");
    sb.append("    codiceFiscaleDelegato: ").append(toIndentedString(codiceFiscaleDelegato)).append("\n");
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

