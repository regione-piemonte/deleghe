/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.business.mapper;

import java.util.ArrayList;
import java.util.UUID;

import it.csi.deleghe.deleghebe.ws.model.RuoloOperazione;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.Dichiarazione;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.DichiarazioneDettaglio;

public class DichiarazioneMapper extends BaseMapper<Dichiarazione, it.csi.deleghe.deleghebe.ws.model.Dichiarazione> {

	@Override
	public it.csi.deleghe.deleghebe.ws.model.Dichiarazione to(Dichiarazione source) {
		if(source==null) {
			return null;
		}
		it.csi.deleghe.deleghebe.ws.model.Dichiarazione result = new it.csi.deleghe.deleghebe.ws.model.Dichiarazione();

		final String uuid = source.getUuid();
		if (uuid != null) result.setUuid(UUID.fromString(uuid));
		result.setStato(new DichiarazioneStatoMapper().to(source.getStato()));
		result.setModo(new DichiarazioneModoMapper().to(source.getModo()));
		result.setTipo(new DichiarazioneTipoMapper().to(source.getTipo()));
		result.setDataCreazione(source.getDataInserimento());
		result.setCompilatoreCF(source.getCompilatoreCF());
		result.setCittadino(new CittadinoMapper().to(source.getCittadino()));
		result.setDataCreazione(source.getDataInserimento());
		result.setnPratica(source.getNPratica());
		final RuoloOperazione ruoloOperazione = new RuoloOperazione();
		ruoloOperazione.setCodice("CITTADINO");
		result.setRuoloOperazione(ruoloOperazione);

		result.setNumeroDocumento(source.getNumeroDocumento());
		result.setAutorita(source.getAutorita());
		result.setDataInizioTutela(source.getDataInizioTutela());
		result.setDataFineTutela(source.getDataFineTutela());		

		result.setDettagli(new ArrayList<it.csi.deleghe.deleghebe.ws.model.DichiarazioneDettaglio>());
		for(DichiarazioneDettaglio dettaglio : source.getDettagli()){
			result.getDettagli().add(new DichiarazioneDettaglioMapper().to(dettaglio));
		}
		
		return result;
	}

	@Override
	public Dichiarazione from(it.csi.deleghe.deleghebe.ws.model.Dichiarazione dest) {
		if(dest==null) {
			return null;
		}
		Dichiarazione result = new Dichiarazione();

		final UUID uuid = dest.getUuid();
		if (uuid != null) result.setUuid(uuid.toString());
		result.setStato(new DichiarazioneStatoMapper().from(dest.getStato()));
		result.setModo(new DichiarazioneModoMapper().from(dest.getModo()));
		result.setTipo(new DichiarazioneTipoMapper().from(dest.getTipo()));
		result.setDataInserimento(dest.getDataCreazione());
		result.setCompilatoreCF(dest.getCompilatoreCF());
		result.setCittadino(new CittadinoMapper().from(dest.getCittadino()));
		result.setDataInserimento(dest.getDataCreazione());
		result.setNPratica(dest.getnPratica());

		result.setNumeroDocumento(dest.getNumeroDocumento());
		result.setAutorita(dest.getAutorita());
		result.setDataInizioTutela(dest.getDataInizioTutela());
		result.setDataFineTutela(dest.getDataFineTutela());
		
		result.setDettagli(new ArrayList<DichiarazioneDettaglio>());
		for(it.csi.deleghe.deleghebe.ws.model.DichiarazioneDettaglio dettaglio : dest.getDettagli()){
			result.getDettagli().add(new DichiarazioneDettaglioMapper().from(dettaglio));
		}
		
		return result;
	}

}
