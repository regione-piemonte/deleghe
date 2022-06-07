/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.dto.delegheboweb;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.annotations.ApiModelProperty;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Servizio  {
	
	private String codice = null;
	private Integer serId = null;
	private String  validitaServizio = null;
	
	@ApiModelProperty(value = "Valore tipo")
	  @JsonProperty("codice")
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	
	@ApiModelProperty(value = "Identificativo tipo")
	  @JsonProperty("ser_id")
	public Integer getSerId() {
		return serId;
	}
	public void setSerId(Integer serId) {
		this.serId = serId;
	}
	
	@ApiModelProperty(value = "Giorni validita servizio")
	  @JsonProperty("validita_servizio")
	public String getValiditaServizio() {
		return validitaServizio;
	}
	public void setValiditaServizio(String validitaServizio) {
		this.validitaServizio = validitaServizio;
	}
	
	 @Override
	  public boolean equals(Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    Servizio servizio = (Servizio) o;
	    return Objects.equals(codice, servizio.codice) &&
	        Objects.equals(serId, servizio.serId) &&
	        Objects.equals(validitaServizio, servizio.validitaServizio) ;
	  }

	  @Override
	  public int hashCode() {
	    return Objects.hash(codice, serId, validitaServizio);
	  }

	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class Documento {\n");
	    
	    sb.append("    codice: ").append(toIndentedString(codice)).append("\n");
	    sb.append("    serId: ").append(toIndentedString(serId)).append("\n");
	    sb.append("    validitaServizio: ").append(toIndentedString(validitaServizio)).append("\n");
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
