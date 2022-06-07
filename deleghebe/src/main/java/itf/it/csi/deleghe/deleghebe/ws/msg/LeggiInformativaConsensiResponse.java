/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.ws.msg;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import it.csi.deleghe.deleghebe.ws.model.InformativaConsensi;

@XmlType
public class LeggiInformativaConsensiResponse extends ServiceResponse {
	
	@XmlElementWrapper
	@XmlElement(name="informativaConsensi")
	private List<InformativaConsensi> elencoInformativaConsensi;

	public List<InformativaConsensi> getElencoInformativaConsensi() {
		return elencoInformativaConsensi;
	}

	public void setElencoInformativaConsensi(List<InformativaConsensi> elencoInformativaConsensi) {
		this.elencoInformativaConsensi = elencoInformativaConsensi;
	}

}
