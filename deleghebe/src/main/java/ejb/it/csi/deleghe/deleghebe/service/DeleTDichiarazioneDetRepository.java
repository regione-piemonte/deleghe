/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;

import it.csi.deleghe.deleghebe.model.DeleTDichiarazioneDet;
import it.csi.deleghe.deleghebe.service.DeleDDichiarazioneDetStatoRepository.DicDetStatoCodEnum;
import it.csi.deleghe.deleghebe.service.exception.BeServiceException;
import it.csi.deleghe.deleghebe.util.LogUtil;

@Stateless
public class DeleTDichiarazioneDetRepository extends BaseRepository {

	@Inject
	private LogUtil log;

	@Inject
	private EntityManager em;

	public DeleTDichiarazioneDet ricercaDichiarazioneDetByUUID(UUID uuid) {
		final String METHOD_NAME = "ricercaDichiarazioneDetByUUID";
		log.debug(METHOD_NAME, "UUID:" + uuid);

		Map<String, Object> params = new HashMap<>();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT tab FROM DeleTDichiarazioneDet tab ");
		sb.append("WHERE tab.dataCancellazione IS NULL ");
		sb.append("AND tab.uuid = :uuid ");
		params.put("uuid", uuid);
		String jpql = sb.toString();
		TypedQuery<DeleTDichiarazioneDet> query = em.createQuery(jpql, DeleTDichiarazioneDet.class);
		params.entrySet().stream().forEach(e -> {
			String key = e.getKey();
			Object value = e.getValue();
			query.setParameter(key, value);
			log.debug(METHOD_NAME, "Param: key: %s, value: %s", key, value);
		});

		DeleTDichiarazioneDet result;
		try {
			result = query.getSingleResult();
		} catch (NonUniqueResultException nure) {
			String msg = "Trovato piu' di un risultato per l'UUID " + uuid;
			log.error(METHOD_NAME, msg, nure);
			throw new BeServiceException("DA.R01", msg);
		} catch (NoResultException nre) {
			String msg = "Nessun risultato trovato per l'UUID " + uuid;
			log.error(METHOD_NAME, msg, nre);
			return null;
		}

		return result;
	}

	public DeleTDichiarazioneDet ricercaDichiarazioneDetByCF(String citId1, String citId2) {
		final String METHOD_NAME = "ricercaDichiarazioneDetByCF";
		log.debug(METHOD_NAME, "CF ID 1:" + citId1);
		log.debug(METHOD_NAME, "CF ID 2:" + citId2);

		Map<String, Object> params = new HashMap<>();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT d FROM DeleTDichiarazioneDet d ");
		sb.append("WHERE d.dataCancellazione IS NULL ");
		if (StringUtils.isNotEmpty(citId1)) {
			sb.append("AND d.deleTCittadino1.citCf = :cf1 ");
			params.put("cf1", citId1);
		}
		if (StringUtils.isNotEmpty(citId2)) {
			sb.append("AND d.deleTCittadino2.citCf = :cf2 ");
			params.put("cf2", citId2);
		}
		sb.append("AND d.deleDDichiarazioneDetStato.dicdetStatoCod IN (:dicdetStatoCod) ");
		params.put("dicdetStatoCod", Arrays.asList(DicDetStatoCodEnum.DA_APPROVARE.name(), DicDetStatoCodEnum.VALIDA.name()));
		
		String jpql = sb.toString();
		TypedQuery<DeleTDichiarazioneDet> query = em.createQuery(jpql, DeleTDichiarazioneDet.class);
		params.entrySet().stream().forEach(e -> {
			String key = e.getKey();
			Object value = e.getValue();
			query.setParameter(key, value);
			log.debug(METHOD_NAME, "Param: key: %s, value: %s", key, value);
		});

		DeleTDichiarazioneDet result;
		try {
			result = query.getSingleResult();
		} catch (NonUniqueResultException nure) {
			String msg = "Trovato piu' di un risultato per i CF [" + citId1 + "," + citId2 + "]";
			log.error(METHOD_NAME, msg, nure);
			throw new BeServiceException("DA.R01", msg);
		} catch (NoResultException nre) {
			String msg = "Nessun risultato trovato per per i CF [" + citId1 + "," + citId2 + "]";
			log.error(METHOD_NAME, msg, nre);
			return null;
		}

		return result;
	}

	public List<DeleTDichiarazioneDet> ricercaDichiarazioneDetByStato(String stato) {
		return ricercaDichiarazioneDetByStato(Arrays.asList(stato));
	}
	public List<DeleTDichiarazioneDet> ricercaDichiarazioneDetByStato(List<String> stati) {
		final String METHOD_NAME = "ricercaDichiarazioneDetByStato";

		Map<String, Object> params = new HashMap<>();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT tab FROM DeleTDichiarazioneDet tab ");
		sb.append("WHERE tab.dataCancellazione IS NULL ");
		sb.append("AND tab.deleDDichiarazioneDetStato.dicdetStatoCod IN (:stati) ");
		params.put("stati", stati);
		String jpql = sb.toString();
		TypedQuery<DeleTDichiarazioneDet> query = em.createQuery(jpql, DeleTDichiarazioneDet.class);
		params.entrySet().stream().forEach(e -> {
			String key = e.getKey();
			Object value = e.getValue();
			query.setParameter(key, value);
			log.debug(METHOD_NAME, "Param: key: %s, value: %s", key, value);
		});

		List<DeleTDichiarazioneDet> result = query.getResultList();

		return result;
	}
}