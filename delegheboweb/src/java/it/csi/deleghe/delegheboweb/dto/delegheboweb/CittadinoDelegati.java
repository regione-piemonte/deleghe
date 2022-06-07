/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.dto.delegheboweb;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CittadinoDelegati   {
  
  private Cittadino cittadino = null;
  private List<DelegaCittadino> delegati = new ArrayList<DelegaCittadino>();

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
  @JsonProperty("delegati")
  public List<DelegaCittadino> getDelegati() {
    return delegati;
  }
  public void setDelegati(List<DelegaCittadino> delegati) {
    this.delegati = delegati;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CittadinoDelegati cittadinoDelegati = (CittadinoDelegati) o;
    return Objects.equals(cittadino, cittadinoDelegati.cittadino) &&
        Objects.equals(delegati, cittadinoDelegati.delegati);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cittadino, delegati);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CittadinoDelegati {\n");
    
    sb.append("    cittadino: ").append(toIndentedString(cittadino)).append("\n");
    sb.append("    delegati: ").append(toIndentedString(delegati)).append("\n");
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

