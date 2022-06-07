/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.dto.delegheboweb;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.Objects;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DelegaServizio   {
  
  private String uuid = null;
  private String codiceServizio = null;
  private String descriptionServizio = null;
  private Date dataInizioDelega = null;
  private Date dataFineDelega = null;
  private Date dataRevocaDelega = null;
  private Date dataRinunciaDelega = null;
  private Date dataCancellazione = null;
  private String statoDelega = null;
  private String gradoDelega = null;

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("data_cancellazione")
  public Date getDataCancellazione() {
    return dataCancellazione;
  }
  public void setDataCancellazione(Date dataCancellazione) {
    this.dataCancellazione = dataCancellazione;
  }


  @ApiModelProperty(value = "")
  @JsonProperty("description_servizio")
  public String getDescriptionServizio() {
    return descriptionServizio;
  }
  public void setDescriptionServizio(String descriptionServizio) {
    this.descriptionServizio = descriptionServizio;
  }

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
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("data_revoca_delega")
  public Date getDataRevocaDelega() {
    return dataRevocaDelega;
  }
  public void setDataRevocaDelega(Date dataRevocaDelega) {
    this.dataRevocaDelega = dataRevocaDelega;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("data_rinuncia_delega")
  public Date getDataRinunciaDelega() {
    return dataRinunciaDelega;
  }
  public void setDataRinunciaDelega(Date dataRinunciaDelega) {
    this.dataRinunciaDelega = dataRinunciaDelega;
  }

  /**
   * i possibili valori dello stato delega modificabili sono  ATTIVA, REVOCATA, RIFIUTATA. Altri valori restituibili dalla business ma non modificabili sono IN SCADENZA e SCADUTA.
   **/
  
  @ApiModelProperty(value = "i possibili valori dello stato delega modificabili sono  ATTIVA, REVOCATA, RIFIUTATA. Altri valori restituibili dalla business ma non modificabili sono IN SCADENZA e SCADUTA.")
  @JsonProperty("stato_delega")
  public String getStatoDelega() {
    return statoDelega;
  }
  public void setStatoDelega(String statoDelega) {
    this.statoDelega = statoDelega;
  }
  
  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("grado_delega")
  public String getGradoDelega() {
	return gradoDelega;
  }
  public void setGradoDelega(String gradoDelega) {
	this.gradoDelega = gradoDelega;
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
        Objects.equals(dataRevocaDelega, delegaServizio.dataRevocaDelega) &&
        Objects.equals(dataRinunciaDelega, delegaServizio.dataRinunciaDelega) &&
        Objects.equals(statoDelega, delegaServizio.statoDelega) &&
        Objects.equals(gradoDelega, delegaServizio.gradoDelega);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid, codiceServizio, dataInizioDelega, dataFineDelega, dataRevocaDelega, dataRinunciaDelega, statoDelega, gradoDelega);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DelegaServizio {\n");
    
    sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
    sb.append("    codiceServizio: ").append(toIndentedString(codiceServizio)).append("\n");
    sb.append("    dataInizioDelega: ").append(toIndentedString(dataInizioDelega)).append("\n");
    sb.append("    dataFineDelega: ").append(toIndentedString(dataFineDelega)).append("\n");
    sb.append("    dataRevocaDelega: ").append(toIndentedString(dataRevocaDelega)).append("\n");
    sb.append("    dataRinunciaDelega: ").append(toIndentedString(dataRinunciaDelega)).append("\n");
    sb.append("    statoDelega: ").append(toIndentedString(statoDelega)).append("\n");
    sb.append("    gradoDelega: ").append(toIndentedString(gradoDelega)).append("\n");
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

