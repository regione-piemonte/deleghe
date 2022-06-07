/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import static it.csi.deleghe.deleghebe.util.Check.checkEsitoSuccesso;

import static it.csi.deleghe.deleghebe.util.Check.checkNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import it.csi.deleghe.deleghebe.service.base.BaseService;
import it.csi.deleghe.deleghebe.ws.model.Cittadino;
import it.csi.deleghe.deleghebe.ws.model.DatiDelegaServizioDB;
import it.csi.deleghe.deleghebe.ws.model.DatiDelegatoDB;
import it.csi.deleghe.deleghebe.ws.model.Delega;
import it.csi.deleghe.deleghebe.ws.model.DelegaCittadino;
import it.csi.deleghe.deleghebe.ws.model.DelegaServizio;
import it.csi.deleghe.deleghebe.ws.model.Errore;
import it.csi.deleghe.deleghebe.ws.model.Richiedente;
import it.csi.deleghe.deleghebe.ws.model.RisultatoCodice;
import it.csi.deleghe.deleghebe.ws.model.Sesso;
import it.csi.deleghe.deleghebe.ws.msg.GetDelegati;
import it.csi.deleghe.deleghebe.ws.msg.GetDelegatiResponse;
import it.csi.deleghe.deleghebe.ws.msg.RicercaDeleghe;


public class GetDelegatiService extends BaseService<GetDelegati, GetDelegatiResponse> {
	
	@Inject
	private DelegaBean delegaBean;
	
	@Override
	protected void checkServiceParams(GetDelegati req) {
		checkNotNull(req.getCittadinoDelegante(), "DA.V02", "Delegante non valorizzato.");
		checkNotNull(req.getCittadinoDelegante().getCodiceFiscale(), "DA.V01", "Codice fiscale delegante non valorizzato.");
	}
	
	@Override
	protected GetDelegatiResponse execute(GetDelegati req) {		
		GetDelegatiResponse result = new GetDelegatiResponse();		
		List<DelegaCittadino> delegatiResponse = null;
		List<DatiDelegatoDB> delegatiDB = null;
		String codiceFiscaleDelegatoInput = null;
		


		Richiedente richiedente = req.getRichiedente();
		
		Delega delega=new Delega();
		delega.setDelegante(req.getCittadinoDelegante());

		if(req.getCittadinoDelegato()!=null){
			if(req.getCittadinoDelegato().getCodiceFiscale()!=null) {
				codiceFiscaleDelegatoInput = req.getCittadinoDelegato().getCodiceFiscale();
				delega.setDelegato(req.getCittadinoDelegato());
			}			
		}
			
		
		RicercaDeleghe ricercaDeleghe=new RicercaDeleghe();
		ricercaDeleghe.setRichiedente(req.getRichiedente());	
		ricercaDeleghe.setDelega(delega);
		ricercaDeleghe.setStatiDelega(req.getStatiDelega());
		ricercaDeleghe.setCodiciServizio(req.getCodiciServizio());	
		
		

		if(richiedente.getServizio()!=null) {
			if(richiedente.getServizio().getCodice()!=null) {
				String codiceServizio =richiedente.getServizio().getCodice();
				if(codiceServizio.equalsIgnoreCase("SANSOL")) {
					
					delegatiDB = delegaBean.ricercaDelegati(ricercaDeleghe.getDelega(), ricercaDeleghe.getCodiciServizio(),ricercaDeleghe.getStatiDelega());
					
					
					delegatiResponse =estraiDelegati(delegatiDB, ricercaDeleghe.getCodiciServizio(),ricercaDeleghe.getStatiDelega(), delega.getDelegante().getCodiceFiscale(),codiceFiscaleDelegatoInput);		
					result.setEsito(RisultatoCodice.SUCCESSO);
				}else {
					
					delegatiDB = delegaBean.ricercaDelegatiFSE(ricercaDeleghe.getDelega());
					
					
					delegatiResponse =estraiDelegatiFSE(delegatiDB, delega.getDelegante().getCodiceFiscale());		
					result.setEsito(RisultatoCodice.SUCCESSO);
				}				
			}
		}		
		
		
		if(!delegatiResponse.isEmpty()) {
			result.setDelegati(delegatiResponse);
		}
		else {
			List<Errore> errori=new ArrayList<>();
			errori.add(new Errore("DA.R02","Non esistono occorrenze rispetto alla ricerca effettuata"));
			result.setErrori(errori);
		}
		
		return result;
	}
	
	public List<DelegaCittadino> estraiDelegati(List<DatiDelegatoDB> delegatiDB, List<String> codiciServizio, List<String> statiDelega, String codiceFiscaleDelegante, String codiceFiscaleDelegato) {
		
		List<DelegaCittadino> delegatiResponse = new ArrayList<>();
		if (delegatiDB != null) {
			for (DatiDelegatoDB delegatoDB : delegatiDB) {
				DelegaCittadino delegatoPiuSOL = estraiDelegatoCittadino(delegatoDB, codiciServizio, statiDelega, codiceFiscaleDelegante, codiceFiscaleDelegato);
				delegatiResponse.add(delegatoPiuSOL);
			}
		}
		return delegatiResponse;
	}	
	
	public List<DelegaCittadino> estraiDelegatiFSE(List<DatiDelegatoDB> delegatiDB, String codiceFiscaleDelegante) {
		
		List<DelegaCittadino> delegatiResponse = new ArrayList<>();
		if (delegatiDB != null) {
			for (DatiDelegatoDB delegatoDB : delegatiDB) {
				DelegaCittadino delegatoPiuSOL = estraiDelegatoCittadinoFSE(delegatoDB, codiceFiscaleDelegante);
				delegatiResponse.add(delegatoPiuSOL);
			}
		}
		return delegatiResponse;
	}	
	
	public DelegaCittadino estraiDelegatoCittadino(DatiDelegatoDB delegatoDB, List<String> codiciServizio, List<String> statiDelega, String codiceFiscaleDelegante, String codiceFiscaleDelegato) {
		DelegaCittadino delegato = null;
		Long idAura = null;

			
			delegato = new DelegaCittadino();
			delegato.setCodiceFiscale(delegatoDB.getCodiceFiscale());
			delegato.setCognome(delegatoDB.getCognome());
			delegato.setDataDiNascita(delegatoDB.getDataDiNascita());
			delegato.setNome(delegatoDB.getNome());
			delegato.setSesso(Sesso.fromValue(delegatoDB.getSesso()));
			delegato.setLuogoNascita(delegatoDB.getLuogoNascita());
			if(delegatoDB.getIdAura()!=null) {
				delegato.setIdAura(idAura.parseLong(delegatoDB.getIdAura()));				
			}	
			else {
				delegato.setIdAura(null);
			}
			delegato.setUuid(delegatoDB.getUuid());
			

			
			
			List<DatiDelegaServizioDB> listDatiDelegaServizio = delegaBean.ricercaServiziAssociatiDelegato(codiceFiscaleDelegante, codiciServizio, statiDelega, delegatoDB.getDlgaId(), codiceFiscaleDelegato);
			
			if (listDatiDelegaServizio != null && !listDatiDelegaServizio.isEmpty()) {
				
				List<DelegaServizio> listDelegaServizio = new ArrayList<>();
								
				for (DatiDelegaServizioDB datiDelegaServizio : listDatiDelegaServizio) {
								
					DelegaServizio delegaServizio = new DelegaServizio();
					delegaServizio.setCodiceServizio(datiDelegaServizio.getCodiceServizio());
					delegaServizio.setDataInizioDelega(datiDelegaServizio.getDataInizioDelega());
					delegaServizio.setDataFineDelega(datiDelegaServizio.getDataFineDelega());
					delegaServizio.setDataRevoca(datiDelegaServizio.getDataRevoca());
					delegaServizio.setDataRinuncia(datiDelegaServizio.getDataRinuncia());
					delegaServizio.setStatoDelega(datiDelegaServizio.getStatoDelega());
					delegaServizio.setUUID(datiDelegaServizio.getUuid());
					delegaServizio.setTipoDelega(datiDelegaServizio.getTipoDelega());					
			
					if(datiDelegaServizio.getIdGradoDelega()!=null) {
						String gradoDelega  =delegaBean.ricercaGrado(datiDelegaServizio.getIdGradoDelega());
						delegaServizio.setGradoDelega(gradoDelega);
					}
					


					listDelegaServizio.add(delegaServizio);
				}			
				delegato.setDeleghe(listDelegaServizio);				
			}
			
		return delegato;
	}
	
	public DelegaCittadino estraiDelegatoCittadinoFSE(DatiDelegatoDB delegatoDB, String codiceFiscaleDelegante) {
		DelegaCittadino delegato = null;
		Long idAura = null;

			
			delegato = new DelegaCittadino();
			delegato.setCodiceFiscale(delegatoDB.getCodiceFiscale());
			delegato.setCognome(delegatoDB.getCognome());
			delegato.setDataDiNascita(delegatoDB.getDataDiNascita());
			delegato.setNome(delegatoDB.getNome());
			delegato.setSesso(Sesso.fromValue(delegatoDB.getSesso()));
			delegato.setLuogoNascita(delegatoDB.getLuogoNascita());
			if(delegatoDB.getIdAura()!=null) {
				delegato.setIdAura(idAura.parseLong(delegatoDB.getIdAura()));				
			}	
			else {
				delegato.setIdAura(null);
			}
			delegato.setUuid(delegatoDB.getUuid());
			

			
			
			List<DatiDelegaServizioDB> listDatiDelegaServizio = delegaBean.ricercaServiziAssociatiDelegatoFSE(codiceFiscaleDelegante, delegatoDB.getDlgaId());
			
			if (listDatiDelegaServizio != null && !listDatiDelegaServizio.isEmpty()) {
				List<DelegaServizio> listDelegaServizio = new ArrayList<>();
				
				for (DatiDelegaServizioDB datiDelegaServizio : listDatiDelegaServizio) {
								
					DelegaServizio delegaServizio = new DelegaServizio();
					delegaServizio.setCodiceServizio(datiDelegaServizio.getCodiceServizio());
					delegaServizio.setDataInizioDelega(datiDelegaServizio.getDataInizioDelega());
					delegaServizio.setDataFineDelega(datiDelegaServizio.getDataFineDelega());
					delegaServizio.setDataRevoca(datiDelegaServizio.getDataRevoca());
					delegaServizio.setDataRinuncia(datiDelegaServizio.getDataRinuncia());
					delegaServizio.setStatoDelega("ATTIVA");
					delegaServizio.setUUID(datiDelegaServizio.getUuid());
					delegaServizio.setTipoDelega(datiDelegaServizio.getTipoDelega());
					
					if(datiDelegaServizio.getIdGradoDelega()!=null) {
						String gradoDelega  =delegaBean.ricercaGrado(datiDelegaServizio.getIdGradoDelega());
						delegaServizio.setGradoDelega(gradoDelega);
					}
					


					listDelegaServizio.add(delegaServizio);
				}			
				delegato.setDeleghe(listDelegaServizio);				
			}
			
		return delegato;
	}
	

	

	


}
