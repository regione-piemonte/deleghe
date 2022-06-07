/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.business.mapper;

import java.util.UUID;

import it.csi.deleghe.delegheboweb.dto.delegheboweb.DichiarazioneDettaglio;
import it.csi.deleghe.delegheboweb.dto.delegheboweb.RuoloOperazione;

public class DichiarazioneDettaglioMapper extends BaseMapper<DichiarazioneDettaglio, it.csi.deleghe.deleghebe.ws.model.DichiarazioneDettaglio> {

	@Override
	public it.csi.deleghe.deleghebe.ws.model.DichiarazioneDettaglio to(DichiarazioneDettaglio source) {
		if(source == null) {
			return null;
		}
		it.csi.deleghe.deleghebe.ws.model.DichiarazioneDettaglio result = new it.csi.deleghe.deleghebe.ws.model.DichiarazioneDettaglio();

		final String uuid = source.getUuid();
		if (uuid != null) result.setUuid(UUID.fromString(uuid));
		result.setCittadino1(new CittadinoMapper().to(source.getGenitoreTutoreCuratore()));
		result.setRuoloCittadino1(new DichiarazioneRuoloMapper().to(source.getRuoloGenitoreTutoreCuratore()));
		
		result.setCittadino2(new CittadinoMapper().to(source.getFiglioTutelatoCurato()));
		result.setRuoloCittadino2(new DichiarazioneRuoloMapper().to(source.getRuoloFiglioTutelatoCurato()));
		result.setNotaRevocaBlocco(source.getNoteRevocaBlocco());
		result.setMotivazioneCasella(source.getMotivazioneCasella());
		result.setStato(new DichiarazioneDettaglioStatoMapper().to(source.getStato()));
		
		result.setDocumento(new DocumentoMapper().to(source.getDocumento()));
		result.setIdDettaglio(source.getDicdetId());


		result.setDataCancellazione(source.getDataCancellazione());
		result.setDataCreazione(source.getDataCreazione());
		result.setDataModifica(source.getDataModifica());
		result.setLoginOperazione(source.getLoginOperazione());


		return result;
	}

	@Override
	public DichiarazioneDettaglio from(it.csi.deleghe.deleghebe.ws.model.DichiarazioneDettaglio dest) {
		if(dest == null) {
			return null;
		}
		DichiarazioneDettaglio result = new DichiarazioneDettaglio();

		final UUID uuid = dest.getUuid();
		if (uuid != null) result.setUuid(uuid.toString());
		result.setGenitoreTutoreCuratore(new CittadinoMapper().from(dest.getCittadino1()));
		result.setRuoloGenitoreTutoreCuratore(new DichiarazioneRuoloMapper().from(dest.getRuoloCittadino1()));
		
		result.setFiglioTutelatoCurato(new CittadinoMapper().from(dest.getCittadino2()));
		result.setRuoloFiglioTutelatoCurato(new DichiarazioneRuoloMapper().from(dest.getRuoloCittadino2()));
		result.setNoteRevocaBlocco(dest.getNotaRevocaBlocco());
		result.setMotivazioneCasella(dest.getMotivazioneCasella());
		result.setStato(new DichiarazioneDettaglioStatoMapper().from(dest.getStato()));
		
		result.setDocumento(new DocumentoMapper().from(dest.getDocumento()));
		result.setDicdetId(dest.getIdDettaglio());		

		result.setDataCancellazione(dest.getDataCancellazione());
		result.setDataCreazione(dest.getDataCreazione());
		result.setDataModifica(dest.getDataModifica());
		result.setLoginOperazione(dest.getLoginOperazione());



		return result;
	}

}
