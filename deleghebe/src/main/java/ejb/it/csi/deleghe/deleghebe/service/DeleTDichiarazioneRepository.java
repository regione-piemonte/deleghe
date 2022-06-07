/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import java.util.*;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;

import it.csi.deleghe.deleghebe.model.DeleDDichiarazioneDetStato;
import it.csi.deleghe.deleghebe.model.DeleDDichiarazioneStato;
import it.csi.deleghe.deleghebe.model.DeleTDelega;
import it.csi.deleghe.deleghebe.model.DeleTDichiarazione;
import it.csi.deleghe.deleghebe.model.DeleTDichiarazioneDet;
import it.csi.deleghe.deleghebe.service.DeleDDichiarazioneDetStatoRepository.DicDetStatoCodEnum;
import it.csi.deleghe.deleghebe.service.DeleDDichiarazioneStatoRepository.DicStatoCodEnum;
import it.csi.deleghe.deleghebe.service.exception.BeServiceException;
import it.csi.deleghe.deleghebe.util.LogUtil;
import it.csi.deleghe.deleghebe.util.LogUtil.ToLog;

@Stateless
public class DeleTDichiarazioneRepository extends BaseRepository {

	@Inject
	private LogUtil log;

	@Inject
	private EntityManager em;

	@Inject
	private DeleTDichiarazioneDetRepository deleTDichiarazioneDetRepository;
	@Inject
	private DeleDDichiarazioneStatoRepository deleDDichiarazioneStatoRepository;
	@Inject
	private DeleDDichiarazioneDetStatoRepository deleDDichiarazioneDetStatoRepository;

	public DeleTDichiarazione inserisciDichiarazione(DeleTDichiarazione dichiarazione, String loginOperazione) {
		final String METHOD_NAME = "inserisciDichiarazione";
		log.debug(METHOD_NAME, "inserisci dichiarazione per cittadino %s", (ToLog) () -> (dichiarazione != null)? dichiarazione.getDeleTCittadino().getCitCf() : "null");

		String statoCodiceDichiarazione = dichiarazione.getDeleDDichiarazioneStato().getDicStatoCod();
		dichiarazione.setDeleDDichiarazioneStato(deleDDichiarazioneStatoRepository.ricercaDichiarazioneStatoByDicStatoCod(statoCodiceDichiarazione));
		dichiarazione.setDicId(null);
		dichiarazione.setUuid(UUID.randomUUID());
		setCreazione(dichiarazione, loginOperazione);

		if (dichiarazione.getDeleTCittadino() != null) {
			setCreazione(dichiarazione.getDeleTCittadino(), loginOperazione);
		}

		if (dichiarazione.getDeleTDichiarazioneDets() != null) {
			for (DeleTDichiarazioneDet dd : dichiarazione.getDeleTDichiarazioneDets()) {
				dd.setDicdetId(null);
				dd.setUuid(UUID.randomUUID());
				setCreazione(dd, loginOperazione);


				String statoCodiceDelega =dd.getDeleDDichiarazioneDetStato().getDicdetStatoCod();
				dd.setDeleDDichiarazioneDetStato(deleDDichiarazioneDetStatoRepository.ricercaDetStatoByDicDetStatoCod(DicDetStatoCodEnum.valueOf(statoCodiceDelega)));

				if (dd.getDeleTCittadino1() != null) {
					setCreazione(dd.getDeleTCittadino1(), loginOperazione);
				}
				if (dd.getDeleTCittadino2() != null) {
					setCreazione(dd.getDeleTCittadino2(), loginOperazione);
				}								
				
				if (dd.getDeleTDocumento() != null) {
					dd.getDeleTDocumento().setDocId(null);
					setCreazione(dd.getDeleTDocumento(), loginOperazione);
					
					if (dd.getDeleTDocumento().getDeleTFile() != null) {
						dd.getDeleTDocumento().getDeleTFile().setFileId(null);
						setCreazione(dd.getDeleTDocumento().getDeleTFile(), loginOperazione);
					}
				}				

				if (dd.getDeleTDelegas() != null) {
					for (DeleTDelega deletdelega : dd.getDeleTDelegas()) {
						deletdelega.setDlgaId(null);
						setCreazione(deletdelega, loginOperazione);
					}
				}
			}
		}

		em.persist(dichiarazione);
		return dichiarazione;
	}

	/**
	 * La modifica attualmente, come espresso in analisi, prevede esclusivamente l'aggionamento delle date
	 * 
	 * @param dichiarazione
	 * @param loginOperazione
	 * @return
	 */
	public DeleTDichiarazione aggiornaDichiarazione(DeleTDichiarazione dichiarazione, String loginOperazione) {
		final String METHOD_NAME = "aggiornaDichiarazione";


		DeleTDichiarazione dichiarazioneBydb = em.find(DeleTDichiarazione.class, dichiarazione.getDicId());

		log.debug(METHOD_NAME, "aggiorna dichiarazione UUID: %s", (ToLog) () -> (dichiarazione != null)? dichiarazione.getUuid() : "null");

		if (dichiarazioneBydb == null) {
			String msg = "Nessun risultato trovato per l'id " + dichiarazione.getDicId();
			log.error(METHOD_NAME, msg);
			throw new BeServiceException("DA.R02", msg);
		}

		setModifica(dichiarazioneBydb, dichiarazioneBydb.getLoginOperazione());
		dichiarazioneBydb.setUuid(UUID.randomUUID());


		DeleDDichiarazioneStato deleDDichciarazioneStato = deleDDichiarazioneStatoRepository.ricercaDichiarazioneStatoByDicStatoCod(dichiarazione.getDeleDDichiarazioneStato().getDicStatoCod());
		if (deleDDichciarazioneStato != null) {
			dichiarazioneBydb.setDeleDDichiarazioneStato(deleDDichciarazioneStato);
		}

		if (dichiarazioneBydb.getDeleTCittadino() != null) {
			setModifica(dichiarazioneBydb.getDeleTCittadino(), dichiarazioneBydb.getDeleTCittadino().getLoginOperazione());
		}


		if (dichiarazione.getDeleTDichiarazioneDets() != null) {
			for (DeleTDichiarazioneDet dd : dichiarazione.getDeleTDichiarazioneDets()) {

				DeleTDichiarazioneDet deleTDichiarazioneDetTrovato = deleTDichiarazioneDetRepository.ricercaDichiarazioneDetByUUID(dd.getUuid());

				if (deleTDichiarazioneDetTrovato != null) {
					DeleTDichiarazioneDet deleTDichiarazioneDet = em.find(DeleTDichiarazioneDet.class, deleTDichiarazioneDetTrovato.getDicdetId());
					deleTDichiarazioneDet.setUuid(UUID.randomUUID());
					deleTDichiarazioneDet.setNoteRevocaBlocco(dd.getNoteRevocaBlocco());
					deleTDichiarazioneDet.setDeleDEnumerazione(dd.getDeleDEnumerazione());
					deleTDichiarazioneDet.setNotaMotivazione(dd.getNotaMotivazione());
					setModifica(deleTDichiarazioneDet, deleTDichiarazioneDet.getLoginOperazione());


					checkStatiDettaglio(dd, deleTDichiarazioneDet.getDeleDDichiarazioneDetStato().getDicdetStatoCod(), METHOD_NAME);
					
					
					DeleDDichiarazioneDetStato deleDDichiarazioneDetStato = deleDDichiarazioneDetStatoRepository.ricercaDetStatoByDicDetStatoCod(dd.getDeleDDichiarazioneDetStato().getDicdetStatoCod());
					if (deleDDichciarazioneStato != null) {
						deleTDichiarazioneDet.setDeleDDichiarazioneDetStato(deleDDichiarazioneDetStato);
					}
				} else {
					String msg = "Nessun risultato trovato per l'UUID: " + dd.getUuid();
					log.error(METHOD_NAME, msg);
					throw new BeServiceException("DA.R02", msg);
				}
			}

		}

		em.merge(dichiarazioneBydb);

		return dichiarazioneBydb;
	}



	private boolean isApprovable(String stato) {
		return stato.equals(DicDetStatoCodEnum.DA_APPROVARE.name()) || stato.equals(DicDetStatoCodEnum.IN_ATTESA_DI_CONFERMA.name())
				|| stato.equals(DicDetStatoCodEnum.IN_ATTESA_DI_APPROVAZIONE.name()) || stato.equals(DicDetStatoCodEnum.RICHIESTA_RETTIFICATA.name())
				|| stato.equals(DicDetStatoCodEnum.IN_LAVORAZIONE.name());
	}

	private void checkStatiDettaglio(DeleTDichiarazioneDet dettaglioInput, String codStatoOutput, String METHOD_NAME) {

		if (dettaglioInput != null && dettaglioInput.getDeleDDichiarazioneDetStato() != null) {

			String codStatoInput = dettaglioInput.getDeleDDichiarazioneDetStato().getDicdetStatoCod();


			if (codStatoInput.equals(DicDetStatoCodEnum.DA_APPROVARE.name())) {
				String msg = "Stato dettaglio dichiarazione non ammissibile";
				log.error(METHOD_NAME, msg);
				throw new BeServiceException("DA.DCH09", msg);
			}

			final String ERROR_MSG = "Variazione di stato dettaglio dichiarazione " + codStatoOutput + " -> " + codStatoInput + " non consentita";


			if (( codStatoInput.equals(DicDetStatoCodEnum.VALIDA.name()) || codStatoInput.equals(DicDetStatoCodEnum.ATTIVA.name()) )  && !isApprovable(codStatoOutput)) {
				log.error(METHOD_NAME, ERROR_MSG);
				throw new BeServiceException("DA.DCH08", ERROR_MSG);
			}

			
			if (codStatoInput.equals(DicDetStatoCodEnum.REVOCATA.name()) && !(codStatoOutput.equals(DicDetStatoCodEnum.DA_APPROVARE.name()) || codStatoOutput.equals(DicDetStatoCodEnum.VALIDA.name()))) {
				log.error(METHOD_NAME, ERROR_MSG);
				throw new BeServiceException("DA.DCH08", ERROR_MSG);
			}

			
			if (codStatoInput.equals(DicDetStatoCodEnum.REVOCATA_BLOCCO.name()) && !(codStatoOutput.equals(DicDetStatoCodEnum.DA_APPROVARE.name()) || codStatoOutput.equals(DicDetStatoCodEnum.VALIDA.name()))) {
				log.error(METHOD_NAME, ERROR_MSG);
				throw new BeServiceException("DA.DCH08", ERROR_MSG);
			}

			
			if (codStatoInput.equals(DicDetStatoCodEnum.SCADUTA.name()) && !codStatoOutput.equals(DicDetStatoCodEnum.VALIDA.name())) {
				log.error(METHOD_NAME, ERROR_MSG);
				throw new BeServiceException("DA.DCH08", ERROR_MSG);
			}
		}
	}

	public List<DeleTDichiarazione> ricercaDichiarazioni(UUID uuid, String codFiscale1, String ruoloCit1, String codFiscale2,
																		  String ruoloCit2, List<String> tipoDichiarazione,
																		  List<String> modoDichiarazione, List<String> statoDichiarazione,
																		  Date dataInserimentoDa, Date dataInserimentoA, String ruoloCittadino,
																		  String compilatoreCF) {
		final String METHOD_NAME = "ricercaDichiarazioni";

		Map<String, Object> params = new HashMap<>();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT d FROM DeleTDichiarazione d ");
		sb.append("WHERE d.dataCancellazione IS NULL ");
		populateFilter(sb, params, uuid, codFiscale1, ruoloCit1, codFiscale2, ruoloCit2, tipoDichiarazione,
				modoDichiarazione, statoDichiarazione,dataInserimentoDa,dataInserimentoA,ruoloCittadino,compilatoreCF);
		sb.append("ORDER BY d.dataCreazione");
		
		String jpql = sb.toString();
		TypedQuery<DeleTDichiarazione> query = em.createQuery(jpql, DeleTDichiarazione.class);
		params.entrySet().stream().forEach(e -> {
			String key = e.getKey();
			Object value = e.getValue();
			query.setParameter(key, value);
			log.debug(METHOD_NAME, "Param: key: %s, value: %s", key, value);
		});
		List<DeleTDichiarazione> dichiarazioni = query.getResultList();
		
		


		Map<String,DeleTDichiarazione> mapDichiarazioni = new HashMap<>();
		for (DeleTDichiarazione dichiarazione : dichiarazioni) {
			StringBuilder sbKey = new StringBuilder();
			for (DeleTDichiarazioneDet dichiarazioneDett : dichiarazione.getDeleTDichiarazioneDets()) {
				sbKey.append(dichiarazioneDett.getDeleTCittadino1().getCitCf());
				sbKey.append(dichiarazioneDett.getDeleTCittadino2().getCitCf());
			}
			String key = sbKey.toString();
			mapDichiarazioni.put(key, dichiarazione);

		}
		return new ArrayList<>(mapDichiarazioni.values());
	}

	private void populateFilter(StringBuilder sb, Map<String, Object> params, UUID uuid, String codFiscale1,
										 String ruoloCit1, String codFiscale2, String ruoloCit2, List<String> tipoDichiarazione,
										 List<String> modoDichiarazione, List<String> statoDichiarazione,
										 Date dataInserimentoDa, Date dataInserimentoA, String ruoloCittadino,
										 String cittadinoCF) {
		
										 
		if (uuid != null) {
			sb.append("AND d.uuid = :uuid ");
			params.put("uuid", uuid);
			return;
		}

		boolean filterOnDetails = StringUtils.isNotEmpty(codFiscale1) || StringUtils.isNotEmpty(ruoloCit1) 
			|| StringUtils.isNotEmpty(codFiscale2) || StringUtils.isNotEmpty(ruoloCit2) 
			|| ((statoDichiarazione!= null) && !statoDichiarazione.isEmpty()) || dataInserimentoDa!=null || dataInserimentoA!=null
				|| StringUtils.isNotEmpty(ruoloCittadino);
		if (filterOnDetails) {
			sb.append("AND EXISTS (SELECT 1 FROM d.deleTDichiarazioneDets dd WHERE dd.dataCancellazione IS NULL ");


			if (StringUtils.isNotEmpty(codFiscale1)) {
				sb.append("AND dd.deleTCittadino1.citCf IN :codFiscale1 ");
				params.put("codFiscale1", codFiscale1);
			}
			if (StringUtils.isNotEmpty(ruoloCit1)) {
				sb.append("AND dd.deleDDichiarazioneRuolo1.dicRuoloCod = :ruoloCit1 ");
				params.put("ruoloCit1", ruoloCit1);
			}
			if (StringUtils.isNotEmpty(codFiscale2)) {
				sb.append("AND dd.deleTCittadino2.citCf IN :codFiscale2 ");
				params.put("codFiscale2", codFiscale2);
			}
			if (StringUtils.isNotEmpty(ruoloCit2)) {
				sb.append("AND dd.deleDDichiarazioneRuolo2.dicRuoloCod IN :ruoloCit2 ");
				params.put("ruoloCit2", ruoloCit2);
			}

			
			if(StringUtils.isNotEmpty(ruoloCittadino)){
				sb.append("AND (dd.deleDDichiarazioneRuolo1.dicRuoloCod IN :ruoloCittadino OR dd.deleDDichiarazioneRuolo2.dicRuoloCod IN :ruoloCittadino) ");
				params.put("ruoloCittadino", ruoloCittadino);
			}
			if(StringUtils.isNotEmpty(cittadinoCF)){
				sb.append("AND (dd.deleTCittadino1.citCf IN (:cittadinoCF) OR dd.deleTCittadino2.citCf IN (:cittadinoCF))");
				params.put("cittadinoCF", cittadinoCF);
			}
			sb.append(") ");
		}

		
		if (tipoDichiarazione != null) {
			sb.append("AND d.deleDDichiarazioneTipo.dicTipoCod IN (:tipoDichiarazione) ");
			params.put("tipoDichiarazione", tipoDichiarazione);
		}
		
		if (statoDichiarazione != null) {
				sb.append("AND d.deleDDichiarazioneStato.dicStatoCod IN (:statoDichiarazione) ");
				params.put("statoDichiarazione", statoDichiarazione);
		}
		

		if (modoDichiarazione != null) {
			sb.append("AND d.deleDDichiarazioneModo.dicModoCod IN (:modoDichiarazione) ");
			params.put("modoDichiarazione", modoDichiarazione);
		}

		if(dataInserimentoDa != null){
			
			sb.append("AND to_date(to_char(d.dataCreazione, 'yyyy-MM-dd'), 'yyyy-MM-dd')  >= to_date(:dataInserimentoDa, 'yyyy-MM-dd') ");
			params.put("dataInserimentoDa", dataInserimentoDa);
		}
		if(dataInserimentoA != null){
			
			sb.append("AND to_date(to_char(d.dataCreazione, 'yyyy-MM-dd'), 'yyyy-MM-dd')  <= to_date(:dataInserimentoA, 'yyyy-MM-dd') ");
			params.put("dataInserimentoA", dataInserimentoA);
		}
		

	}

	public DeleTDichiarazione ricercaDichiarazioniByUUID(UUID uuid) {
		final String METHOD_NAME = "ricercaDichiarazioniByUUID";

		Map<String, Object> params = new HashMap<>();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT tab FROM DeleTDichiarazione tab ");
		sb.append("WHERE tab.dataCancellazione IS NULL ");
		sb.append("AND tab.uuid = :uuid ");
		params.put("uuid", uuid);
		String jpql = sb.toString();
		TypedQuery<DeleTDichiarazione> query = em.createQuery(jpql, DeleTDichiarazione.class);
		params.entrySet().stream().forEach(e -> {
			String key = e.getKey();
			Object value = e.getValue();
			query.setParameter(key, value);
			log.debug(METHOD_NAME, "Param: key: %s, value: %s", key, value);
		});

		DeleTDichiarazione result;
		try {
			result = query.getSingleResult();
		} catch (NonUniqueResultException nure) {
    		String msg = "Trovato piu' di un risultato per l'UUID " + uuid;
    		log.error(METHOD_NAME, msg, nure);
    		throw new BeServiceException("DA.R01", msg);
    	} catch(NoResultException nre) {
    		String msg = "Nessun risultato trovato per l'UUID " + uuid;
    		log.error(METHOD_NAME, msg, nre);
			return null;
		}

		return result;
	}

	public void eliminaDichiarazione(DeleTDichiarazione dichiarazione, String loginOperazione) {

		setCancellazione(dichiarazione, loginOperazione);

		em.persist(dichiarazione);
	}


}