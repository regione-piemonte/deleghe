/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.dto.delegheboweb;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.Entita;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Objects;


@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DichiarazioneDettaglio extends Entita {


  private String uuid = null;
  private DichiarazioneDettaglioStato stato = null;
  private Cittadino genitoreTutoreCuratore = null;
  private DichiarazioneRuolo ruoloGenitoreTutoreCuratore = null;
  private Cittadino figlioTutelatoCurato = null;
  private DichiarazioneRuolo ruoloFiglioTutelatoCurato = null;
  private Documento documento = null;
  private String noteRevocaBlocco = null;
  private Integer dicdetId;
  private String motivazioneCasella = "";

  @ApiModelProperty(value = "")
  @JsonProperty("motivazione_casella")
  public String getMotivazioneCasella() {
    return motivazioneCasella;
  }
  public void setMotivazioneCasella(String motivazioneCasella) {
    this.motivazioneCasella = motivazioneCasella;
  }

  @ApiModelProperty(value = "")
  @JsonProperty("dicdet_id")
  public Integer getDicdetId() {
    return dicdetId;
  }
  public void setDicdetId(Integer dicdetId) {
    this.dicdetId = dicdetId;
  }


  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("uuid")

  public String getUuid() {
    return uuid;
  }
  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("stato")

  public DichiarazioneDettaglioStato getStato() {
    return stato;
  }
  public void setStato(DichiarazioneDettaglioStato stato) {
    this.stato = stato;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("genitore_tutore_curatore")

  public Cittadino getGenitoreTutoreCuratore() {
    return genitoreTutoreCuratore;
  }
  public void setGenitoreTutoreCuratore(Cittadino genitoreTutoreCuratore) {
    this.genitoreTutoreCuratore = genitoreTutoreCuratore;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("ruolo_genitore_tutore_curatore")

  public DichiarazioneRuolo getRuoloGenitoreTutoreCuratore() {
    return ruoloGenitoreTutoreCuratore;
  }
  public void setRuoloGenitoreTutoreCuratore(DichiarazioneRuolo ruoloGenitoreTutoreCuratore) {
    this.ruoloGenitoreTutoreCuratore = ruoloGenitoreTutoreCuratore;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("figlio_tutelato_curato")

  public Cittadino getFiglioTutelatoCurato() {
    return figlioTutelatoCurato;
  }
  public void setFiglioTutelatoCurato(Cittadino figlioTutelatoCurato) {
    this.figlioTutelatoCurato = figlioTutelatoCurato;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("ruolo_figlio_tutelato_curato")

  public DichiarazioneRuolo getRuoloFiglioTutelatoCurato() {
    return ruoloFiglioTutelatoCurato;
  }
  public void setRuoloFiglioTutelatoCurato(DichiarazioneRuolo ruoloFiglioTutelatoCurato) {
    this.ruoloFiglioTutelatoCurato = ruoloFiglioTutelatoCurato;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("documento")

  public Documento getDocumento() {
    return documento;
  }
  public void setDocumento(Documento documento) {
    this.documento = documento;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("note_revoca_blocco")

  public String getNoteRevocaBlocco() {
    return noteRevocaBlocco;
  }
  public void setNoteRevocaBlocco(String noteRevocaBlocco) {
    this.noteRevocaBlocco = noteRevocaBlocco;
  }
  

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DichiarazioneDettaglio dichiarazioneDettaglio = (DichiarazioneDettaglio) o;
    return Objects.equals(uuid, dichiarazioneDettaglio.uuid) &&
          Objects.equals(stato, dichiarazioneDettaglio.stato) &&
          Objects.equals(motivazioneCasella, dichiarazioneDettaglio.motivazioneCasella) &&
          Objects.equals(genitoreTutoreCuratore, dichiarazioneDettaglio.genitoreTutoreCuratore) &&
          Objects.equals(ruoloGenitoreTutoreCuratore, dichiarazioneDettaglio.ruoloGenitoreTutoreCuratore) &&
          Objects.equals(figlioTutelatoCurato, dichiarazioneDettaglio.figlioTutelatoCurato) &&
          Objects.equals(ruoloFiglioTutelatoCurato, dichiarazioneDettaglio.ruoloFiglioTutelatoCurato) &&
          Objects.equals(documento, dichiarazioneDettaglio.documento) &&
          Objects.equals(noteRevocaBlocco, dichiarazioneDettaglio.noteRevocaBlocco);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid, motivazioneCasella, stato, genitoreTutoreCuratore, ruoloGenitoreTutoreCuratore, figlioTutelatoCurato, ruoloFiglioTutelatoCurato, documento, noteRevocaBlocco);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DichiarazioneDettaglio {\n");

    sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
    sb.append("    stato: ").append(toIndentedString(stato)).append("\n");
    sb.append("    genitoreTutoreCuratore: ").append(toIndentedString(genitoreTutoreCuratore)).append("\n");
    sb.append("    ruoloGenitoreTutoreCuratore: ").append(toIndentedString(ruoloGenitoreTutoreCuratore)).append("\n");
    sb.append("    motivazioneCasella: ").append(toIndentedString(motivazioneCasella)).append("\n");
    sb.append("    figlioTutelatoCurato: ").append(toIndentedString(figlioTutelatoCurato)).append("\n");
    sb.append("    ruoloFiglioTutelatoCurato: ").append(toIndentedString(ruoloFiglioTutelatoCurato)).append("\n");
    sb.append("    documento: ").append(toIndentedString(documento)).append("\n");
    sb.append("    noteRevocaBlocco: ").append(toIndentedString(noteRevocaBlocco)).append("\n");
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

