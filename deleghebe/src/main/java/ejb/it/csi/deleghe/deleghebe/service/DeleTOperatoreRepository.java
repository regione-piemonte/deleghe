/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;

import it.csi.deleghe.deleghebe.model.DeleTOperatore;
import it.csi.deleghe.deleghebe.service.exception.BeServiceException;
import it.csi.deleghe.deleghebe.util.LogUtil;
import it.csi.deleghe.deleghebe.ws.model.Delega;

public class DeleTOperatoreRepository {
	
	@Inject
    private LogUtil log;

    @Inject
    private EntityManager em;
    
   
    public DeleTOperatore ricercaCFOperatore(String  cf)  {
    	final String METHOD_NAME = "ricercaCFOperatore";

    	StringBuilder sb = new StringBuilder();
    	sb.append("SELECT o FROM DeleTOperatore o ");
    	
    	Map<String, Object> params = new HashMap<>();
    	populateWhere(sb, params, cf);
    			
    	String jpql = sb.toString();
    	log.debug(METHOD_NAME, "jpql: %s", jpql);
    	TypedQuery<DeleTOperatore> query = em.createQuery(jpql, DeleTOperatore.class);
    	params.entrySet().stream().forEach(e -> {
    		String key = e.getKey();
    		Object value = e.getValue();
    		query.setParameter(key, value);
    		log.debug(METHOD_NAME, "Param: key: %s, value: %s", key, value);
    	});
    	DeleTOperatore result;
    	try {
			result = query.getSingleResult();
		} catch (NonUniqueResultException nure) {
    		String msg = "Trovato piu' di un risultato per il cod. fiscale " + cf;
    		log.warn(METHOD_NAME, msg, nure);
    		throw new BeServiceException("DA.R01", msg);
    	} catch(NoResultException nre) {
    		String msg = "Nessun risultato trovato per il cod. fiscale " + cf;
    		log.warn(METHOD_NAME, msg, nre);
			return null;
		}
    	log.debug(METHOD_NAME, "Trovato cf nella tabella DeleTOperatore " + cf);
    	return result;
    	
    }
    
    private void populateWhere(StringBuilder sb, Map<String, Object> params, String codice) {
		if(StringUtils.isNotBlank(codice)) {
			sb.append("WHERE o.opCf IN (:codice) ");
			params.put("codice", codice);
		}
	}
    
	public String ricercaOperatoreValidoConAsl(String codiceFiscale) {
		final String METHOD_NAME = "ricercaOperatoreValidoConAsl";
		log.info(METHOD_NAME, "Entrato nel metodo" );
		
		String resultQuery = null;

		Map<String, Object> params = new HashMap<>();
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT asl.asl_code ");
		sb.append("FROM deleghe.dele_t_operatore op, dele_t_asl asl, dele_r_operatore_asl re ");
		sb.append("WHERE "); 
		sb.append("op.op_id = re.op_id AND "); 
		sb.append("re.asl_id = asl.asl_id AND "); 
		sb.append("op.op_cf  = '" +codiceFiscale+"' AND ");
		sb.append("now() between op.validita_inizio AND op.validita_fine ");

		String jpql = sb.toString();
		log.info(METHOD_NAME, "jpql: "+jpql );
		Query query = em.createNativeQuery(jpql);

		try {
		resultQuery = (String) query.getSingleResult();
		} catch(NoResultException nre) {
    		String msg = "Nessun risultato trovato.";
    		log.warn(METHOD_NAME, msg, nre);
			return null;
		}
		
		return resultQuery;
	}
    

}
