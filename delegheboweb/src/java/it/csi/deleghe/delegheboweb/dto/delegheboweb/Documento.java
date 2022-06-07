/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.dto.delegheboweb;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.Entita;

import java.util.Date;
import java.util.Objects;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Documento extends Entita {
  
  private Integer id = null;
  private Integer fileId = null;
  private String desc = null;
  private DocumentoTipo tipo = null;
  private DocumentoFile file = null;
  private Date dataScadenzaDoc = null;
  

  private Date dataRilascio;
  private String docDesc;
  private String numeroDocumento;
  private String autorita;
  
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
  
  
  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("data_scadenza_doc")
  public Date getDataScadenzaDoc() {
		return dataScadenzaDoc;
  }
  public void setDataScadenzaDoc(Date dataScadenzaDoc) {
	this.dataScadenzaDoc = dataScadenzaDoc;
  }

  
  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("data_rilascio")
  public Date getDataRilascio() {
		return dataRilascio;
  }
  public void setDataRilascio(Date dataRilascio) {
	this.dataRilascio = dataRilascio;
  }

  
  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("doc_desc")
  public String getDocDesc() {
	return docDesc;
  }
  public void setDocDesc(String docDesc) {
	this.docDesc = docDesc;
  }

  
  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("numero_documento")
  public String getNumeroDocumento() {
	return numeroDocumento;
  }
  public void setNumeroDocumento(String numeroDocumento) {
	this.numeroDocumento = numeroDocumento;
  }

  
  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("autorita")
  public String getAutorita() {
	return autorita;
  }
  public void setAutorita(String autorita) {
	this.autorita = autorita;
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
    Documento documento = (Documento) o;
    return Objects.equals(id, documento.id) &&
        Objects.equals(desc, documento.desc) &&
        Objects.equals(tipo, documento.tipo) &&
        Objects.equals(file, documento.file) &&
        Objects.equals(dataScadenzaDoc, documento.dataScadenzaDoc) &&
        Objects.equals(dataRilascio, documento.dataRilascio) &&
        Objects.equals(docDesc, documento.docDesc) &&
        Objects.equals(numeroDocumento, documento.numeroDocumento) &&
        Objects.equals(autorita, documento.autorita) &&
        Objects.equals(fileId, documento.fileId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, desc, tipo, file,dataScadenzaDoc, dataRilascio, docDesc, numeroDocumento, autorita, fileId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Documento {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    desc: ").append(toIndentedString(desc)).append("\n");
    sb.append("    tipo: ").append(toIndentedString(tipo)).append("\n");
    sb.append("    file: ").append(toIndentedString(file)).append("\n");
    sb.append("    dataScadenzaDoc: ").append(toIndentedString(dataScadenzaDoc)).append("\n");
    sb.append("    dataRilascio: ").append(toIndentedString(dataRilascio)).append("\n");
    sb.append("    docDesc: ").append(toIndentedString(docDesc)).append("\n");
    sb.append("    numeroDocumento: ").append(toIndentedString(numeroDocumento)).append("\n");
    sb.append("    autorita: ").append(toIndentedString(autorita)).append("\n");
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

