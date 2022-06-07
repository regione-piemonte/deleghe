/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.deleghe.deleghebe.ws.msg;

import javax.xml.bind.annotation.XmlType;

import it.csi.deleghe.deleghebe.ws.model.Cittadino;
import it.csi.deleghe.deleghebe.ws.model.DichiarazioneRuolo;

@XmlType
public class ElencoGenitori extends ServiceRequest {
	private Cittadino cittadino;
	

	public Cittadino getCittadino() {
		return cittadino;
	}
	
	private DichiarazioneRuolo dichiarazioneRuolo;
	

	public void setCittadino(Cittadino cittadino) {
		this.cittadino = cittadino;
	}

	public DichiarazioneRuolo getDichiarazioneRuolo() {
		return dichiarazioneRuolo;
	}

	public void setDichiarazioneRuolo(DichiarazioneRuolo dichiarazioneRuolo) {
		this.dichiarazioneRuolo = dichiarazioneRuolo;
	}
	
	

}
