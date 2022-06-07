/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.ws.model;

import java.util.Date;

public class DatiDelegaServizioDB {

	private Integer serId;
	private Date dataInizioDelega;
	private Date dataFineDelega;
	private Date dataRevoca;
	private Date dataRinuncia;
	private String uuid;
	private String statoDelega;
	private String tipoDelega;
	private Integer idGradoDelega;
	private String codiceServizio;
	
	public Date getDataInizioDelega() {
		return dataInizioDelega;
	}
	public void setDataInizioDelega(Date dataInizioDelega) {
		this.dataInizioDelega = dataInizioDelega;
	}
	public Date getDataFineDelega() {
		return dataFineDelega;
	}
	public void setDataFineDelega(Date dataFineDelega) {
		this.dataFineDelega = dataFineDelega;
	}
	public Date getDataRevoca() {
		return dataRevoca;
	}
	public void setDataRevoca(Date dataRevoca) {
		this.dataRevoca = dataRevoca;
	}
	public Date getDataRinuncia() {
		return dataRinuncia;
	}
	public void setDataRinuncia(Date dataRinuncia) {
		this.dataRinuncia = dataRinuncia;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getStatoDelega() {
		return statoDelega;
	}
	public void setStatoDelega(String statoDelega) {
		this.statoDelega = statoDelega;
	}
	public String getTipoDelega() {
		return tipoDelega;
	}
	public void setTipoDelega(String tipoDelega) {
		this.tipoDelega = tipoDelega;
	}
	public String getCodiceServizio() {
		return codiceServizio;
	}
	public void setCodiceServizio(String codiceServizio) {
		this.codiceServizio = codiceServizio;
	}
	public Integer getSerId() {
		return serId;
	}
	public void setSerId(Integer serId) {
		this.serId = serId;
	}
	public Integer getIdGradoDelega() {
		return idGradoDelega;
	}
	public void setIdGradoDelega(Integer idGradoDelega) {
		this.idGradoDelega = idGradoDelega;
	}
	
	
	
}
