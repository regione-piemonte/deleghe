/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.dto.deleghebowebLEGACY;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Dichiarazione   {
  // verra' utilizzata la seguente strategia serializzazione degli attributi: [explicit-as-modeled] 
  
  private String uuid = null;
  private DichiarazioneTipo tipo = null;
  private DichiarazioneStato stato = null;
  private DichiarazioneModo modo = null;
  private Cittadino cittadino = null;
  private Date dataInserimento = null;
  private List<DichiarazioneDettaglio> dettagli = new ArrayList<DichiarazioneDettaglio>();

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
  @JsonProperty("tipo")
 
  public DichiarazioneTipo getTipo() {
    return tipo;
  }
  public void setTipo(DichiarazioneTipo tipo) {
    this.tipo = tipo;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("stato")
 
  public DichiarazioneStato getStato() {
    return stato;
  }
  public void setStato(DichiarazioneStato stato) {
    this.stato = stato;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("modo")
 
  public DichiarazioneModo getModo() {
    return modo;
  }
  public void setModo(DichiarazioneModo modo) {
    this.modo = modo;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("cittadino")
 
  public Cittadino getCittadino() {
    return cittadino;
  }
  public void setCittadino(Cittadino cittadino) {
    this.cittadino = cittadino;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("data_inserimento")
 
  public Date getDataInserimento() {
    return dataInserimento;
  }
  public void setDataInserimento(Date dataInserimento) {
    this.dataInserimento = dataInserimento;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("dettagli")
 
  public List<DichiarazioneDettaglio> getDettagli() {
    return dettagli;
  }
  public void setDettagli(List<DichiarazioneDettaglio> dettagli) {
    this.dettagli = dettagli;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Dichiarazione dichiarazione = (Dichiarazione) o;
    return Objects.equals(uuid, dichiarazione.uuid) &&
        Objects.equals(tipo, dichiarazione.tipo) &&
        Objects.equals(stato, dichiarazione.stato) &&
        Objects.equals(modo, dichiarazione.modo) &&
        Objects.equals(cittadino, dichiarazione.cittadino) &&
        Objects.equals(dataInserimento, dichiarazione.dataInserimento) &&
        Objects.equals(dettagli, dichiarazione.dettagli);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid, tipo, stato, modo, cittadino, dataInserimento, dettagli);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Dichiarazione {\n");
    
    sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
    sb.append("    tipo: ").append(toIndentedString(tipo)).append("\n");
    sb.append("    stato: ").append(toIndentedString(stato)).append("\n");
    sb.append("    modo: ").append(toIndentedString(modo)).append("\n");
    sb.append("    cittadino: ").append(toIndentedString(cittadino)).append("\n");
    sb.append("    dataInserimento: ").append(toIndentedString(dataInserimento)).append("\n");
    sb.append("    dettagli: ").append(toIndentedString(dettagli)).append("\n");
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

