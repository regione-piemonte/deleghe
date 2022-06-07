/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

import it.csi.deleghe.deleghebe.model.DeleTCittadino;
import it.csi.deleghe.deleghebe.util.LogUtil;
import it.csi.deleghe.deleghebe.util.LogUtil.ToLog;

@Stateless
public class DeleTCittadinoRepository extends BaseRepository {

    @Inject
    private LogUtil log;

    @Inject
    private EntityManager em;
    
    
    public DeleTCittadino ricercaCittadino(String citCf)  {
    	final String METHOD_NAME = "ricercaCittadino";
    	
    	if(citCf == null || citCf.isEmpty()) {
    		String msg = "Codice fiscale cittadino non valorizzato";
			log.error(METHOD_NAME, msg);
    		throw new NoResultException(msg);
    	}
    	
    	Map<String, Object> params = new HashMap<>();
    	StringBuilder sb = new StringBuilder();
    	sb.append("SELECT tab FROM DeleTCittadino tab ");
    	sb.append("WHERE tab.dataCancellazione IS NULL ");
    	sb.append("AND tab.citCf = :citCF ");
    	params.put("citCF", citCf);
    	String jpql = sb.toString();
    	TypedQuery<DeleTCittadino> query = em.createQuery(jpql, DeleTCittadino.class);
    	params.entrySet().stream().forEach(e -> {
    		String key = e.getKey();
    		Object value = e.getValue();
    		query.setParameter(key, value);
    		log.debug(METHOD_NAME, "Param: key: %s, value: %s", key, value);
    	});

    	DeleTCittadino result;
    	try {
			result = query.getSingleResult();
		} catch (NonUniqueResultException nure) {
			String msg = "Trovato piu' di un risultato per il codice fiscale :" + citCf;
			log.error(METHOD_NAME, msg, nure, new Object[] {});
			throw new IllegalArgumentException(msg);
		} catch (NoResultException nre) {
			String msg = "Nessun risultato trovato per il codicefiscale :" + citCf;
			log.debug(METHOD_NAME, msg, nre, new Object[] {});
//			throw new IllegalArgumentException(msg);
			return null;
		}
    	
    	return result;
    }
    
    
    public List<DeleTCittadino> ricercaCittadino(DeleTCittadino cittadino)  {
    	final String METHOD_NAME = "ricercaCittadino";
    	
    	
    	Map<String, Object> params = new HashMap<>();
    	StringBuilder sb = new StringBuilder();
    	sb.append("SELECT tab FROM DeleTCittadino tab ");
    	sb.append("WHERE tab.dataCancellazione IS NULL ");
    	populateFilter(sb, params, cittadino);
    	
    	String jpql = sb.toString();
    	TypedQuery<DeleTCittadino> query = em.createQuery(jpql, DeleTCittadino.class);
    	params.entrySet().stream().forEach(e -> {
    		String key = e.getKey();
    		Object value = e.getValue();
    		query.setParameter(key, value);
    		log.debug(METHOD_NAME, "Param: key: %s, value: %s", key, value);
    	});

    	List<DeleTCittadino> result = query.getResultList();
    	
    	return result;
    }
    

    private void populateFilter(StringBuilder sb, Map<String, Object> params, DeleTCittadino cittadino) {
		
    	if(cittadino!=null) {
			
			if(cittadino.getCitCf()!=null) {
				sb.append("AND tab.citCf = :codFiscale ");
		    	params.put("codFiscale", cittadino.getCitCf());
			}
			if(cittadino.getCitCognome()!=null) {
				sb.append("AND LOWER(tab.citCognome) = LOWER(:cognome) "); 
		    	params.put("cognome", cittadino.getCitCognome());
			}
			if(cittadino.getCitNome()!=null) {
				sb.append("AND LOWER(tab.citNome) = LOWER(:nome) ");
		    	params.put("nome", cittadino.getCitNome());
			}
			if(cittadino.getCitNascitaData()!=null) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(cittadino.getCitNascitaData());
					cal.set(Calendar.HOUR_OF_DAY, 0);
			        cal.set(Calendar.MINUTE, 0);
			        cal.set(Calendar.SECOND, 0);
			        cal.set(Calendar.MILLISECOND, 0);
				
					sb.append("AND tab.citNascitaData = :dob ");
			    	params.put("dob",  cal.getTime());
			}
			if(cittadino.getCitNascitaComune()!=null) {
				sb.append("AND LOWER(tab.citNascitaComune) = LOWER(:comuneNascita) ");
		    	params.put("comuneNascita", cittadino.getCitNascitaComune());
			}
			if(cittadino.getCitSesso()!=null) {
				sb.append("AND LOWER(tab.citSesso) = LOWER(:sesso) ");
		    	params.put("sesso", cittadino.getCitSesso());
			}
		}
    	
    	

		
	}
    
    
    public DeleTCittadino inserisciCittadino(DeleTCittadino cittadino, String loginOperazione){
    	final String METHOD_NAME = "inserisciCittadino";
    	log.debug(METHOD_NAME, "inserisci cittadino for: citCf =%s", (ToLog) () -> {
    				return cittadino != null ? cittadino.getCitCf() : "null";
		});
    	cittadino.setCitId(null);
    	setCreazione(cittadino, loginOperazione);
    	
    	em.persist(cittadino);
    	return cittadino;
    }
    
    public DeleTCittadino aggiornaCittadino(DeleTCittadino cittadino, String loginOperazione){
    	final String METHOD_NAME = "aggiornaCittadino";
    	log.debug(METHOD_NAME, "aggiorna cittadino for: citCf =%s", (ToLog) () -> {
    				return cittadino != null ? cittadino.getCitCf() : "null";
		});
    	

    	DeleTCittadino cittBydb=em.find(DeleTCittadino.class,cittadino.getCitId());
    	
    	if(cittBydb == null) {
    		String msg = "Cittadino non valorizzato";
			log.error(METHOD_NAME, msg);
    		throw new NoResultException(msg);
    	}

    	cittBydb.setCitAuraid(cittadino.getCitAuraid());
    	setModifica(cittBydb, loginOperazione);
    	
    	em.merge(cittBydb);
    	
    	return cittBydb;
    }
   

}
