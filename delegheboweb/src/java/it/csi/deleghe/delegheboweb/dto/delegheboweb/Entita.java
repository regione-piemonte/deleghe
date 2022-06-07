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
public class Entita {
	
	private String loginOperazione;
	private Date dataCreazione;
	private Date dataModifica;
	private Date dataCancellazione;	
	private RuoloOperazione ruoloOperazione;
	
	/**
	 **/
	  
	@ApiModelProperty(value = "")
	@JsonProperty("loginOperazione")
	public String getLoginOperazione() {
		return loginOperazione;
	}
	public void setLoginOperazione(String loginOperazione) {
		this.loginOperazione = loginOperazione;
	}

	/**
	 **/
	  
	@ApiModelProperty(value = "")
	@JsonProperty("dataCreazione")
	public Date getDataCreazione() {
		return dataCreazione;
	}
	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	/**
	 **/
	  
	@ApiModelProperty(value = "")
	@JsonProperty("dataModifica")
	public Date getDataModifica() {
		return dataModifica;
	}
	public void setDataModifica(Date dataModifica) {
		this.dataModifica = dataModifica;
	}

	/**
	 **/
	  
	@ApiModelProperty(value = "")
	@JsonProperty("dataCancellazione")
	public Date getDataCancellazione() {
		return dataCancellazione;
	}
	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}
	
	/**
	 **/
	  
	@ApiModelProperty(value = "")
	@JsonProperty("ruoloOperazione")
	public RuoloOperazione getRuoloOperazione() {
		return ruoloOperazione;
	}
	public void setRuoloOperazione(RuoloOperazione ruoloOperazione) {
		this.ruoloOperazione = ruoloOperazione;
	}
	

	 @Override
	  public boolean equals(Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    Entita entita = (Entita) o;
	    return Objects.equals(loginOperazione, entita.loginOperazione) &&
	        Objects.equals(dataCreazione, entita.dataCreazione) &&
	        Objects.equals(dataModifica, entita.dataModifica) &&
	        Objects.equals(dataCancellazione, entita.dataCancellazione) &&
	        Objects.equals(ruoloOperazione, entita.ruoloOperazione);
	  }

	  @Override
	  public int hashCode() {
	    return Objects.hash(loginOperazione, dataCreazione, dataModifica, dataCancellazione, ruoloOperazione);
	  }

	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class Entita {\n");
	    
	    sb.append("    loginOperazione: ").append(toIndentedString(loginOperazione)).append("\n");
	    sb.append("    dataCreazione: ").append(toIndentedString(dataCreazione)).append("\n");
	    sb.append("    dataModifica: ").append(toIndentedString(dataModifica)).append("\n");
	    sb.append("    dataCancellazione: ").append(toIndentedString(dataCancellazione)).append("\n");
	    sb.append("    ruoloOperazione: ").append(toIndentedString(ruoloOperazione)).append("\n");
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
