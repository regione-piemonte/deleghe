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
import java.util.Date;
import java.util.List;
import java.util.Objects;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DelegheFilter {
  // verra' utilizzata la seguente strategia serializzazione degli attributi: [explicit-as-modeled] 
  
  private Integer ciIdDelegato = null;
  private Integer citIdDelegante = null;
  private String uuid = null;
  private String delegatoCodiceFiscale = null;
  private String deleganteCodiceFiscale = null;
  private DichiarazioneRuolo ruolo = null;
  private Date dataInserimentoDa = null;
  private Date dataInserimentoA = null;
  private Date dataValiditaInizio = null;
  private Date dataValiditaFine = null;
  private List<DichiarazioneTipo> tipo = new ArrayList<DichiarazioneTipo>();
  private List<DichiarazioneStato> stato = new ArrayList<DichiarazioneStato>();
  
  @ApiModelProperty(value = "")
  @JsonProperty("cit_id_delegato")
  public Integer getCiIdDelegato() {
	return ciIdDelegato;
  }
  public void setCiIdDelegato(Integer ciIdDelegato) {
	this.ciIdDelegato = ciIdDelegato;
  }
	
  @ApiModelProperty(value = "")
  @JsonProperty("cit_id_delegante")
  public Integer getCitIdDelegante() {
	return citIdDelegante;
  }
  public void setCitIdDelegante(Integer citIdDelegante) {
	this.citIdDelegante = citIdDelegante;
  }

  @ApiModelProperty(value = "")
  @JsonProperty("uuid")
  public String getUuid() {
    return uuid;
  }
  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  @ApiModelProperty(value = "")
  @JsonProperty("data_validita_inizio")
  public Date getDataValiditaInizio() {
    return dataValiditaInizio;
  }
  public void setDataValiditaInizio(Date dataValiditaInizio) {
    this.dataValiditaInizio = dataValiditaInizio;
  }

  @ApiModelProperty(value = "")
  @JsonProperty("data_validita_fine")
  public Date getDataValiditaFine() {
    return dataValiditaFine;
  }
  public void setDataValiditaFine(Date dataValiditaFine) {
    this.dataValiditaFine = dataValiditaFine;
  }

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
  @JsonProperty("ruolo")
  public DichiarazioneRuolo getRuolo() {
    return ruolo;
  }
  public void setRuolo(DichiarazioneRuolo ruolo) {
    this.ruolo = ruolo;
  }

  @ApiModelProperty(value = "")
  @JsonProperty("delegato_codice_fiscale")
  public String getDelegatoCodiceFiscale() {
    return delegatoCodiceFiscale;
  }
  public void setDelegatoCodiceFiscale(String delegatoCodiceFiscale) {
    this.delegatoCodiceFiscale = delegatoCodiceFiscale;
  }

  @ApiModelProperty(value = "")
  @JsonProperty("delegante_codice_fiscale")
  public String getDeleganteCodiceFiscale() {
    return deleganteCodiceFiscale;
  }
  public void setDeleganteCodiceFiscale(String deleganteCodiceFiscale) {
    this.deleganteCodiceFiscale = deleganteCodiceFiscale;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("tipo")
  public List<DichiarazioneTipo> getTipo() {return tipo;}
  public void setTipo(List<DichiarazioneTipo> tipo) {
    this.tipo = tipo;
  }

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
    DelegheFilter dichiarazioneFilter = (DelegheFilter) o;
    return Objects.equals(ciIdDelegato, dichiarazioneFilter.ciIdDelegato) &&
    	Objects.equals(citIdDelegante, dichiarazioneFilter.citIdDelegante) &&
    	Objects.equals(uuid, dichiarazioneFilter.uuid) &&
        Objects.equals(deleganteCodiceFiscale, dichiarazioneFilter.deleganteCodiceFiscale) &&
        Objects.equals(delegatoCodiceFiscale, dichiarazioneFilter.delegatoCodiceFiscale) &&
        Objects.equals(ruolo, dichiarazioneFilter.ruolo) &&
        Objects.equals(tipo, dichiarazioneFilter.tipo) &&
        Objects.equals(stato, dichiarazioneFilter.stato) &&
        Objects.equals(dataInserimentoDa, dichiarazioneFilter.dataInserimentoDa) &&
        Objects.equals(dataValiditaFine, dichiarazioneFilter.dataValiditaFine) &&
        Objects.equals(dataValiditaInizio, dichiarazioneFilter.dataValiditaInizio) &&
        Objects.equals(dataInserimentoA, dichiarazioneFilter.dataInserimentoA);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ciIdDelegato, citIdDelegante, uuid, deleganteCodiceFiscale, delegatoCodiceFiscale, ruolo, tipo, stato, dataValiditaFine, dataValiditaInizio, dataInserimentoDa, dataInserimentoA);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DichiarazioneFilter {\n");
    
    sb.append("    ciIdDelegato: ").append(toIndentedString(ciIdDelegato)).append("\n");
    sb.append("    citIdDelegante: ").append(toIndentedString(citIdDelegante)).append("\n");
    sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
    sb.append("    deleganteCodiceFiscale: ").append(toIndentedString(deleganteCodiceFiscale)).append("\n");
    sb.append("    delegatoCodiceFiscale: ").append(toIndentedString(delegatoCodiceFiscale)).append("\n");
    sb.append("    ruolo: ").append(toIndentedString(ruolo)).append("\n");
    sb.append("    dataInserimentoDa: ").append(toIndentedString(dataInserimentoDa)).append("\n");
    sb.append("    dataValiditaFine: ").append(toIndentedString(dataValiditaFine)).append("\n");
    sb.append("    dataValiditaInizio: ").append(toIndentedString(dataValiditaInizio)).append("\n");
    sb.append("    dataInserimentoA: ").append(toIndentedString(dataInserimentoA)).append("\n");
    sb.append("    tipo: ").append(toIndentedString(tipo)).append("\n");
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

