/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import it.csi.deleghe.deleghebe.model.*;
import it.csi.deleghe.deleghebe.service.*;
import it.csi.deleghe.deleghebe.ws.model.Cittadino;
import it.csi.deleghe.deleghebe.ws.model.Delega;
import it.csi.deleghe.deleghebe.ws.model.DelegaServ;

public class DelegaDeleTDelegaMapper implements Mapper<Delega, DeleTDelega> {

	@Inject
	private CittadinoDeleTCittadinoMapper cittadinoMapper;
	@Inject
	private DelegatoDeleTDelegatoMapper delegatoMapper;
	@Inject
	private DelegaServDeleTServizioMapper delegaServizioMapper;
	@Inject
	private RuoloOperazioneDeleDRuoloOpMapper ruoloOperazioneDeleDRuoloOpMapper;
	@Inject
	private DeleTCittadinoRepository deleTCittadinoRepository;
	@Inject
	private DeleDDelegaTipoRepository deleDDelegaTipoRepository;
    @Inject
    private DeleDEnumerazioneRepository deleDEnumerazioneRepository;
    @Inject
    private DeleDDelegaStatoRepository deleDDelegaStatoRepository;
    @Inject
	private DichiarazioneDettaglioDeleTDichiarazioneDetMapper dichiarazioneDettaglioDeleTDichiarazioneDetMapper;

   @Override
	public DeleTDelega to(Delega src) {
		if(src==null) {
			return null;
		}
		
		DeleTDelega res = new DeleTDelega();
		
		res.setUuid(src.getUuid());
		res.setDeleDRuoloOp(ruoloOperazioneDeleDRuoloOpMapper.to(src.getRuoloOperazione()));

		Boolean presavisione = src.getPresavisione();
		if(presavisione == null) {
			res.setPresavisione(Boolean.FALSE); 
		}else{
			res.setPresavisione(presavisione);
		}
		
		
		if(src.getDelegante()!=null && !src.getDelegante().getCodiceFiscale().isEmpty()) {
			DeleTCittadino searchCittadino1=deleTCittadinoRepository.ricercaCittadino(src.getDelegante().getCodiceFiscale());
			if(searchCittadino1 == null) {
				res.setDeleTCittadino1(cittadinoMapper.to(src.getDelegante()));
			}
			else {
				res.setDeleTCittadino1(searchCittadino1);
			}
		}
		

		if(src.getDelegato()!=null && !src.getDelegato().getCodiceFiscale().isEmpty()) {
			
			DeleTCittadino searchCittadino2=deleTCittadinoRepository.ricercaCittadino(src.getDelegato().getCodiceFiscale());
			if(searchCittadino2 == null) {
				res.setDeleTCittadino2(cittadinoMapper.to(src.getDelegato()));
			}
			else {
				res.setDeleTCittadino2(searchCittadino2);
			}
		}

		List<DelegaServ> servizi = src.getServizi();
		res.setDeleTDelegaServizios(delegaServizioMapper.toList(servizi));

		if(res.getDeleTDelegaServizios()!=null) {
			res.getDeleTDelegaServizios().forEach(ds -> {
				ds.setDeleTDelega(res);
			});
		}
		
		List<DeleTDelegato> listdTc= new ArrayList<DeleTDelegato>();
		DeleTDelegato dTc=null;

		if(src.getDelegatoInput()!=null) {
			dTc=delegatoMapper.to(src.getDelegatoInput());
			dTc.setDeleTDelega(res);
		}
		else if (src.getDelegato() != null) {
			Cittadino delegato = src.getDelegato();
			dTc = new DeleTDelegato();

			dTc.setDlgoCf(delegato.getCodiceFiscale());
			dTc.setDlgoCognome(delegato.getCognome());
			dTc.setDlgoNome(delegato.getNome());
			dTc.setDlgoSesso(delegato.getSesso());
			dTc.setDlgoNascitaData(delegato.getDataNascita());
			dTc.setDlgoNascitaComune(delegato.getComuneNascita());
			dTc.setDeleDRuoloOp(ruoloOperazioneDeleDRuoloOpMapper.to(delegato.getRuoloOperazione()));

			dTc.setDeleTDelega(res);
		}
		listdTc.add(dTc);
		res.setDeleTDelegatos(listdTc);


      String motivazioneMenu = src.getMotivazioneMenu();
      if (motivazioneMenu != null) {
    	 res.setDeleDEnumerazione(deleDEnumerazioneRepository.ricercaDeleDEnumerazioneByEnumCod(motivazioneMenu.toUpperCase()));
      }
      
      res.setNotaMotivazione(src.getMotivazioneCasella()!=null?src.getMotivazioneCasella():"");
      res.setPresavisioneData(src.getPresavisioneData());      
      res.setCompilatoreCf(src.getCompilatoreCF());
      
		String tipoDelega = src.getTipoDelega();
		if (tipoDelega != null) {
			res.setDeleDDelegaTipo(deleDDelegaTipoRepository.ricercaDeleDDelegaTipoByTipoCod(tipoDelega));
		}

		String statoDelega = src.getStatoDelega();
		if(statoDelega!=null) {
			res.setDeleDDelegaStato(deleDDelegaStatoRepository.ricercaDeleDDelegaStatoByStatoCod(statoDelega));
		}

		res.setDeleTDichiarazioneDet(dichiarazioneDettaglioDeleTDichiarazioneDetMapper.to(src.getDichiarazioneDettaglio()));
		
		Boolean bloccaDelega = src.getBloccaDelega();
		res.setBlocca(bloccaDelega == null? false: bloccaDelega);

		return res;
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
		res.setDelegato(cittadinoMapper.from(dest.getDeleTCittadino2()));
		
		if(dest.getDeleTDelegatos()!= null && !dest.getDeleTDelegatos().isEmpty()) 
			res.setDelegatoInput(delegatoMapper.from(dest.getDeleTDelegatos().get(0)));
		
		res.setServizi(delegaServizioMapper.fromList(dest.getDeleTDelegaServizios()));
		res.setRuoloOperazione(ruoloOperazioneDeleDRuoloOpMapper.from(dest.getDeleDRuoloOp()));
        res.setDataCreazione(dest.getDataCreazione());
        DeleDEnumerazione deleDEnumerazione = dest.getDeleDEnumerazione();
        if (deleDEnumerazione != null) {
        	res.setMotivazioneMenu(deleDEnumerazione.getEnumCod());
        }
        res.setMotivazioneCasella(dest.getNotaMotivazione());
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
