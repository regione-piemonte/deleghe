/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.dto.delegheboweb;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import org.codehaus.jackson.annotate.JsonProperty;
//import it.csi.deleghe.delegheboweb.dto.deleghebowebLEGACY.DelegaServizio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Delega   {

  private Integer numeroPratica;
  private Integer ciIdDelegato;	
  private Integer citIdDelegante;
  private String uuid;
  private Cittadino delegante;
  private Cittadino delegato;
  private String motivazioneMenu;
  private String motivazioneCasella;
  private List<DelegaServizio> servizi = new ArrayList<>();
  private DichiarazioneDettaglio dichiarazioneDettaglio;
  private String compilatoreCF;
  private String tipoDelega;
  private String statoDelega;
  private String fullCompilatoreCF;
  private Boolean presavisione;
  private Boolean bloccaDelega;
  private Date richiesta;
  
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
  @JsonProperty("richiesta")
  public Date getRichiesta() {
    return richiesta;
  }
  public void setRichiesta(Date richiesta) {
    this.richiesta = richiesta;
  }

  @ApiModelProperty(value = "")
  @JsonProperty("blocca_delega")
  public Boolean getBloccaDelega() {
    return bloccaDelega;
  }
  public void setBloccaDelega(Boolean bloccaDelega) {
    this.bloccaDelega = bloccaDelega;
  }

  @ApiModelProperty(value = "")
  @JsonProperty("presavisione")
  public Boolean getPresavisione() {
    return presavisione;
  }
  public void setPresavisione(Boolean presavisione) {
    this.presavisione = presavisione;
  }

  @ApiModelProperty(value = "")
  @JsonProperty("full_compilatore_cf")
  public String getFullCompilatoreCF() {
    return fullCompilatoreCF;
  }
  public void setFullCompilatoreCF(String fullCompilatoreCF) {
    this.fullCompilatoreCF = fullCompilatoreCF;
  }
  
  @ApiModelProperty(value = "")
  @JsonProperty("stato_delega")
  public String getStatoDelega() {
    return statoDelega;
  }
  public void setStatoDelega(String statoDelega) {
    this.statoDelega = statoDelega;
  }

  @ApiModelProperty(value = "")
  @JsonProperty("tipo_delega")
  public String getTipoDelega() {
    return tipoDelega;
  }
  public void setTipoDelega(String tipoDelega) {
    this.tipoDelega = tipoDelega;
  }

  @ApiModelProperty(value = "")
  @JsonProperty("compilatore_cf")
  public String getCompilatoreCF() {
    return compilatoreCF;
  }
  public void setCompilatoreCF(String compilatoreCF) {
    this.compilatoreCF = compilatoreCF;
  }

  @ApiModelProperty(value = "")
  @JsonProperty("dichiarazione_dettaglio")
  public DichiarazioneDettaglio getDichiarazioneDettaglio() {
    return dichiarazioneDettaglio;
  }
  public void setDichiarazioneDettaglio(DichiarazioneDettaglio dichiarazioneDettaglio) {
    this.dichiarazioneDettaglio = dichiarazioneDettaglio;
  }

  @ApiModelProperty(value = "")
  @JsonProperty("motivazione_menu")
  public String getMotivazioneMenu() {
    return motivazioneMenu;
  }
  public void setMotivazioneMenu(String motivazioneMenu) {
    this.motivazioneMenu = motivazioneMenu;
  }

  @ApiModelProperty(value = "")
  @JsonProperty("motivazione_casella")
  public String getMotivazioneCasella() {
    return motivazioneCasella;
  }
  public void setMotivazioneCasella(String motivazioneCasella) {
    this.motivazioneCasella = motivazioneCasella;
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
  @JsonProperty("delegante")
  public Cittadino getDelegante() {
    return delegante;
  }
  public void setDelegante(Cittadino delegante) {
    this.delegante = delegante;
  }
  
  @ApiModelProperty(value = "")
  @JsonProperty("delegato")
  public Cittadino getDelegato() {return delegato;}
  public void setDelegato(Cittadino delegato) {
    this.delegato = delegato;
  }

  @ApiModelProperty(value = "")
  @JsonProperty("servizi")
  public List<DelegaServizio> getServizi() {
    return servizi;
  }
  public void setServizi(List<DelegaServizio> servizi) {
    this.servizi = servizi;
  }
  
  @ApiModelProperty(value = "")
  @JsonProperty("numero_pratica")
  public Integer getNumeroPratica() {
	return numeroPratica;
  }
  public void setNumeroPratica(Integer numeroPratica) {
	this.numeroPratica = numeroPratica;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Delega delega = (Delega) o;
    return Objects.equals(uuid, delega.uuid) &&
        Objects.equals(presavisione, delega.presavisione) &&
        Objects.equals(statoDelega, delega.statoDelega) &&
        Objects.equals(fullCompilatoreCF, delega.fullCompilatoreCF) &&
        Objects.equals(richiesta, delega.richiesta) &&
        Objects.equals(tipoDelega, delega.tipoDelega) &&
        Objects.equals(compilatoreCF, delega.compilatoreCF) &&
        Objects.equals(dichiarazioneDettaglio, delega.dichiarazioneDettaglio) &&
        Objects.equals(delegante, delega.delegante) &&
        Objects.equals(motivazioneCasella, delega.motivazioneCasella) &&
        Objects.equals(motivazioneMenu, delega.motivazioneMenu) &&
        Objects.equals(delegato, delega.delegato) &&
        Objects.equals(servizi, delega.servizi) &&
        Objects.equals(numeroPratica, delega.numeroPratica);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid, delegante, delegato, servizi, numeroPratica);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Delega {\n");
    
    sb.append("    dichiarazioneDettaglio: ").append(toIndentedString(dichiarazioneDettaglio)).append("\n");
    sb.append("    compilatoreCF: ").append(toIndentedString(compilatoreCF)).append("\n");
    sb.append("    tipoDelega: ").append(toIndentedString(tipoDelega)).append("\n");
    sb.append("    richiesta: ").append(toIndentedString(richiesta)).append("\n");
    sb.append("    statoDelega: ").append(toIndentedString(statoDelega)).append("\n");
    sb.append("    presavisione: ").append(toIndentedString(presavisione)).append("\n");
    sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
    sb.append("    delegante: ").append(toIndentedString(delegante)).append("\n");
    sb.append("    fullCompilatoreCF: ").append(toIndentedString(fullCompilatoreCF)).append("\n");
    sb.append("    delegato: ").append(toIndentedString(delegato)).append("\n");
    sb.append("    motivazioneMenu: ").append(toIndentedString(motivazioneMenu)).append("\n");
    sb.append("    motivazioneCasella: ").append(toIndentedString(motivazioneCasella)).append("\n");
    sb.append("    servizi: ").append(toIndentedString(servizi)).append("\n");
    sb.append("    numeroPratica: ").append(toIndentedString(numeroPratica)).append("\n");
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

