/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service.mapper;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import it.csi.deleghe.deleghebe.model.DeleDDelegaStato;
import it.csi.deleghe.deleghebe.model.DeleDDelegaTipo;
import it.csi.deleghe.deleghebe.model.DeleTDelega;
import it.csi.deleghe.deleghebe.service.DeleDDelegaStatoRepository;
import it.csi.deleghe.deleghebe.service.DeleDDelegaTipoRepository;
import it.csi.deleghe.deleghebe.service.DeleDEnumerazioneRepository;
import it.csi.deleghe.deleghebe.service.DeleTCittadinoRepository;
import it.csi.deleghe.deleghebe.ws.model.Cittadino;
import it.csi.deleghe.deleghebe.ws.model.Delega;

public class DelegaDeleTDelegaBoDettaglioMapper implements Mapper<Delega, DeleTDelega> {

	@Inject
	private CittadinoDeleTCittadinoMapper cittadinoMapper;

	@Inject
	private DelegaServDeleTServizioMapper delegaServizioMapper;
	
	@Inject
    private DichiarazioneDettaglioDeleTDichiarazioneDetMapper dichiarazioneDettaglioDeleTDichiarazioneDetMapper;

   @Inject
   DocumentoDeleTDocumentoMapper documentoDeleTDocumentoMapper;
   
   @Override
   public DeleTDelega to(Delega src) {

		return null;
   }

	@Override
	public Delega from(DeleTDelega dest) {
		if(dest==null) {
			return null;
		}
		Delega res = new Delega();
		res.setUuid(dest.getUuid());
		res.setnPratica(dest.getDlgaId());
		
		res.setDelegante(cittadinoMapper.from(dest.getDeleTCittadino1()));
		
		

		Cittadino delegato = new Cittadino();

		delegato.setCodiceFiscale(StringUtils.upperCase(dest.getDeleTCittadino2().getCitCf()));
		delegato.setCognome(WordUtils.capitalizeFully(dest.getDeleTCittadino2().getCitCognome()));
		delegato.setNome(WordUtils.capitalizeFully(dest.getDeleTCittadino2().getCitNome()));
		delegato.setSesso(dest.getDeleTCittadino2().getCitSesso());
		delegato.setDataNascita(dest.getDeleTCittadino2().getCitNascitaData());
		delegato.setComuneNascita(WordUtils.capitalizeFully(dest.getDeleTCittadino2().getCitNascitaComune()));
				
		delegato.setDocumento(documentoDeleTDocumentoMapper.from(dest.getDeleTCittadino2().getDeleTDocumento()));
		
		res.setDelegato(delegato);
		

		
		res.setServizi(delegaServizioMapper.fromList(dest.getDeleTDelegaServizios()));
		

;
		res.setDichiarazioneDettaglio(dichiarazioneDettaglioDeleTDichiarazioneDetMapper.from(dest.getDeleTDichiarazioneDet()));
		Boolean presavisione = dest.getPresavisione();
		if (presavisione != null) {
			res.setPresavisione(presavisione);
		}
		res.setPresavisioneData(dest.getPresavisioneData());
		res.setCompilatoreCF(dest.getCompilatoreCf());
		DeleDDelegaTipo deleDDelegaTipo = dest.getDeleDDelegaTipo();
		if(deleDDelegaTipo != null) {
			res.setTipoDelega(deleDDelegaTipo.getDelTipCod());
		}
		DeleDDelegaStato deleDDelegaStato = dest.getDeleDDelegaStato();
		if(deleDDelegaStato != null) {
			res.setStatoDelega(deleDDelegaStato.getDelStatoCod());
		}

		res.setBloccaDelega(dest.getBlocca());
		return res;
	}
	


}
