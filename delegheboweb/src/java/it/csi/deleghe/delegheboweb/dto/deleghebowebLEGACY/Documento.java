/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.dto.deleghebowebLEGACY;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Objects;


@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Documento   {
  // verra' utilizzata la seguente strategia serializzazione degli attributi: [explicit-as-modeled] 
  
  private Integer id = null;
  private String desc = null;
  private DocumentoTipo tipo = null;
  private DocumentoFile file = null;

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("id")
 
  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("desc")
 
  public String getDesc() {
    return desc;
  }
  public void setDesc(String desc) {
    this.desc = desc;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("tipo")
 
  public DocumentoTipo getTipo() {
    return tipo;
  }
  public void setTipo(DocumentoTipo tipo) {
    this.tipo = tipo;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("file")
 
  public DocumentoFile getFile() {
    return file;
  }
  public void setFile(DocumentoFile file) {
    this.file = file;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Documento documento = (Documento) o;
    return Objects.equals(id, documento.id) &&
        Objects.equals(desc, documento.desc) &&
        Objects.equals(tipo, documento.tipo) &&
        Objects.equals(file, documento.file);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, desc, tipo, file);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Documento {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    desc: ").append(toIndentedString(desc)).append("\n");
    sb.append("    tipo: ").append(toIndentedString(tipo)).append("\n");
    sb.append("    file: ").append(toIndentedString(file)).append("\n");
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

