/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import it.csi.deleghe.deleghebe.model.DServizio;
import it.csi.deleghe.deleghebe.model.DeleDServizio;
import it.csi.deleghe.deleghebe.service.mapper.ServizioDServizioMapper;
import it.csi.deleghe.deleghebe.service.mapper.ServizioDeleDServizioMapper;
import it.csi.deleghe.deleghebe.ws.model.Servizio;

@Stateless
public class ServizioBean {
	
	@Inject
	private DServizioRepository serviziRepository;
	@Inject
	private ServizioDServizioMapper servizioMapper;
	@Inject
	private DeleDServizioRepository servizioRep;

	public Servizio inserisciServizio(Servizio servizio, String loginOperazione) {
		
		List<Servizio> servizi = ricercaServizi(servizio);
		
		if(servizi != null && !servizi.isEmpty()) {
			throw new IllegalArgumentException("Servizio già presente.");
		}
		
		DServizio dServizio = servizioMapper.to(servizio);
		
		DServizio dServizioInserito = serviziRepository.inserisciServizio(dServizio, loginOperazione);

		return servizioMapper.from(dServizioInserito);
	}
	
	public Servizio aggiornaServizio(Servizio servizio, String loginOperazione) {
		
		DServizio dServizioInput = servizioMapper.to(servizio);
		List<DServizio> dServizi  = serviziRepository.ricercaServizi(dServizioInput);
		
		if(dServizi != null && dServizi.size()>1) {
			throw new IllegalArgumentException("Trovato più di un servzio con codice:" + servizio.getCodice());
		}
		

		DServizio dServizio=dServizi.get(0);

		dServizio=localMapper(dServizio,servizio);
		
		DServizio dServizioInserito = serviziRepository.aggiornaServizio(dServizio, loginOperazione);

		return servizioMapper.from(dServizioInserito);
	}
	
	public List<Servizio> ricercaServizi(Servizio servizio) {
		
		List<DeleDServizio> deleDServizios = servizioRep.ricercaServizi();

		return new ServizioDeleDServizioMapper().fromList(deleDServizios);
	}
	
	private DServizio localMapper(DServizio dServizio,Servizio servizio) {
	
		dServizio.setServCod(servizio.getCodice());
		dServizio.setServDesc(servizio.getDescrizione());
		dServizio.setServDescestesa(servizio.getDescrizioneEstesa());
		dServizio.setServMinore(servizio.getMinore());
		dServizio.setServArruolabile(servizio.getArruolabile());
		dServizio.setServNotificabile(servizio.getNotificabile());
		dServizio.setServDelegabile(servizio.getDelegabile());
		dServizio.setServObblarruolamento(servizio.getObbligatorioArruolamento());
		dServizio.setServMaxggDelega(servizio.getNumeroGiorniDelegabili());
		dServizio.setValiditaInizio(servizio.getDataInizioValidita());
		dServizio.setValiditaFine(servizio.getDataFineValidita());
		
		return dServizio;
	}
	
}
