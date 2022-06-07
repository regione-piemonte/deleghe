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
public class Utente   {
  // verra' utilizzata la seguente strategia serializzazione degli attributi: [explicit-as-modeled] 
  
  private String nome = null;
  private String cognome = null;
  private String codiceFiscale = null;
  private String idProvider = null;
  private String timestamp = null;
  private String livelloAutenticazione = null;

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("nome")
 
  public String getNome() {
    return nome;
  }
  public void setNome(String nome) {
    this.nome = nome;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("cognome")
 
  public String getCognome() {
    return cognome;
  }
  public void setCognome(String cognome) {
    this.cognome = cognome;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("codice_fiscale")
 
  public String getCodiceFiscale() {
    return codiceFiscale;
  }
  public void setCodiceFiscale(String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("id_provider")
 
  public String getIdProvider() {
    return idProvider;
  }
  public void setIdProvider(String idProvider) {
    this.idProvider = idProvider;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("timestamp")
 
  public String getTimestamp() {
    return timestamp;
  }
  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("livello_autenticazione")
 
  public String getLivelloAutenticazione() {
    return livelloAutenticazione;
  }
  public void setLivelloAutenticazione(String livelloAutenticazione) {
    this.livelloAutenticazione = livelloAutenticazione;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Utente utente = (Utente) o;
    return Objects.equals(nome, utente.nome) &&
        Objects.equals(cognome, utente.cognome) &&
        Objects.equals(codiceFiscale, utente.codiceFiscale) &&
        Objects.equals(idProvider, utente.idProvider) &&
        Objects.equals(timestamp, utente.timestamp) &&
        Objects.equals(livelloAutenticazione, utente.livelloAutenticazione);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nome, cognome, codiceFiscale, idProvider, timestamp, livelloAutenticazione);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Utente {\n");
    
    sb.append("    nome: ").append(toIndentedString(nome)).append("\n");
    sb.append("    cognome: ").append(toIndentedString(cognome)).append("\n");
    sb.append("    codiceFiscale: ").append(toIndentedString(codiceFiscale)).append("\n");
    sb.append("    idProvider: ").append(toIndentedString(idProvider)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("    livelloAutenticazione: ").append(toIndentedString(livelloAutenticazione)).append("\n");
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

