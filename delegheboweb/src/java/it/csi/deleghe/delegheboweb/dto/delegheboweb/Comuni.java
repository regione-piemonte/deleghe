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
public class Comuni {
	
	private String desc = null;	
	private String codice_catasto= null;
	
	@ApiModelProperty(example = "TORINO", value = "descrizione comune")
	@JsonProperty("descrizione")
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	@ApiModelProperty(example = "L219A", value = "Codice catasto")
	@JsonProperty("codice_catasto")
	public String getCodice_catasto() {
		return codice_catasto;
	}

	public void setCodice_catasto(String codice_catasto) {
		this.codice_catasto = codice_catasto;
	}
	
	
	@Override
	  public boolean equals(Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    Comuni comuni = (Comuni) o;
	    return Objects.equals(desc, comuni.desc) &&
	            Objects.equals(codice_catasto, comuni.codice_catasto);
	  }

	 @Override
	  public int hashCode() {
	    return Objects.hash(desc);
	  }
	 
	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class Comuni {\n");
	    
	    sb.append("    desc: ").append(toIndentedString(desc)).append("\n");
	    sb.append("    codice_catasto: ").append(toIndentedString(codice_catasto)).append("\n");
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
