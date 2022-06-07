/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;

import it.csi.deleghe.deleghebe.model.DeleDDichiarazioneRuolo;
import it.csi.deleghe.deleghebe.service.exception.BeServiceException;
import it.csi.deleghe.deleghebe.util.LogUtil;

@Stateless
public class DeleDDichiarazioneRuoloRepository {

    @Inject
    private LogUtil log;

    @Inject
    private EntityManager em;
    
    public enum DicRuoloCodEnum {
    	GENITORE_1,
    	GENITORE_2,
    	FIGLIO,
    	TUTORE,
    	TUTELATO,
		TUTORE_DI_UN_MAGIORE_INTER
    }

    public DeleDDichiarazioneRuolo ricercaServiziByDicRuoloCod(String dicRuoloCod)  {
    	DicRuoloCodEnum e;
    	try {
    		e = DicRuoloCodEnum.valueOf(dicRuoloCod);
    	} catch (IllegalArgumentException iae) {
    		throw new BeServiceException("DA.R03", "Codice ruolo cittadino in dichiarazione ["+ dicRuoloCod + "] non previsto");
    	}
    	
    	return ricercaServiziByDicRuoloCod(e);
    }
    
    public DeleDDichiarazioneRuolo ricercaServiziByDicRuoloCod(DicRuoloCodEnum dicRuoloCod)  {
    	final String METHOD_NAME = "ricercaServiziByDicRuoloCod";
		log.debug(METHOD_NAME, "get service for: dicRuoloCod=%s", dicRuoloCod);

    	StringBuilder sb = new StringBuilder();
    	sb.append("SELECT d FROM DeleDDichiarazioneRuolo d ");
    	sb.append("WHERE d.dataCancellazione IS NULL ");
    			
    	Map<String, Object> params = new HashMap<>();
    	populateStatoFilter(sb, params, dicRuoloCod.toString());
    	
    	String jpql = sb.toString();
    	log.debug(METHOD_NAME, "jpql: %s", jpql);
    	TypedQuery<DeleDDichiarazioneRuolo> query = em.createQuery(jpql, DeleDDichiarazioneRuolo.class);
    	params.entrySet().stream().forEach(e -> {
    		String key = e.getKey();
    		Object value = e.getValue();
    		query.setParameter(key, value);
    		log.debug(METHOD_NAME, "Param: key: %s, value: %s", key, value);
    	});
    	DeleDDichiarazioneRuolo result;
    	try {
    		result = query.getSingleResult();
    	} catch(NonUniqueResultException nure) {
    		String msg = "Trovato piu' di un risultato per il codice "+ dicRuoloCod;
    		log.error(METHOD_NAME, msg, nure);
    		throw new BeServiceException("DA.R01", msg);
    	} catch(NoResultException nre) {
    		String msg = "Nessun risultato trovato per il codice "+ dicRuoloCod;
    		log.error(METHOD_NAME, msg, nre);
    		throw new BeServiceException("DA.R02", msg);
    	}
    	log.debug(METHOD_NAME, "result ruoloId: %s", result.getDicRuoloId());
    	return result;
    	
    }
	
	private void populateStatoFilter(StringBuilder sb, Map<String, Object> params, String dicRuoloCod) {
		if(StringUtils.isNotBlank(dicRuoloCod)) {
			sb.append("AND d.dicRuoloCod = :dicRuoloCod ");
			params.put("dicRuoloCod", dicRuoloCod);
		}
	}
}
