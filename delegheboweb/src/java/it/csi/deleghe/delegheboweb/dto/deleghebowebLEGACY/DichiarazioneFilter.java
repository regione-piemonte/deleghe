/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.dto.deleghebowebLEGACY;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Date;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DichiarazioneFilter   {
  // verra' utilizzata la seguente strategia serializzazione degli attributi: [explicit-as-modeled] 
  
  private StringCrit uuid = null;
  private StringCrit codiceFiscaleCit1 = null;
  private DichiarazioneRuolo ruoloCit1 = null;
  private StringCrit codiceFiscaleCit2 = null;
  private DichiarazioneRuolo ruoloCit2 = null;
  private Date dataInserimentoDa = null;
  private Date dataInserimentoA = null;
  private List<DichiarazioneTipo> tipo = new ArrayList<DichiarazioneTipo>();
  private List<DichiarazioneModo> modo = new ArrayList<DichiarazioneModo>();
  private List<DichiarazioneStato> stato = new ArrayList<DichiarazioneStato>();

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("data_inserimento_da")
  public Date getDataInserimentoDa() {
    return dataInserimentoDa;
  }
  public void setDataInserimentoDa(Date dataInserimentoDa) {
    this.dataInserimentoDa = dataInserimentoDa;
  }

  @ApiModelProperty(value = "")
  @JsonProperty("data_inserimento_a")
  public Date getDataInserimentoA() {
    return dataInserimentoA;
  }
  public void setDataInserimentoA(Date dataInserimentoA) {
    this.dataInserimentoA = dataInserimentoA;
  }

  @ApiModelProperty(value = "")
  @JsonProperty("uuid")
  public StringCrit getUuid() {
    return uuid;
  }
  public void setUuid(StringCrit uuid) {
    this.uuid = uuid;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("codice_fiscale_cit1")
 
  public StringCrit getCodiceFiscaleCit1() {
    return codiceFiscaleCit1;
  }
  public void setCodiceFiscaleCit1(StringCrit codiceFiscaleCit1) {
    this.codiceFiscaleCit1 = codiceFiscaleCit1;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("ruolo_cit1")
 
  public DichiarazioneRuolo getRuoloCit1() {
    return ruoloCit1;
  }
  public void setRuoloCit1(DichiarazioneRuolo ruoloCit1) {
    this.ruoloCit1 = ruoloCit1;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("codice_fiscale_cit2")
 
  public StringCrit getCodiceFiscaleCit2() {
    return codiceFiscaleCit2;
  }
  public void setCodiceFiscaleCit2(StringCrit codiceFiscaleCit2) {
    this.codiceFiscaleCit2 = codiceFiscaleCit2;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("ruolo_cit2")
 
  public DichiarazioneRuolo getRuoloCit2() {
    return ruoloCit2;
  }
  public void setRuoloCit2(DichiarazioneRuolo ruoloCit2) {
    this.ruoloCit2 = ruoloCit2;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("tipo")
 
  public List<DichiarazioneTipo> getTipo() {
    return tipo;
  }
  public void setTipo(List<DichiarazioneTipo> tipo) {
    this.tipo = tipo;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("modo")
 
  public List<DichiarazioneModo> getModo() {
    return modo;
  }
  public void setModo(List<DichiarazioneModo> modo) {
    this.modo = modo;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("stato")
 
  public List<DichiarazioneStato> getStato() {
    return stato;
  }
  public void setStato(List<DichiarazioneStato> stato) {
    this.stato = stato;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DichiarazioneFilter dichiarazioneFilter = (DichiarazioneFilter) o;
    return Objects.equals(uuid, dichiarazioneFilter.uuid) &&
        Objects.equals(codiceFiscaleCit1, dichiarazioneFilter.codiceFiscaleCit1) &&
        Objects.equals(ruoloCit1, dichiarazioneFilter.ruoloCit1) &&
        Objects.equals(codiceFiscaleCit2, dichiarazioneFilter.codiceFiscaleCit2) &&
        Objects.equals(ruoloCit2, dichiarazioneFilter.ruoloCit2) &&
        Objects.equals(tipo, dichiarazioneFilter.tipo) &&
        Objects.equals(modo, dichiarazioneFilter.modo) &&
        Objects.equals(stato, dichiarazioneFilter.stato) &&
        Objects.equals(dataInserimentoDa, dichiarazioneFilter.dataInserimentoDa) &&
        Objects.equals(dataInserimentoA, dichiarazioneFilter.dataInserimentoA);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid, codiceFiscaleCit1, ruoloCit1, codiceFiscaleCit2, ruoloCit2, tipo, modo, stato, dataInserimentoDa, dataInserimentoA);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DichiarazioneFilter {\n");
    
    sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
    sb.append("    codiceFiscaleCit1: ").append(toIndentedString(codiceFiscaleCit1)).append("\n");
    sb.append("    ruoloCit1: ").append(toIndentedString(ruoloCit1)).append("\n");
    sb.append("    codiceFiscaleCit2: ").append(toIndentedString(codiceFiscaleCit2)).append("\n");
    sb.append("    ruoloCit2: ").append(toIndentedString(ruoloCit2)).append("\n");
    sb.append("    dataInserimentoDa: ").append(toIndentedString(dataInserimentoDa)).append("\n");
    sb.append("    dataInserimentoA: ").append(toIndentedString(dataInserimentoA)).append("\n");
    sb.append("    tipo: ").append(toIndentedString(tipo)).append("\n");
    sb.append("    modo: ").append(toIndentedString(modo)).append("\n");
    sb.append("    stato: ").append(toIndentedString(stato)).append("\n");
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

