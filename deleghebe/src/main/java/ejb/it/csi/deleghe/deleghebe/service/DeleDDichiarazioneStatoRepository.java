/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;

import it.csi.deleghe.deleghebe.model.DeleDDichiarazioneStato;
import it.csi.deleghe.deleghebe.service.exception.BeServiceException;
import it.csi.deleghe.deleghebe.util.LogUtil;

@Stateless
public class DeleDDichiarazioneStatoRepository {

    @Inject
    private LogUtil log;

    @Inject
    private EntityManager em;
    
    public enum DicStatoCodEnum {
    	DA_COMPLETARE,
    	ATTIVA,
    	REVOCATA,
		BLOCCA,
    	SCADUTA,
		RIFIUTA,
		IN_ATTESA_DI_CONFERMA,
		VALIDA
    }

    public DeleDDichiarazioneStato ricercaDichiarazioneStatoByDicStatoCod(String dicStatoCod)  {
    	DicStatoCodEnum e;
    	try {
    		e = DicStatoCodEnum.valueOf(dicStatoCod);
    	} catch (IllegalArgumentException iae) {
    		throw new BeServiceException("DA.R03", "Codice stato dichiarazione ["+ dicStatoCod + "] non previsto");
   	}
    	
    	return ricercaDichiarazioneStatoByDicStatoCod(e);
    }
    
    public DeleDDichiarazioneStato ricercaDichiarazioneStatoByDicStatoCod(DicStatoCodEnum dicStatoCod)  {
    	final String METHOD_NAME = "ricercaServiziByDicStatoCod";
		log.debug(METHOD_NAME, "get service for: dicStatoCod=%s", dicStatoCod);

    	StringBuilder sb = new StringBuilder();
    	sb.append("SELECT d FROM DeleDDichiarazioneStato d ");
    	sb.append("WHERE d.dataCancellazione IS NULL ");
    			
    	Map<String, Object> params = new HashMap<>();
    	populateStatoFilter(sb, params, dicStatoCod.toString());
    	
    	String jpql = sb.toString();
    	log.debug(METHOD_NAME, "jpql: %s", jpql);
    	TypedQuery<DeleDDichiarazioneStato> query = em.createQuery(jpql, DeleDDichiarazioneStato.class);
    	params.entrySet().stream().forEach(e -> {
    		String key = e.getKey();
    		Object value = e.getValue();
    		query.setParameter(key, value);
    		log.debug(METHOD_NAME, "Param: key: %s, value: %s", key, value);
    	});
    	DeleDDichiarazioneStato result;
    	try {
    		result = query.getSingleResult();
    	} catch(NonUniqueResultException nure) {
    		String msg = "Trovato piu' di un risultato per il codice "+ dicStatoCod;
    		log.error(METHOD_NAME, msg, nure);
    		throw new BeServiceException("DA.R01", msg);
    	} catch(NoResultException nre) {
    		String msg = "Nessun risultato trovato per il codice "+ dicStatoCod;
    		log.error(METHOD_NAME, msg, nre);
    		throw new BeServiceException("DA.R02", msg);
    	}
    	log.debug(METHOD_NAME, "result statoId: %s", result.getDicStatoId());
    	return result;
    	
    }
	
    
    public List<DeleDDichiarazioneStato> ricercaStatiDichiarazione()  {
    	final String METHOD_NAME = "ricercaStatiDichiarazione";
		
    	StringBuilder sb = new StringBuilder();
    	sb.append("SELECT d FROM DeleDDichiarazioneStato d ");
    	sb.append("WHERE d.dataCancellazione IS NULL ");
    			
    	String jpql = sb.toString();
    	log.debug(METHOD_NAME, "jpql: %s", jpql);
    	TypedQuery<DeleDDichiarazioneStato> query = em.createQuery(jpql, DeleDDichiarazioneStato.class);
    	
    	List<DeleDDichiarazioneStato> result = query.getResultList();
    	
    	log.debug(METHOD_NAME, "result size: %s", result.size());
    	return result;
    	
    }
    
	private void populateStatoFilter(StringBuilder sb, Map<String, Object> params, String dicStatoCod) {
		if(StringUtils.isNotBlank(dicStatoCod)) {
			sb.append("AND d.dicStatoCod = :dicStatoCod ");
			params.put("dicStatoCod", dicStatoCod);
		}
	}
}
