/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.business.mapper;

import it.csi.deleghe.delegheboweb.dto.delegheboweb.Delega;

import java.util.UUID;

public class DelegheMapper extends BaseMapper<Delega, it.csi.deleghe.deleghebe.ws.model.Delega> {

   private CittadinoMapper cittadinoMapper = new CittadinoMapper();
   private DelegaServMapper delegaServMapper = new DelegaServMapper();
   private DichiarazioneDettaglioMapper dichiarazioneDettaglioMapper = new DichiarazioneDettaglioMapper();

   @Override
   public it.csi.deleghe.deleghebe.ws.model.Delega to(Delega source) {

      it.csi.deleghe.deleghebe.ws.model.Delega result = new it.csi.deleghe.deleghebe.ws.model.Delega();

      final String uuid = source.getUuid();
      if (uuid != null) result.setUuid(UUID.fromString(uuid));
      result.setDelegante(cittadinoMapper.to(source.getDelegante()));
      result.setDelegato(cittadinoMapper.to(source.getDelegato()));
      result.setServizi(delegaServMapper.toList(source.getServizi()));
      result.setMotivazioneCasella(source.getMotivazioneCasella());
      result.setMotivazioneMenu(source.getMotivazioneMenu());
      result.setCompilatoreCF(source.getCompilatoreCF());
      result.setDichiarazioneDettaglio(dichiarazioneDettaglioMapper.to(source.getDichiarazioneDettaglio()));
      result.setPresavisione(source.getPresavisione());
      result.setStatoDelega(source.getStatoDelega());
      result.setTipoDelega(source.getTipoDelega());
      result.setBloccaDelega(source.getBloccaDelega());
      result.setDataCreazione(source.getRichiesta());
      return result;
   }

   @Override
   public Delega from(it.csi.deleghe.deleghebe.ws.model.Delega dest) {
      Delega result = new Delega();
      
      result.setNumeroPratica(dest.getnPratica());
      result.setDelegante(cittadinoMapper.from(dest.getDelegante()));
      result.setDelegato(cittadinoMapper.from(dest.getDelegato()));
      result.setServizi(delegaServMapper.fromList(dest.getServizi()));
      final UUID uuid = dest.getUuid();
      if (uuid != null) result.setUuid(uuid.toString());
      result.setMotivazioneCasella(dest.getMotivazioneCasella());
      result.setMotivazioneMenu(dest.getMotivazioneMenu());
      result.setCompilatoreCF(dest.getCompilatoreCF());
      result.setDichiarazioneDettaglio(dichiarazioneDettaglioMapper.from(dest.getDichiarazioneDettaglio()));
      result.setPresavisione(dest.getPresavisione());
      result.setStatoDelega(dest.getStatoDelega());
      result.setTipoDelega(dest.getTipoDelega());
      result.setBloccaDelega(dest.getBloccaDelega());
      result.setRichiesta(dest.getDataCreazione());
      return result;
   }
}
