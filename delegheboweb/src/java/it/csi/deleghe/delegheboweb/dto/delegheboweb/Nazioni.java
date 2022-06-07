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
public class Nazioni {
	
	private String desc = null;	
	
	@ApiModelProperty(example = "FRANCIA", value = "descrizione nazione")
	@JsonProperty("descrizione")
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}	
	
	@Override
	  public boolean equals(Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    Nazioni nazioni = (Nazioni) o;
	    return Objects.equals(desc, nazioni.desc);
	  }

	 @Override
	  public int hashCode() {
	    return Objects.hash(desc);
	  }
	 
	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class Nazioni {\n");	    
	    sb.append("    desc: ").append(toIndentedString(desc)).append("\n");
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
