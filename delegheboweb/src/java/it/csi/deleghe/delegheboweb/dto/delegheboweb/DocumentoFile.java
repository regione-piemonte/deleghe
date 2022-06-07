/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.dto.delegheboweb;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DocumentoFile extends Entita {
  
  private String nome = null;
  private Integer dimensioneInBytes = null;
  private String base64File = null;
  private byte[] bytes = null;
  private Integer fileId = null;
  

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("nome")
  public String getNome() {
    return nome;
  }
  public void setNome(String nome) {
    this.nome = nome;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("dimensione_in_bytes")
  public Integer getDimensioneInBytes() {
    return dimensioneInBytes;
  }
  public void setDimensioneInBytes(Integer dimensioneInBytes) {
    this.dimensioneInBytes = dimensioneInBytes;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("base_64_file")
  public String getBase64File() {
    return base64File;
  }
  public void setBase64File(String base64File) {
    this.base64File = base64File;
  }
  
  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("bytes")
  public byte[] getBytes() {
		return bytes;
  }
  public void setBytes(byte[] bytes) {
	this.bytes = bytes;
  }
  
  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("file_id")
  public Integer getFileId() {
		return fileId;
	}
	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DocumentoFile documentoFile = (DocumentoFile) o;
    return Objects.equals(nome, documentoFile.nome) &&
        Objects.equals(dimensioneInBytes, documentoFile.dimensioneInBytes) &&
        Objects.equals(base64File, documentoFile.base64File) &&
        Objects.equals(bytes, documentoFile.bytes)  &&
        Objects.equals(fileId, documentoFile.fileId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nome, dimensioneInBytes, base64File, bytes, fileId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DocumentoFile {\n");
    
    sb.append("    nome: ").append(toIndentedString(nome)).append("\n");
    sb.append("    dimensioneInBytes: ").append(toIndentedString(dimensioneInBytes)).append("\n");
    sb.append("    base64File: ").append(toIndentedString(base64File)).append("\n");
    sb.append("    bytes: ").append(toIndentedString(bytes)).append("\n");
    sb.append("    fileId: ").append(toIndentedString(fileId)).append("\n");
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

