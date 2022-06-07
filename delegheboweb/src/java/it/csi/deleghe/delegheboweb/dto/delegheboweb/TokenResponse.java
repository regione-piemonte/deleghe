/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.dto.delegheboweb;

import java.util.Objects;

import org.codehaus.jackson.annotate.JsonProperty;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.annotations.ApiModelProperty;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TokenResponse {
	
	private String codiceAzienda;
	private String codiceFiscale;
	private String esito;
	
	@ApiModelProperty(value = "")
	@JsonProperty("codice_azienda")
	public String getCodiceAzienda() {
		return codiceAzienda;
	}
	public void setCodiceAzienda(String codiceAzienda) {
		this.codiceAzienda = codiceAzienda;
	}
	
	@ApiModelProperty(value = "")
	@JsonProperty("codice_fiscale")
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	@ApiModelProperty(value = "")
	@JsonProperty("esito")
	public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		this.esito = esito;
	}
	
	@Override
	  public boolean equals(Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    TokenResponse tokenResponse = (TokenResponse) o;
	    return Objects.equals(codiceAzienda, tokenResponse.codiceAzienda) &&
	        Objects.equals(codiceFiscale, tokenResponse.codiceFiscale) &&
	        Objects.equals(esito, tokenResponse.esito);
	  }

	  @Override
	  public int hashCode() {
	    return Objects.hash(codiceAzienda, codiceFiscale, esito);
	  }
	  
	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class TokenRequest {\n");
	    
	    sb.append("    codiceAzienda: ").append(toIndentedString(codiceAzienda)).append("\n");
	    sb.append("    codiceFiscale: ").append(toIndentedString(codiceFiscale)).append("\n");
	    sb.append("    esito: ").append(toIndentedString(esito)).append("\n");
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
