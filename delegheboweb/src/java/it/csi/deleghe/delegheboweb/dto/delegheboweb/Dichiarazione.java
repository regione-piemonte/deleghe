/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.dto.delegheboweb;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Dichiarazione {


   private String uuid = null;
   private DichiarazioneTipo tipo = null;
   private DichiarazioneStato stato = null;
   private DichiarazioneModo modo = null;
   private Cittadino cittadino = null;
   private Date dataInserimento = null;
   private List<DichiarazioneDettaglio> dettagli = new ArrayList<DichiarazioneDettaglio>();
   private String compilatoreCF;
   private Integer nPratica;
   private String fullCompilatoreCF;

   private String numeroDocumento;
   private String autorita;
   private Date dataInizioTutela = null;
   private Date dataFineTutela = null;
   private String statoServizioDelega = null;
   private String provenienza = null;

   @ApiModelProperty(value = "")
   @JsonProperty("full_compilatore_cf")
   public String getFullCompilatoreCF() {
      return fullCompilatoreCF;
   }
   public void setFullCompilatoreCF(String fullCompilatoreCF) {
      this.fullCompilatoreCF = fullCompilatoreCF;
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
   @JsonProperty("npratica")
   public Integer getNPratica() {
      return nPratica;
   }
   public void setNPratica(Integer nPratica) {
      this.nPratica = nPratica;
   }

   @ApiModelProperty(value = "")
   @JsonProperty("compilatoreCF")
   public String getCompilatoreCF() {
      return compilatoreCF;
   }
   public void setCompilatoreCF(String compilatoreCF) {
      this.compilatoreCF = compilatoreCF;
   }

   @ApiModelProperty(value = "")
   @JsonProperty("tipo")
   public DichiarazioneTipo getTipo() {
      return tipo;
   }
   public void setTipo(DichiarazioneTipo tipo) {
      this.tipo = tipo;
   }

   @ApiModelProperty(value = "")
   @JsonProperty("stato")
   public DichiarazioneStato getStato() {
      return stato;
   }
   public void setStato(DichiarazioneStato stato) {
      this.stato = stato;
   }

   @ApiModelProperty(value = "")
   @JsonProperty("modo")
   public DichiarazioneModo getModo() {
      return modo;
   }

   public void setModo(DichiarazioneModo modo) {
      this.modo = modo;
   }

   /**
    **/

   @ApiModelProperty(value = "")
   @JsonProperty("cittadino")
   public Cittadino getCittadino() {
      return cittadino;
   }
   public void setCittadino(Cittadino cittadino) {
      this.cittadino = cittadino;
   }

   /**
    **/

   @ApiModelProperty(value = "")
   @JsonProperty("data_inserimento")
   public Date getDataInserimento() {
      return dataInserimento;
   }
   public void setDataInserimento(Date dataInserimento) {
      this.dataInserimento = dataInserimento;
   }

   /**
    **/

   @ApiModelProperty(value = "")
   @JsonProperty("dettagli")
   public List<DichiarazioneDettaglio> getDettagli() {
      return dettagli;
   }
   public void setDettagli(List<DichiarazioneDettaglio> dettagli) {
      this.dettagli = dettagli;
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
   @JsonProperty("data_inizio_tutela")
   public Date getDataInizioTutela() {
	   return dataInizioTutela;
   }
   public void setDataInizioTutela(Date dataInizioTutela) {
	   this.dataInizioTutela = dataInizioTutela;
   }
   
   /**
    **/

   @ApiModelProperty(value = "")
   @JsonProperty("data_fine_tutela")
   public Date getDataFineTutela() {
	   return dataFineTutela;
   }
   public void setDataFineTutela(Date dataFineTutela) {
	   this.dataFineTutela = dataFineTutela;
   }

   
   /**
    **/

   @ApiModelProperty(value = "")
   @JsonProperty("stato_servizio_delega")
   public String getStatoServizioDelega() {
		return statoServizioDelega;
   }
   public void setStatoServizioDelega(String statoServizioDelega) {
	   this.statoServizioDelega = statoServizioDelega;
   }
   
   /**
    **/

   @ApiModelProperty(value = "")
   @JsonProperty("provenienza")
   public String getProvenienza() {
		return provenienza;
	}
	public void setProvenienza(String provenienza) {
		this.provenienza = provenienza;
	}

  


   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      }
      if (o == null || getClass() != o.getClass()) {
         return false;
      }
      Dichiarazione dichiarazione = (Dichiarazione) o;
      return Objects.equals(uuid, dichiarazione.uuid) &&
            Objects.equals(tipo, dichiarazione.tipo) &&
            Objects.equals(fullCompilatoreCF, dichiarazione.fullCompilatoreCF) &&
            Objects.equals(stato, dichiarazione.stato) &&
            Objects.equals(modo, dichiarazione.modo) &&
            Objects.equals(cittadino, dichiarazione.cittadino) &&
            Objects.equals(dataInserimento, dichiarazione.dataInserimento) &&
            Objects.equals(dettagli, dichiarazione.dettagli) &&
            Objects.equals(numeroDocumento, dichiarazione.numeroDocumento) &&
            Objects.equals(autorita, dichiarazione.autorita) &&
            Objects.equals(dataInizioTutela, dichiarazione.dataInizioTutela) &&
            Objects.equals(dataFineTutela, dichiarazione.dataFineTutela) &&
            Objects.equals(statoServizioDelega, dichiarazione.statoServizioDelega) &&
            Objects.equals(provenienza, dichiarazione.provenienza);
   }

   @Override
   public int hashCode() {
      return Objects.hash(fullCompilatoreCF, uuid, tipo, stato, modo, cittadino, dataInserimento, dettagli, numeroDocumento, autorita, dataInizioTutela, dataFineTutela, statoServizioDelega, provenienza);
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("class Dichiarazione {\n");

      sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
      sb.append("    tipo: ").append(toIndentedString(tipo)).append("\n");
      sb.append("    stato: ").append(toIndentedString(stato)).append("\n");
      sb.append("    fullCompilatoreCF: ").append(toIndentedString(fullCompilatoreCF)).append("\n");
      sb.append("    modo: ").append(toIndentedString(modo)).append("\n");
      sb.append("    cittadino: ").append(toIndentedString(cittadino)).append("\n");
      sb.append("    dataInserimento: ").append(toIndentedString(dataInserimento)).append("\n");
      sb.append("    dettagli: ").append(toIndentedString(dettagli)).append("\n");
      sb.append("    numeroDocumento: ").append(toIndentedString(numeroDocumento)).append("\n");
      sb.append("    autorita: ").append(toIndentedString(autorita)).append("\n");
      sb.append("    dataInizioTutela: ").append(toIndentedString(dataInizioTutela)).append("\n");
      sb.append("    dataFineTutela: ").append(toIndentedString(dataFineTutela)).append("\n");
      sb.append("    statoServizioDelega: ").append(toIndentedString(statoServizioDelega)).append("\n");
      sb.append("    provenienza: ").append(toIndentedString(provenienza)).append("\n");
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

