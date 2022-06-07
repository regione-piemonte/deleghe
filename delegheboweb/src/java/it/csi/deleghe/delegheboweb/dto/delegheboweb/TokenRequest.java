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
public class TokenRequest {
	
	private String token;
	private String applicazione;
	private String ipBrowser;
	
	@ApiModelProperty(value = "")
	@JsonProperty("token")
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	@ApiModelProperty(value = "")
	@JsonProperty("applicazione")
	public String getApplicazione() {
		return applicazione;
	}
	public void setApplicazione(String applicazione) {
		this.applicazione = applicazione;
	}
	
	@ApiModelProperty(value = "")
	@JsonProperty("ip_browser")
	public String getIpBrowser() {
		return ipBrowser;
	}
	public void setIpBrowser(String ipBrowser) {
		this.ipBrowser = ipBrowser;
	}
	
	@Override
	  public boolean equals(Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    TokenRequest tokenRequest = (TokenRequest) o;
	    return Objects.equals(token, tokenRequest.token) &&
	        Objects.equals(applicazione, tokenRequest.applicazione) &&
	        Objects.equals(ipBrowser, tokenRequest.ipBrowser);
	  }

	  @Override
	  public int hashCode() {
	    return Objects.hash(token, applicazione, ipBrowser);
	  }
	  
	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class TokenRequest {\n");
	    
	    sb.append("    token: ").append(toIndentedString(token)).append("\n");
	    sb.append("    applicazione: ").append(toIndentedString(applicazione)).append("\n");
	    sb.append("    ipBrowser: ").append(toIndentedString(ipBrowser)).append("\n");;
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
