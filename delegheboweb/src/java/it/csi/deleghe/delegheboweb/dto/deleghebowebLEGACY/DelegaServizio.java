/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.dto.deleghebowebLEGACY;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;
import java.util.Objects;


@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DelegaServizio   {
  // verra' utilizzata la seguente strategia serializzazione degli attributi: [explicit-as-modeled] 
  
  private String uuid = null;
  private String codiceServizio = null;
  private Date dataInizioDelega = null;
  private Date dataFineDelega = null;
  private String statoDelega = null;

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("uuid")
 
  public String getUuid() {
    return uuid;
  }
  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("codice_servizio")
 
  public String getCodiceServizio() {
    return codiceServizio;
  }
  public void setCodiceServizio(String codiceServizio) {
    this.codiceServizio = codiceServizio;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("data_inizio_delega")
 
  public Date getDataInizioDelega() {
    return dataInizioDelega;
  }
  public void setDataInizioDelega(Date dataInizioDelega) {
    this.dataInizioDelega = dataInizioDelega;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("data_fine_delega")
 
  public Date getDataFineDelega() {
    return dataFineDelega;
  }
  public void setDataFineDelega(Date dataFineDelega) {
    this.dataFineDelega = dataFineDelega;
  }

  /**
   * i possibili valori dello stato delega modificabili sono  Attiva,  Non attiva per proroga, Revocata, Rifiutata. Altri valori restituibili dalla business ma non modificabili sono In Scadenza E Scaduta.
   **/
  
  @ApiModelProperty(value = "i possibili valori dello stato delega modificabili sono  Attiva,  Non attiva per proroga, Revocata, Rifiutata. Altri valori restituibili dalla business ma non modificabili sono In Scadenza E Scaduta.")
  @JsonProperty("stato_delega")
 
  public String getStatoDelega() {
    return statoDelega;
  }
  public void setStatoDelega(String statoDelega) {
    this.statoDelega = statoDelega;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DelegaServizio delegaServizio = (DelegaServizio) o;
    return Objects.equals(uuid, delegaServizio.uuid) &&
        Objects.equals(codiceServizio, delegaServizio.codiceServizio) &&
        Objects.equals(dataInizioDelega, delegaServizio.dataInizioDelega) &&
        Objects.equals(dataFineDelega, delegaServizio.dataFineDelega) &&
        Objects.equals(statoDelega, delegaServizio.statoDelega);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid, codiceServizio, dataInizioDelega, dataFineDelega, statoDelega);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DelegaServizio {\n");
    
    sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
    sb.append("    codiceServizio: ").append(toIndentedString(codiceServizio)).append("\n");
    sb.append("    dataInizioDelega: ").append(toIndentedString(dataInizioDelega)).append("\n");
    sb.append("    dataFineDelega: ").append(toIndentedString(dataFineDelega)).append("\n");
    sb.append("    statoDelega: ").append(toIndentedString(statoDelega)).append("\n");
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

