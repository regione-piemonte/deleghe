/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.dto.delegheboweb;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.annotations.ApiModelProperty;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Operatore {
	
	private boolean esitoRicerca;
	private String aslOperatore;
	
	@ApiModelProperty(value = "")
	  @JsonProperty("esito_ricerca")
	public boolean isEsitoRicerca() {
		return esitoRicerca;
	}

	public void setEsitoRicerca(boolean esitoRicerca) {
		this.esitoRicerca = esitoRicerca;
	}

	@ApiModelProperty(value = "")
	  @JsonProperty("asl_operatore")
	public String getAslOperatore() {
		return aslOperatore;
	}

	public void setAslOperatore(String aslOperatore) {
		this.aslOperatore = aslOperatore;
	}
	  
	
	
	@Override
	  public boolean equals(Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    Operatore operatore = (Operatore) o;
	    return Objects.equals(esitoRicerca, operatore.esitoRicerca)  &&
	        Objects.equals(aslOperatore, operatore.aslOperatore);
	  }
	
	 @Override
	  public int hashCode() {
	    return Objects.hash(esitoRicerca, aslOperatore);
	  }

	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class Operatore {\n");
	    
	    sb.append("    esitoRicerca: ").append(toIndentedString(esitoRicerca)).append("\n");
	    sb.append("    aslOperatore: ").append(toIndentedString(aslOperatore)).append("\n");
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
