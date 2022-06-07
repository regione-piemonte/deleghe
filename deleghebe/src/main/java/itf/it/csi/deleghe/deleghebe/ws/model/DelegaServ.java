/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.deleghe.deleghebe.ws.model;

import java.util.Date;
import java.util.UUID;

import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per DelegaServ complex type.
 * 
 * 
 */
@XmlType
public class DelegaServ extends Entita {


	
	protected Delega delega;
	
	protected Servizio servizio;
	
	

    protected UUID uuid;
    
    

    @XmlSchemaType(name = "dateTime")
    protected Date dataDecorrenza;
    

    @XmlSchemaType(name = "dateTime")
    protected Date dataScadenza;
    

    @XmlSchemaType(name = "dateTime")
    protected Date dataRevoca;
    

    @XmlSchemaType(name = "dateTime")
    protected Date dataRinuncia;
    
    protected DelegaServStato stato;

    protected String tipoDelega;
    protected String gradoDelega;


	public Delega getDelega() {
		return delega;
	}

	public void setDelega(Delega delega) {
		this.delega = delega;
	}

	public Servizio getServizio() {
		return servizio;
	}

	public void setServizio(Servizio servizio) {
		this.servizio = servizio;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	@XmlTransient
	public Date getDataDecorrenza() {
		return dataDecorrenza;
	}

	public void setDataDecorrenza(Date dataDecorrenza) {
		this.dataDecorrenza = dataDecorrenza;
	}

	@XmlTransient
	public Date getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	@XmlTransient
	public Date getDataRevoca() {
		return dataRevoca;
	}

	public void setDataRevoca(Date dataRevoca) {
		this.dataRevoca = dataRevoca;
	}

	@XmlTransient
	public Date getDataRinuncia() {
		return dataRinuncia;
	}

	public void setDataRinuncia(Date dataRinuncia) {
		this.dataRinuncia = dataRinuncia;
	}

	public DelegaServStato getStato() {
		return stato;
	}

	public void setStato(DelegaServStato stato) {
		this.stato = stato;
	}

	@Override
	public boolean equals(Object obj) {
		boolean res=false;
		if(obj != null && obj instanceof DelegaServ ) {
			res=this.getServizio().getCodice().equals(((DelegaServ)obj).getServizio().getCodice());
		}
		return res;
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	public String getTipoDelega() {
		return tipoDelega;
	}

	public void setTipoDelega(String tipoDelega) {
		this.tipoDelega = tipoDelega;
	}

	public String getGradoDelega() {
		return gradoDelega;
	}

	public void setGradoDelega(String gradoDelega) {
		this.gradoDelega = gradoDelega;
	}
}
