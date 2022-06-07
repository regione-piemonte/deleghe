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
public class InfoServizio   {
  
  private String nome = null;
  private String descrizione = null;
  private String serviceDescrizione = null;
  private Date data = null;
  private Boolean servizioAttivo = null;
  private Boolean delegabile = null;
  private String codSerpadre = null;
  private String fraseDebole = null;
  private String fraseForte = null;
  private Integer numeroGiorniDelegabili=null;

  /**
   * nome del servizio
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("service_descrizione")
  public String getServiceDescrizione() {
    return serviceDescrizione;
  }
  public void setServiceDescrizione(String serviceDescrizione) {
    this.serviceDescrizione = serviceDescrizione;
  }
  
  @ApiModelProperty(value = "nome del servizio")
  @JsonProperty("nome")
  public String getNome() {
    return nome;
  }
  public void setNome(String nome) {
    this.nome = nome;
  }
  
  @ApiModelProperty(value = "codice servizio padre")
  @JsonProperty("cod_serpadre")
  public String getCodSerpadre() {
		return codSerpadre;
  }
  public void setCodSerpadre(String codSerpadre) {
	this.codSerpadre = codSerpadre;
  }
  
  @ApiModelProperty(value = "frase per delega debole")
  @JsonProperty("frase_debole")
  public String getFraseDebole() {
		return fraseDebole;
  }
	
  public void setFraseDebole(String fraseDebole) {
	this.fraseDebole = fraseDebole;
  }

  @ApiModelProperty(value = "frase per delega forte")
  @JsonProperty("frase_forte")
  public String getFraseForte() {
	return fraseForte;
  }
	
  public void setFraseForte(String fraseForte) {
	this.fraseForte = fraseForte;
  }

  /**
   * descrizione del servizio
   **/
  
  @ApiModelProperty(value = "descrizione del servizio")
  @JsonProperty("descrizione")
  public String getDescrizione() {
    return descrizione;
  }
  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("data")
  public Date getData() {
    return data;
  }
  public void setData(Date data) {
    this.data = data;
  }

  /**
   * boolean di utilita per eventuali test
   **/
  
  @ApiModelProperty(value = "boolean di utilita per eventuali test")
  @JsonProperty("servizio_attivo")
  public Boolean isServizioAttivo() {
    return servizioAttivo;
  }
  public void setServizioAttivo(Boolean servizioAttivo) {
    this.servizioAttivo = servizioAttivo;
  }
  
  @ApiModelProperty(value = "Numero giorni delegabili")
  @JsonProperty("numero_giorni_delegabili")
  public Integer getNumeroGiorniDelegabili() {
	return numeroGiorniDelegabili;
  }
  public void setNumeroGiorniDelegabili(Integer numeroGiorniDelegabili) {
	this.numeroGiorniDelegabili = numeroGiorniDelegabili;
  }

  @ApiModelProperty(value = "Flag delegabile")
  @JsonProperty("delegabile")
  public Boolean getDelegabile() {
		return delegabile;
	}
	public void setDelegabile(Boolean delegabile) {
		this.delegabile = delegabile;
	}

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InfoServizio infoServizio = (InfoServizio) o;
    return Objects.equals(nome, infoServizio.nome) &&
        Objects.equals(descrizione, infoServizio.descrizione) &&
        Objects.equals(data, infoServizio.data) &&
        Objects.equals(serviceDescrizione, infoServizio.serviceDescrizione) &&
        Objects.equals(servizioAttivo, infoServizio.servizioAttivo) &&
        Objects.equals(codSerpadre, infoServizio.codSerpadre) &&
        Objects.equals(fraseDebole, infoServizio.fraseDebole) &&
        Objects.equals(fraseForte, infoServizio.fraseForte)  &&
        Objects.equals(numeroGiorniDelegabili, infoServizio.numeroGiorniDelegabili)  &&
        Objects.equals(delegabile, infoServizio.delegabile);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nome, serviceDescrizione, descrizione, data, servizioAttivo, codSerpadre, fraseDebole, fraseForte, numeroGiorniDelegabili, delegabile);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InfoServizio {\n");
    
    sb.append("    nome: ").append(toIndentedString(nome)).append("\n");
    sb.append("    descrizione: ").append(toIndentedString(descrizione)).append("\n");
    sb.append("    serviceDescrizione: ").append(toIndentedString(serviceDescrizione)).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
    sb.append("    servizioAttivo: ").append(toIndentedString(servizioAttivo)).append("\n");
    sb.append("    codSerpadre: ").append(toIndentedString(codSerpadre)).append("\n");
    sb.append("    fraseDebole: ").append(toIndentedString(fraseDebole)).append("\n");
    sb.append("    fraseForte: ").append(toIndentedString(fraseForte)).append("\n");
    sb.append("    numeroGiorniDelegabili: ").append(toIndentedString(numeroGiorniDelegabili)).append("\n");
    sb.append("    delegabile: ").append(toIndentedString(delegabile)).append("\n");
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

