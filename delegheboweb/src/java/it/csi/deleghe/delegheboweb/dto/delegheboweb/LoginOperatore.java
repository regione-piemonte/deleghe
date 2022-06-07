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
public class LoginOperatore {
	
	 private String codiceFiscaleOperatore = null;
	 private String cognomeOperatore = null;
	 private String nomeOperatore = null;
	 private String tokenLcce = null;
	 private String ipAddress = null;

	  /**
	   **/
	  
	  @ApiModelProperty(value = "")
	  @JsonProperty("codice_fiscale_operatore")
	  public String getCodiceFiscaleOperatore() {
	    return codiceFiscaleOperatore;
	  }
	  public void setCodiceFiscaleOperatore(String codiceFiscaleOperatore) {
	    this.codiceFiscaleOperatore = codiceFiscaleOperatore;
	  }

	  /**
	   **/
	  
	  @ApiModelProperty(value = "")
	  @JsonProperty("cognome_operatore")
	  public String getCognomeOperatore() {
	    return cognomeOperatore;
	  }
	  public void setCognomeOperatore(String cognomeOperatore) {
	    this.cognomeOperatore = cognomeOperatore;
	  }
	  
	  /**
	   **/
	  
	  @ApiModelProperty(value = "")
	  @JsonProperty("nome_operatore")
	  public String getNomeOperatore() {
			return nomeOperatore;
	  }
	  public void setNomeOperatore(String nomeOperatore) {
		  this.nomeOperatore = nomeOperatore;
	  }
	  
	  @ApiModelProperty(value = "")
	  @JsonProperty("token_lcce")
	  public String getTokenLcce() {
		  return tokenLcce;
	  }
	  public void setTokenLcce(String tokenLcce) {
		  this.tokenLcce = tokenLcce;
	  }
	  
	  @ApiModelProperty(value = "")
	  @JsonProperty("ip_address")
	  public String getIpAddress() {
			return ipAddress;
	  }
	  public void setIpAddress(String ipAddress) {
			this.ipAddress = ipAddress;
	  }

	  @Override
	  public boolean equals(Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    LoginOperatore login = (LoginOperatore) o;
	    return Objects.equals(codiceFiscaleOperatore, login.codiceFiscaleOperatore) &&
	        Objects.equals(cognomeOperatore, login.cognomeOperatore) &&
	        Objects.equals(nomeOperatore, login.nomeOperatore) &&
	        Objects.equals(tokenLcce, login.tokenLcce) &&
	        Objects.equals(ipAddress, login.ipAddress);
	  }

	  @Override
	  public int hashCode() {
	    return Objects.hash(codiceFiscaleOperatore, cognomeOperatore, nomeOperatore, tokenLcce, ipAddress);
	  }

	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class Dettaglio {\n");
	    
	    sb.append("    codiceFiscaleOperatore: ").append(toIndentedString(codiceFiscaleOperatore)).append("\n");
	    sb.append("    cognomeOperatore: ").append(toIndentedString(cognomeOperatore)).append("\n");
	    sb.append("    nomeOperatore: ").append(toIndentedString(nomeOperatore)).append("\n");
	    sb.append("    tokenLcce: ").append(toIndentedString(tokenLcce)).append("\n");
	    sb.append("    ipAddress: ").append(toIndentedString(ipAddress)).append("\n");
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
