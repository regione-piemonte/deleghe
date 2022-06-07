/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import java.util.Calendar;
import java.util.Date;
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

import it.csi.deleghe.deleghebe.model.DeleRServizioDelegaTipoGrado;
import it.csi.deleghe.deleghebe.util.LogUtil;

@Stateless
public class DeleRServizioDelegaTipoGradoRepository extends BaseRepository {

	
	 @Inject
	    private LogUtil log;

	    @Inject
	    private EntityManager em;
	    
	    
	    public DeleRServizioDelegaTipoGrado ricercaGradoAssegnatoAlServizioByID(Integer solGradoId)  {
	    	final String METHOD_NAME = "ricercaGradoAssegnatoAlServizioByID";
	    	
	    	if(solGradoId == null) {
	    		String msg = "solGradoId non valorizzato";
				log.error(METHOD_NAME, msg);
	    		throw new NoResultException(msg);
	    	}
	    	
	    	Map<String, Object> params = new HashMap<>();
	    	StringBuilder sb = new StringBuilder();
	    	
	    	sb.append("SELECT tab FROM DeleRServizioDelegaTipoGrado tab ");
	    	sb.append("WHERE tab.dataCancellazione IS NULL ");
	    	sb.append("AND tab.solGradoId = :solGradoId ");
	    	params.put("solGradoId", solGradoId);
	    	
	    	String jpql = sb.toString();
	    	TypedQuery<DeleRServizioDelegaTipoGrado> query = em.createQuery(jpql, DeleRServizioDelegaTipoGrado.class);
	    	params.entrySet().stream().forEach(e -> {
	    		String key = e.getKey();
	    		Object value = e.getValue();
	    		query.setParameter(key, value);
	    		log.debug(METHOD_NAME, "Param: key: %s, value: %s", key, value);
	    	});

	    	DeleRServizioDelegaTipoGrado result;
	    	try {
				result = query.getSingleResult();
			} catch (NonUniqueResultException nure) {
				String msg = "Trovato piu' di un risultato per il solGradoId :" + solGradoId;
				log.error(METHOD_NAME, msg, nure, new Object[] {});
				throw new IllegalArgumentException(msg);
			} catch (NoResultException nre) {
				String msg = "Nessun risultato trovato per il solGradoId :" + solGradoId;
				log.debug(METHOD_NAME, msg, nre, new Object[] {});
				return null;
			}
	    	
	    	return result;
	    }
	    
	    
	    public DeleRServizioDelegaTipoGrado ricercaGradoAssegnatoAlServizio(Integer delTipId, Integer serId)  {
	    	final String METHOD_NAME = "ricercaGradoAssegnatoAlServizioByID";
	    	
	    	if(delTipId == null) {
	    		String msg = "delTipId non valorizzato";
				log.error(METHOD_NAME, msg);
	    		throw new NoResultException(msg);
	    	}
	    	if(serId == null) {
	    		String msg = "serId non valorizzato";
				log.error(METHOD_NAME, msg);
	    		throw new NoResultException(msg);
	    	}
	    	
	    	Map<String, Object> params = new HashMap<>();
	    	StringBuilder sb = new StringBuilder();
	    	
	    	sb.append("SELECT tab FROM DeleRServizioDelegaTipoGrado tab ");
	    	sb.append("WHERE tab.dataCancellazione IS NULL ");
	    	populateFilter(sb, params, delTipId, serId);
	    	
	    	
	    	String jpql = sb.toString();
	    	TypedQuery<DeleRServizioDelegaTipoGrado> query = em.createQuery(jpql, DeleRServizioDelegaTipoGrado.class);
	    	params.entrySet().stream().forEach(e -> {
	    		String key = e.getKey();
	    		Object value = e.getValue();
	    		query.setParameter(key, value);
	    		log.debug(METHOD_NAME, "Param: key: %s, value: %s", key, value);
	    	});

	    	DeleRServizioDelegaTipoGrado result;
	    	try {
				result = query.getSingleResult();
			} catch (NonUniqueResultException nure) {
				String msg = "Trovato piu' di un risultato per ricercaGradoAssegnatoAlServizio";
				log.error(METHOD_NAME, msg, nure, new Object[] {});
				throw new IllegalArgumentException(msg);
			} catch (NoResultException nre) {
				String msg = "Nessun risultato trovato per ricercaGradoAssegnatoAlServizio";
				log.debug(METHOD_NAME, msg, nre, new Object[] {});
				return null;
			}
	    	
	    	return result;
	    }
	    
	    
	    public List<DeleRServizioDelegaTipoGrado> ricercaListaGradiAssegnatiAiServizi(DeleRServizioDelegaTipoGrado deleRServizioDelegaTipoGrado)  {
	    	final String METHOD_NAME = "ricercaListaGradiAssegnatiAiServizi";
	    	
	    	
	    	Map<String, Object> params = new HashMap<>();
	    	StringBuilder sb = new StringBuilder();
	    	
	    	sb.append("SELECT tab FROM DeleRServizioDelegaTipoGrado tab ");
	    	sb.append("WHERE tab.dataCancellazione IS NULL ");
	    	
	    	populateFilter(sb, params, deleRServizioDelegaTipoGrado);
	    	
	    	String jpql = sb.toString();
	    	TypedQuery<DeleRServizioDelegaTipoGrado> query = em.createQuery(jpql, DeleRServizioDelegaTipoGrado.class);
	    	params.entrySet().stream().forEach(e -> {
	    		String key = e.getKey();
	    		Object value = e.getValue();
	    		query.setParameter(key, value);
	    		log.debug(METHOD_NAME, "Param: key: %s, value: %s", key, value);
	    	});

	    	List<DeleRServizioDelegaTipoGrado> result = query.getResultList();
	    	
	    	return result;
	    }
	    

	    private void populateFilter(StringBuilder sb, Map<String, Object> params, DeleRServizioDelegaTipoGrado deleRServizioDelegaTipoGrado) {
			
	    	if(deleRServizioDelegaTipoGrado!=null) {
				
				if(deleRServizioDelegaTipoGrado.getSolGradoId()!=null) {
					sb.append("AND tab.solGradoId = :solGradoId ");
			    	params.put("solGradoId", deleRServizioDelegaTipoGrado.getSolGradoId());
				}
				if(deleRServizioDelegaTipoGrado.getValiditaInizio()!=null) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(deleRServizioDelegaTipoGrado.getValiditaInizio());
					cal.set(Calendar.HOUR_OF_DAY, 0);
			        cal.set(Calendar.MINUTE, 0);
			        cal.set(Calendar.SECOND, 0);
			        cal.set(Calendar.MILLISECOND, 0);
				
					sb.append("AND tab.dataCreazione = :dob ");
			    	params.put("dob",  cal.getTime());
				}
				if(deleRServizioDelegaTipoGrado.getDataCreazione()!=null) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(deleRServizioDelegaTipoGrado.getDataCreazione());
						cal.set(Calendar.HOUR_OF_DAY, 0);
				        cal.set(Calendar.MINUTE, 0);
				        cal.set(Calendar.SECOND, 0);
				        cal.set(Calendar.MILLISECOND, 0);
					
						sb.append("AND tab.dataCreazione = :dob ");
				    	params.put("dob",  cal.getTime());
				}				
			}	    				
		}
	    
	    private void populateFilter(StringBuilder sb, Map<String, Object> params, Integer delTipId, Integer serId) {
					
					if (serId!=null ) {
					sb.append("AND EXISTS (SELECT 1 FROM tab.deleDServizio dd WHERE dd.dataCancellazione IS NULL ");					
						
						if (serId!=null) {
						sb.append("AND dd.serId = :serId )");
						params.put("serId", serId);
						}
					}
					if (delTipId!=null ) {
					sb.append("AND EXISTS (SELECT 1 FROM tab.deleDDelegaTipo dt WHERE dt.dataCancellazione IS NULL ");					
						
						if (delTipId!=null) {
						sb.append("AND dt.delTipId = :delTipId )");
						params.put("delTipId", delTipId);
						}

					}	
					
					
		}
	    
	    
	    public DeleRServizioDelegaTipoGrado inserisciGradiAssegnatiAiServizi(DeleRServizioDelegaTipoGrado deleRServizioDelegaTipoGrado, String loginOperazione){
	    	final String METHOD_NAME = "inserisciGradiAssegnatiAiServizi";

	    	deleRServizioDelegaTipoGrado.setSolGradoId(null);
	    	setCreazione(deleRServizioDelegaTipoGrado, loginOperazione);
	    	
	    	em.persist(deleRServizioDelegaTipoGrado);
	    	return deleRServizioDelegaTipoGrado;
	    }
	    
	    
	    public DeleRServizioDelegaTipoGrado aggiornaGradiAssegnatiAiServizi(DeleRServizioDelegaTipoGrado deleRServizioDelegaTipoGrado, String loginOperazione){
	    	final String METHOD_NAME = "aggiornaGradiAssegnatiAiServizi";
	    	
	    	DeleRServizioDelegaTipoGrado gradoBydb=em.find(DeleRServizioDelegaTipoGrado.class,deleRServizioDelegaTipoGrado.getSolGradoId());
	    	
	    	if(gradoBydb == null) {
	    		String msg = "gradoBydb non valorizzato";
				log.error(METHOD_NAME, msg);
	    		throw new NoResultException(msg);
	    	}

	    	gradoBydb.setSolGradoId(deleRServizioDelegaTipoGrado.getSolGradoId());
	    	setModifica(gradoBydb, loginOperazione);
	    	
	    	em.merge(gradoBydb);
	    	
	    	return gradoBydb;
	    }
}

