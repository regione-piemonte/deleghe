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
public class Delegato   {
  // verra' utilizzata la seguente strategia serializzazione degli attributi: [explicit-as-modeled] 
  
  private String codiceFiscale = null;
  private String nome = null;
  private String cognome = null;
  private Date dataNascita = null;
  private String comuneNascita = null;
  private String sesso = null;
  

  /**
   * codice fiscale
   **/
  
  @ApiModelProperty(example = "xyz", value = "codice fiscale")
  @JsonProperty("codice_fiscale")
 
  public String getCodiceFiscale() {
    return codiceFiscale;
  }
  public void setCodiceFiscale(String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
  }

  /**
   * nome
   **/
  
  @ApiModelProperty(value = "nome")
  @JsonProperty("nome")
 
  public String getNome() {
    return nome;
  }
  public void setNome(String nome) {
    this.nome = nome;
  }

  /**
   * cognome
   **/
  
  @ApiModelProperty(value = "cognome")
  @JsonProperty("cognome")
 
  public String getCognome() {
    return cognome;
  }
  public void setCognome(String cognome) {
    this.cognome = cognome;
  }

  /**
   * data di nascita
   **/
  
  @ApiModelProperty(value = "data di nascita")
  @JsonProperty("data_nascita")
 
  public Date getDataNascita() {
    return dataNascita;
  }
  public void setDataNascita(Date dataNascita) {
    this.dataNascita = dataNascita;
  }

  /**
   * comune di nascita
   **/
  
  @ApiModelProperty(value = "comune di nascita")
  @JsonProperty("comune_nascita")
 
  public String getComuneNascita() {
    return comuneNascita;
  }
  public void setComuneNascita(String comuneNascita) {
    this.comuneNascita = comuneNascita;
  }

  /**
   * sesso questo campo non è obbligatorio
   **/
  
  @ApiModelProperty(value = "sesso questo campo non è obbligatorio")
  @JsonProperty("sesso")
 
  public String getSesso() {
    return sesso;
  }
  public void setSesso(String sesso) {
    this.sesso = sesso;
  }




  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Delegato cittadino = (Delegato) o;
    return Objects.equals(codiceFiscale, cittadino.codiceFiscale) &&
        Objects.equals(nome, cittadino.nome) &&
        Objects.equals(cognome, cittadino.cognome) &&
        Objects.equals(dataNascita, cittadino.dataNascita) &&
        Objects.equals(comuneNascita, cittadino.comuneNascita) &&
        Objects.equals(sesso, cittadino.sesso);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codiceFiscale, nome, cognome, dataNascita, comuneNascita, sesso);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Cittadino {\n");
    
    sb.append("    codiceFiscale: ").append(toIndentedString(codiceFiscale)).append("\n");
    sb.append("    nome: ").append(toIndentedString(nome)).append("\n");
    sb.append("    cognome: ").append(toIndentedString(cognome)).append("\n");
    sb.append("    dataNascita: ").append(toIndentedString(dataNascita)).append("\n");
    sb.append("    comuneNascita: ").append(toIndentedString(comuneNascita)).append("\n");
    sb.append("    sesso: ").append(toIndentedString(sesso)).append("\n");
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

