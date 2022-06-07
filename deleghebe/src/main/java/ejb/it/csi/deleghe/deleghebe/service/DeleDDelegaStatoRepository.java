/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import it.csi.deleghe.deleghebe.model.DeleDDelegaStato;
import it.csi.deleghe.deleghebe.model.DeleDDelegaTipo;
import it.csi.deleghe.deleghebe.service.exception.BeServiceException;
import it.csi.deleghe.deleghebe.util.LogUtil;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeleDDelegaStatoRepository {

   @Inject
   private LogUtil log;

   @Inject
   private EntityManager em;

   public DeleDDelegaStato ricercaDeleDDelegaStatoByStatoCod(String delStatoCod)  {
      final String METHOD_NAME = "ricercaDeleDDelegaStatoByStatoCod";
      log.debug(METHOD_NAME, "get delegation stato statoDelega for: statoDelega=%s", delStatoCod);

      StringBuilder sb = new StringBuilder();
      sb.append("SELECT d FROM DeleDDelegaStato d ");
      sb.append("WHERE d.dataCancellazione IS NULL ");

      Map<String, Object> params = new HashMap<>();
      populateStatoFilter(sb, params, delStatoCod);

      String jpql = sb.toString();
      log.debug(METHOD_NAME, "jpql: %s", jpql);
      TypedQuery<DeleDDelegaStato> query = em.createQuery(jpql, DeleDDelegaStato.class);
      params.entrySet().stream().forEach(e -> {
         String key = e.getKey();
         Object value = e.getValue();
         query.setParameter(key, value);
         log.debug(METHOD_NAME, "Param: key: %s, value: %s", key, value);
      });
      DeleDDelegaStato result;
      try {
         result = query.getSingleResult();
      } catch(NonUniqueResultException nure) {
         String msg = "Trovato piu' di un risultato per il codice "+ delStatoCod;
         log.error(METHOD_NAME, msg, nure);
         throw new BeServiceException("DA.R01", msg);
      } catch(NoResultException nre) {
         String msg = "Nessun risultato trovato per il codice "+ delStatoCod;
         log.error(METHOD_NAME, msg, nre);
         throw new BeServiceException("DA.R02", msg);
      }
      log.debug(METHOD_NAME, "result statoId: %s", result.getDelStatoId());
      return result;

   }

   
   public List<DeleDDelegaStato> ricercaStatiDelega()  {
      final String METHOD_NAME = "ricercaStatiDelega";
      
      StringBuilder sb = new StringBuilder();
      sb.append("SELECT d FROM DeleDDelegaStato d ");
      sb.append("WHERE d.dataCancellazione IS NULL ");

      String jpql = sb.toString();
      log.debug(METHOD_NAME, "jpql: %s", jpql);
      TypedQuery<DeleDDelegaStato> query = em.createQuery(jpql, DeleDDelegaStato.class);
      
      List<DeleDDelegaStato> result = query.getResultList();
      
      log.debug(METHOD_NAME, "result size: %s", result.size());
      return result;

   }
   
   private void populateStatoFilter(StringBuilder sb, Map<String, Object> params, String delStatoCod) {
      if(StringUtils.isNotBlank(delStatoCod)) {
         sb.append("AND d.delStatoCod = :delStatoCod ");
         params.put("delStatoCod", delStatoCod);
      }
   }

}
