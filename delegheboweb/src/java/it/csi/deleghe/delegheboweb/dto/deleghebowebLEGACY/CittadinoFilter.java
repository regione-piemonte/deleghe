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
public class CittadinoFilter   {
   
  
  private StringCrit codiceFiscale = null;
  private StringCrit cognome = null;
  private StringCrit nome = null;
  private DateCrit dataNascita = null;
  private StringCrit comuneNascita = null;
  private StringCrit sesso = null;

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
  @JsonProperty("cognome")
 
  public StringCrit getCognome() {
    return cognome;
  }
  public void setCognome(StringCrit cognome) {
    this.cognome = cognome;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("nome")
 
  public StringCrit getNome() {
    return nome;
  }
  public void setNome(StringCrit nome) {
    this.nome = nome;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("data_nascita")
 
  public DateCrit getDataNascita() {
    return dataNascita;
  }
  public void setDataNascita(DateCrit dataNascita) {
    this.dataNascita = dataNascita;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("comune_nascita")
 
  public StringCrit getComuneNascita() {
    return comuneNascita;
  }
  public void setComuneNascita(StringCrit comuneNascita) {
    this.comuneNascita = comuneNascita;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("sesso")
 
  public StringCrit getSesso() {
    return sesso;
  }
  public void setSesso(StringCrit sesso) {
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
    CittadinoFilter cittadinoFilter = (CittadinoFilter) o;
    return Objects.equals(codiceFiscale, cittadinoFilter.codiceFiscale) &&
        Objects.equals(cognome, cittadinoFilter.cognome) &&
        Objects.equals(nome, cittadinoFilter.nome) &&
        Objects.equals(dataNascita, cittadinoFilter.dataNascita) &&
        Objects.equals(comuneNascita, cittadinoFilter.comuneNascita) &&
        Objects.equals(sesso, cittadinoFilter.sesso);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codiceFiscale, cognome, nome, dataNascita, comuneNascita, sesso);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CittadinoFilter {\n");
    
    sb.append("    codiceFiscale: ").append(toIndentedString(codiceFiscale)).append("\n");
    sb.append("    cognome: ").append(toIndentedString(cognome)).append("\n");
    sb.append("    nome: ").append(toIndentedString(nome)).append("\n");
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

