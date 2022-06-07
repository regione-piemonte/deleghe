/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.dto.deleghebowebLEGACY;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.Delega;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Cittadino   {

  
  private String codiceFiscale = null;
  private String nome = null;
  private String cognome = null;
  private Date dataNascita = null;
  private String comuneNascita = null;
  private String sesso = null;
  private Integer idAura = null;
  private List<Delega> deleghe = new ArrayList<Delega>();

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

  /**
   * idAura questo campo non è obbligatorio
   **/
  
  @ApiModelProperty(value = "idAura questo campo non è obbligatorio")
  @JsonProperty("id_aura")
 
  public Integer getIdAura() {
    return idAura;
  }
  public void setIdAura(Integer idAura) {
    this.idAura = idAura;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("deleghe")
 
  public List<Delega> getDeleghe() {
    return deleghe;
  }
  public void setDeleghe(List<Delega> deleghe) {
    this.deleghe = deleghe;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Cittadino cittadino = (Cittadino) o;
    return Objects.equals(codiceFiscale, cittadino.codiceFiscale) &&
        Objects.equals(nome, cittadino.nome) &&
        Objects.equals(cognome, cittadino.cognome) &&
        Objects.equals(dataNascita, cittadino.dataNascita) &&
        Objects.equals(comuneNascita, cittadino.comuneNascita) &&
        Objects.equals(sesso, cittadino.sesso) &&
        Objects.equals(idAura, cittadino.idAura) &&
        Objects.equals(deleghe, cittadino.deleghe);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codiceFiscale, nome, cognome, dataNascita, comuneNascita, sesso, idAura, deleghe);
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
    sb.append("    idAura: ").append(toIndentedString(idAura)).append("\n");
    sb.append("    deleghe: ").append(toIndentedString(deleghe)).append("\n");
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

