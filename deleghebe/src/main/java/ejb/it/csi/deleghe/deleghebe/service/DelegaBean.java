/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import it.csi.deleghe.deleghebe.model.DeleTDelega;
import it.csi.deleghe.deleghebe.model.DeleTDelegaServizio;
import it.csi.deleghe.deleghebe.service.DeleDDelegaServizioStatoRepository.DelstatoCodEnum;
import it.csi.deleghe.deleghebe.service.exception.BeServiceException;
import it.csi.deleghe.deleghebe.service.mapper.DelegaDeleSDelegaMapper;
import it.csi.deleghe.deleghebe.service.mapper.DelegaDeleTDelegaBoDettaglioMapper;
import it.csi.deleghe.deleghebe.service.mapper.DelegaDeleTDelegaBoMapper;
import it.csi.deleghe.deleghebe.service.mapper.DelegaDeleTDelegaMapper;
import it.csi.deleghe.deleghebe.service.mapper.DelegaDelegaCittadinoMapper;
import it.csi.deleghe.deleghebe.util.DateUtil;
import it.csi.deleghe.deleghebe.ws.model.DatiDelegaServizioDB;
import it.csi.deleghe.deleghebe.ws.model.DatiDelegatoDB;
import it.csi.deleghe.deleghebe.ws.model.Delega;
import it.csi.deleghe.deleghebe.ws.model.DelegaCittadino;
import it.csi.deleghe.deleghebe.ws.model.DelegaServ;
import it.csi.deleghe.deleghebe.ws.model.DelegaServizio;

@Stateless
public class DelegaBean extends BaseRepository {

	@Inject
	private DeleTDelegaRepository delegheRepository;
	@Inject
	private DelegaDeleTDelegaMapper tDelegaMapper;
	
	@Inject
	private DelegaDeleTDelegaBoMapper tDelegaBoMapper;
	
	@Inject
	private DelegaDeleTDelegaBoDettaglioMapper tDelegaBoDettaglioMapper;
	

	@Inject
	private DelegaDeleSDelegaMapper sDelegaMapper;
	@Inject
	private DelegaDelegaCittadinoMapper delegaCittadinoMapper;
	@Inject
	private DeleTServizioParametroRepository servizioParametroRepository;

	public Delega inserisciDelega(Delega delega, String loginOperazione) {


		List<String> statiDelega = Arrays.asList(DelstatoCodEnum.ATTIVA.name(), DelstatoCodEnum.IN_SCADENZA.name());

		List<Delega> searchDelegaList = ricercaDeleghe(delega, null, statiDelega);

		if (searchDelegaList != null && !searchDelegaList.isEmpty()) {
    		throw new BeServiceException("DA.DLG08", "Delega gia' inserita");
		}

		if (DateUtil.isMaggiorenne(delega.getDelegante().getDataNascita())) {

			checkMaxDelegatiXDeleganteXServizio(delega);
			checkMaxDelegantiXDelegatoXServizio(delega);
		}
		
		DeleTDelega deleTDelega = tDelegaMapper.to(delega);

		DeleTDelega deleTDelegaInsert = delegheRepository.inserisciDelega(deleTDelega, loginOperazione);

		return tDelegaMapper.from(deleTDelegaInsert);
	}

	
	public void workAroundCorrezionebaco(Integer numeroPratica) {	

		String dicTipoCod = delegheRepository.ricercaDichTipoCodice(numeroPratica);
		if(dicTipoCod!=null) {
			Integer delTipId = delegheRepository.ricercaDelegaTipoCodice(dicTipoCod);
			if(delTipId!=null) {
				boolean risultatoUpdate =  delegheRepository.forzareDelTipId(delTipId, numeroPratica);
			}else {
				
			}
		}else {

		}

	}
	public Delega aggiornaDelega(Delega delega, String loginOperazione) {

		DeleTDelega deleTDelegaDB = delegheRepository.ricercaDelegheByUUID(delega.getUuid());
		if (deleTDelegaDB == null) {
    		String msg = "Delega non trovata per l'UUID:" + delega.getUuid();
    		throw new BeServiceException("DA.R02", msg);
		}


		
		DeleTDelega deleTDelega = tDelegaMapper.to(delega);
		deleTDelega.setDlgaId(deleTDelegaDB.getDlgaId());

		DeleTDelega deleTDelegaUpdate = delegheRepository.aggiornaDelega(deleTDelega, loginOperazione);

		return tDelegaMapper.from(deleTDelegaUpdate);
	}

	public List<Delega> ricercaDeleghe(Delega delega, List<String> codiciServizio, List<String> statiDelega) {
		DeleTDelega deleTDelega = tDelegaMapper.to(delega);

		List<DeleTDelega> listDeleTDelega = delegheRepository.ricercaDeleghe(deleTDelega, codiciServizio, statiDelega);

		return tDelegaMapper.fromList(listDeleTDelega);
	}
	

	public List<DatiDelegatoDB> ricercaDelegati(Delega delega, List<String> codiciServizio, List<String> statiDelega) {

		List<DatiDelegatoDB> listDelegati = delegheRepository.ricercaDelegati(delega, codiciServizio, statiDelega);

		return listDelegati;
	}
	
	public List<DatiDelegatoDB> ricercaDelegatiFSE(Delega delega) {

		List<DatiDelegatoDB> listDelegati = delegheRepository.ricercaDelegatiFSE(delega);

		return listDelegati;
	}
	
	public List<DatiDelegaServizioDB> ricercaServiziAssociatiDelegato(String codiceFiscaleDelegante, List<String> codiciServizio, List<String> statiDelega, Integer dlgaId, String codiceFiscaleDelegato) {

		List<DatiDelegaServizioDB> listDatiDelegaServizio = delegheRepository.ricercaServiziAssociatiDelegato(codiceFiscaleDelegante, codiciServizio, statiDelega, dlgaId, codiceFiscaleDelegato);
		return listDatiDelegaServizio;
	}
	
	public List<DatiDelegaServizioDB> ricercaServiziAssociatiDelegatoFSE(String codiceFiscaleDelegante, Integer dlgaId) {

		List<DatiDelegaServizioDB> listDatiDelegaServizio = delegheRepository.ricercaServiziAssociatiDelegatoFSE(codiceFiscaleDelegante, dlgaId);
		return listDatiDelegaServizio;
	}
	
	public String ricercaGrado(Integer idGradoDelega) {

		String gradoDelega = delegheRepository.ricercaGrado(idGradoDelega);
		return gradoDelega;
	}

	
	

	public List<String> ricercaDelegheFSE(Delega delega, List<String> codiciServizio, List<String> statiDelega) {
		List<String> listDeleTDelega = delegheRepository.ricercaDelegheFSE(delega, codiciServizio, statiDelega);

		return listDeleTDelega;
	}


	/**
	 * NUOVO SERVIZIO BACK-OFFICE
	 * 
	 * @param cfDelegato
	 * @param cfDelegante
	 * @param dataInsDa
	 * @param dataInsA
	 * @param dataValDa
	 * @param dataValA
	 * @param tipiDelega
	 * @param statiDelega
	 * @return
	 */
	public List<Delega> ricercaDeleghe(String cfDelegato, String cfDelegante, 
										Date dataInsDa, Date dataInsA, Date dataValDa, Date dataValA, 
										List<String> tipiDelega, List<String> statiDelega, Integer ciIdDelegato, Integer citIdDelegante) {
		
		List<DeleTDelega> listDeleTDelega = delegheRepository.ricercaDeleghe(cfDelegato, cfDelegante, dataInsDa, dataInsA, 
																				dataValDa,dataValA, tipiDelega, statiDelega, ciIdDelegato, citIdDelegante);

		
		return tDelegaBoMapper.fromList(listDeleTDelega);
	}
	
	/** 
	 * NUOVO SERVIZIO BACK-OFFICE
	 * @param delega
	 * @return
	 */
	public Delega ricercaDettaglioDelegaByUUD(Delega delega) {
		
		DeleTDelega deleTDelega = delegheRepository.ricercaDelegheByUUID(delega.getUuid());

		return tDelegaBoDettaglioMapper.from(deleTDelega);
	}

	public List<Delega> ricercaDelegheStorico(Delega delega, List<String> codiciServizio, List<String> statiDelega) {
		DeleTDelega deleTDelega = tDelegaMapper.to(delega);

		List<DeleTDelega> listDeleTDelega = delegheRepository.ricercaDeleghe(deleTDelega, codiciServizio, statiDelega);

		return sDelegaMapper.fromList(listDeleTDelega);
	}

	/**
	 * Verifica se il numero di delegati per servizio non ha raggiunto il numero massimo ammissibile (definito sul db).
	 * Se si rilancia una eccezione.
	 * @param delega
	 */
	private void checkMaxDelegatiXDeleganteXServizio(Delega delega) {


		Map<String, Integer> mapServizi = new HashMap<>();


		List<DeleTDelega> listDeleghe = delegheRepository.ricercaDelegheByCF(delega.getDelegante().getCodiceFiscale(), null);
		for (DeleTDelega deleTDelega : listDeleghe) {
			for (DeleTDelegaServizio deleTDelegaServizio : deleTDelega.getDeleTDelegaServizios()) {
				if (isActive(deleTDelegaServizio)) {
					String serCod = deleTDelegaServizio.getDeleDServizio().getSerCod();
					if (!mapServizi.containsKey(serCod)) {
						mapServizi.put(serCod, 1);
					} else {
						Integer value = mapServizi.get(serCod);
						mapServizi.put(serCod, value + 1);
					}
				}
			}
		}

		if (!mapServizi.isEmpty()) {
			Integer maxDelegatiServizio = Integer.parseInt(servizioParametroRepository.getMaxNumeroDelegatiServizio());

			for (DelegaServ delegaServ : delega.getServizi()) {
				if (!isContentUpdate(delega, delegaServ, listDeleghe) && isActive(delegaServ) && isFull(delegaServ, mapServizi, maxDelegatiServizio)) {
					String msg = "E' stato raggiunto il numero massimo di delegati per delegante sul servizio selezionato con codice: " + delegaServ.getServizio().getCodice();
					throw new BeServiceException("DA.DLG10", msg);
				}
			}
		}
	}

	/**
	 * Verifica se il numero di deleganti per servizio non ha raggiunto il numero massimo ammissibile (definito sul db).
	 * Se si rilancia una eccezione.
	 * @param delega
	 */
	private void checkMaxDelegantiXDelegatoXServizio(Delega delega) {

		Delega delegaFilter = new Delega();
		delegaFilter.setDelegato(delega.getDelegato());
		
		List<String> serviziFilter = new ArrayList<>();
		delega.getServizi().forEach(ds -> serviziFilter.add(ds.getServizio().getCodice()));
		
		List<String> statiFilter = Arrays.asList(DelstatoCodEnum.ATTIVA.name(), DelstatoCodEnum.IN_SCADENZA.name());
		

		Map<String, Integer> mapServizi = new HashMap<>();

		List<Delega> deleghe = ricercaDeleghe(delegaFilter, serviziFilter, statiFilter);
		List<DelegaCittadino> deleganti = delegaCittadinoMapper.estraiDeleganti(deleghe);
		for (DelegaCittadino delegante : deleganti) {
			for (DelegaServizio delegaServizio : delegante.getDeleghe()) {
				if (isActive(delegaServizio)) {
					String serCod = delegaServizio.getCodiceServizio();
					if (!mapServizi.containsKey(serCod)) {
						mapServizi.put(serCod, 1);
					} else {
						Integer value = mapServizi.get(serCod);
						mapServizi.put(serCod, value + 1);
					}
				}
			}
		}

		if (!mapServizi.isEmpty()) {
			List<DeleTDelega> listDeleghe = delegheRepository.ricercaDelegheByCF(delega.getDelegante().getCodiceFiscale(), null);
			Integer maxDelegantiServizio = Integer.parseInt(servizioParametroRepository.getMaxNumeroDelegantiServizio());

			for (DelegaServ delegaServ : delega.getServizi()) {
				if (!isContentUpdate(delega, delegaServ, listDeleghe) && isActive(delegaServ) && isFull(delegaServ, mapServizi, maxDelegantiServizio)) {
					String msg = "E' stato raggiunto il numero massimo di deleganti per delegato sul servizio selezionato con codice: " + delegaServ.getServizio().getCodice();
					throw new BeServiceException("DA.DLG09", msg);
				}
			}
		}
	}

	/**
	 * Verifica se l'operazione sulla delega è una semplice modifica di contenuti
	 * @param delegaServ
	 * @param listDeleghe
	 * @return
	 */
	private boolean isContentUpdate(Delega delega, DelegaServ delegaServ, List<DeleTDelega> listDeleghe) {
		for (DeleTDelega deleTDelega : listDeleghe) {

			if (deleTDelega.getDeleTCittadino2().getCitCf().equals(delega.getDelegato().getCodiceFiscale())) {
				for (DeleTDelegaServizio deleTDelegaServizio : deleTDelega.getDeleTDelegaServizios()) {
					if (deleTDelegaServizio.getDeleDServizio().getSerCod().equals(delegaServ.getServizio().getCodice())
						&& deleTDelegaServizio.getDeleDDelegaServizioStato().getDelstatoCod().equals(delegaServ.getStato().getCodice())) return true;
				}
				return false;
			}
		}
		return false;
	}
	
	/**
	 * Verifica se il delegato ha gia raggiunto il numero massimo di delegati per servizio
	 * @param delegaServ
	 * @param map
	 * @param maxDelegheServizio
	 * @return
	 */
	private boolean isFull(DelegaServ delegaServ, Map<String, Integer> map, Integer maxPerServizio) {
		String codServ = delegaServ.getServizio().getCodice();
		return map.containsKey(codServ) && (map.get(codServ) >= maxPerServizio);
	}

	private boolean isActive(DeleTDelegaServizio tDelegaServizio) {
		return isActive(tDelegaServizio.getDeleDDelegaServizioStato().getDelstatoCod());
	}
	
	private boolean isActive(DelegaServ delegaServ) {
		return isActive(delegaServ.getStato().getCodice());
	}
	
	private boolean isActive(DelegaServizio delegaServ) {
		return isActive(delegaServ.getStatoDelega());
	}
	
	private boolean isActive(String codStato) {
		return (codStato != null && (codStato.equals(DeleDDelegaServizioStatoRepository.DelstatoCodEnum.ATTIVA.name()) ||
				codStato.equals(DeleDDelegaServizioStatoRepository.DelstatoCodEnum.IN_SCADENZA.name())));
	}

}