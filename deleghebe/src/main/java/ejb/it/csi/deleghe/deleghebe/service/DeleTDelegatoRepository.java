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

import it.csi.deleghe.deleghebe.model.DeleTDelegato;
import it.csi.deleghe.deleghebe.service.exception.BeServiceException;
import it.csi.deleghe.deleghebe.util.LogUtil;
import it.csi.deleghe.deleghebe.util.LogUtil.ToLog;

@Stateless
public class DeleTDelegatoRepository extends BaseRepository {

    @Inject
    private LogUtil log;

    @Inject
    private EntityManager em;
    

    public DeleTDelegato ricercaDelegatoByDelega (Integer dlgaId)  {
    	final String METHOD_NAME = "ricercaDelegatoByDelega";
    	if (dlgaId == null) {
    		String msg = "Id delega non valorizzato";
			log.error(METHOD_NAME, msg);
    		throw new BeServiceException("DA.I01", msg);
    	}
    	
    	Map<String, Object> params = new HashMap<>();
    	StringBuilder sb = new StringBuilder();
    	sb.append("SELECT tab FROM DeleTDelegato tab ");
    	sb.append("WHERE tab.dataCancellazione IS NULL ");
    	sb.append("AND tab.deleTDelega.dlgaId = :dlgaId ");
    	params.put("dlgaId", dlgaId);
    	String jpql = sb.toString();
    	TypedQuery<DeleTDelegato> query = em.createQuery(jpql, DeleTDelegato.class);
    	params.entrySet().stream().forEach(e -> {
    		String key = e.getKey();
    		Object value = e.getValue();
    		query.setParameter(key, value);
    		log.debug(METHOD_NAME, "Param: key: %s, value: %s", key, value);
    	});

    	DeleTDelegato result;
    	try {
			result = query.getSingleResult();
		} catch (NonUniqueResultException nure) {
    		String msg = "Trovato piu' di un risultato per l'id " + dlgaId;
    		log.error(METHOD_NAME, msg, nure);
    		throw new BeServiceException("DA.R01", msg);
    	} catch(NoResultException nre) {
    		String msg = "Nessun risultato trovato per l'id " + dlgaId;
    		log.error(METHOD_NAME, msg, nre);
			return null;
		}
    	
    	return result;
    }
    
    public DeleTDelegato ricercaDelegato(String citCf)  {
    	final String METHOD_NAME = "ricercaDelegato";
    	
    	if (StringUtils.isEmpty(citCf)) {
    		String msg = "Codice fiscale cittadino non valorizzato";
			log.error(METHOD_NAME, msg);
    		throw new BeServiceException("DA.I01", msg);
    	}
    	
    	Map<String, Object> params = new HashMap<>();
    	StringBuilder sb = new StringBuilder();
    	sb.append("SELECT tab FROM DeleTDelegato tab ");
    	sb.append("WHERE tab.dataCancellazione IS NULL ");
    	sb.append("AND tab.dlgoCf = :citCF ");
    	params.put("citCF", citCf);
    	String jpql = sb.toString();
    	TypedQuery<DeleTDelegato> query = em.createQuery(jpql, DeleTDelegato.class);
    	params.entrySet().stream().forEach(e -> {
    		String key = e.getKey();
    		Object value = e.getValue();
    		query.setParameter(key, value);
    		log.debug(METHOD_NAME, "Param: key: %s, value: %s", key, value);
    	});

    	DeleTDelegato result;
    	try {
			result = query.getSingleResult();
		} catch (NonUniqueResultException nure) {
    		String msg = "Trovato piu' di un risultato per il codice fiscale " + citCf;
    		log.error(METHOD_NAME, msg, nure);
    		throw new BeServiceException("DA.R01", msg);
    	} catch(NoResultException nre) {
    		String msg = "Nessun risultato trovato per il codice fiscale " + citCf;
    		log.error(METHOD_NAME, msg, nre);
			return null;
		}
    	
    	return result;
    }
    
    public DeleTDelegato inserisciDelegato(DeleTDelegato delegato, String loginOperazione){
    	final String METHOD_NAME = "inserisciDelegato";
    	
    	log.debug(METHOD_NAME, "inserisci delegato per CF: %s", (ToLog) () -> (delegato != null)? delegato.getDlgoCf() : "null");
    	delegato.setDlgoId(null);
    	setCreazione(delegato, loginOperazione);
    	
    	em.persist(delegato);
    	return delegato;
    }
    
    public DeleTDelegato aggiornaDelegato(DeleTDelegato delegato, String loginOperazione) {
    	final String METHOD_NAME = "aggiornaDelegato";
    	if (delegato == null) {
    		String msg = "Delegato non valorizzato";
			log.error(METHOD_NAME, msg);
    		throw new BeServiceException("DA.I01", msg);
    	}
    	
    	log.debug(METHOD_NAME, "aggiorna delegato per CF: %s", (ToLog) () -> (delegato != null)? delegato.getDlgoCf() : "null");
    	
    	setModifica(delegato, loginOperazione);
    	
    	em.merge(delegato);
    	return delegato;
    }
   

}
