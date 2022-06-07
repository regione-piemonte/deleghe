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
public class InformativaConsensi {
	
	 private String pdfInformativa;
	 private Date dataDecorrenza;
	 private Date dataScadenza;
	 private String descInformativa;
	 private String htmlInformativa;
	 
	 /**
	   * pdfInformativa
	   **/
	  
	  @ApiModelProperty(value = "pdfInformativa")
	  @JsonProperty("pdf_informativa")
	  public String getPdfInformativa() {
		return pdfInformativa;
	  }
	  public void setPdfInformativa(String pdfInformativa) {
		this.pdfInformativa = pdfInformativa;
	  }
	  
	  /**
	   * dataDecorrenza
	   **/
	  
	  @ApiModelProperty(value = "dataDecorrenza")
	  @JsonProperty("data_decorrenza")  
	  public Date getDataDecorrenza() {
		return dataDecorrenza;
	  }
	  public void setDataDecorrenza(Date dataDecorrenza) {
		this.dataDecorrenza = dataDecorrenza;
	  }
	
	 /**
	   * dataScadenza
	   **/
	  
	  @ApiModelProperty(value = "dataScadenza")
	  @JsonProperty("data_scadenza")
	  public Date getDataScadenza() {
		return dataScadenza;
	  }
	  public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	  }
	
	 /**
	   * descInformativa
	   **/
	  
	  @ApiModelProperty(value = "descInformativa")
	  @JsonProperty("desc_informativa")
	  public String getDescInformativa() {
		return descInformativa;
	  }
	  public void setDescInformativa(String descInformativa) {
		this.descInformativa = descInformativa;
	  }
	
	 /**
	   * htmlInformativa
	   **/
	  
	  @ApiModelProperty(value = "htmlInformativa")
	  @JsonProperty("html_informativa")
	  public String getHtmlInformativa() {
		return htmlInformativa;
	  }
	  public void setHtmlInformativa(String htmlInformativa) {
		this.htmlInformativa = htmlInformativa;
	  }
	  
	  @Override
	  public boolean equals(Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    InformativaConsensi informativaConsensi = (InformativaConsensi) o;
	    return Objects.equals(pdfInformativa, informativaConsensi.pdfInformativa) &&
	        Objects.equals(dataDecorrenza, informativaConsensi.dataDecorrenza)  &&
	        Objects.equals(dataScadenza, informativaConsensi.dataScadenza)  &&
	        Objects.equals(descInformativa, informativaConsensi.descInformativa)  &&
	        Objects.equals(htmlInformativa, informativaConsensi.htmlInformativa) ;
	  }

	  @Override
	  public int hashCode() {
	    return Objects.hash(pdfInformativa, dataDecorrenza, dataScadenza, descInformativa, htmlInformativa);
	  }
	  
	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class InformativaConsensi {\n");
	    sb.append("    pdfInformativa: ").append(toIndentedString(pdfInformativa)).append("\n");
	    sb.append("    dataDecorrenza: ").append(toIndentedString(dataDecorrenza)).append("\n");
	    sb.append("    dataScadenza: ").append(toIndentedString(dataScadenza)).append("\n");
	    sb.append("    descInformativa: ").append(toIndentedString(descInformativa)).append("\n");
	    sb.append("    htmlInformativa: ").append(toIndentedString(htmlInformativa)).append("\n");
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
