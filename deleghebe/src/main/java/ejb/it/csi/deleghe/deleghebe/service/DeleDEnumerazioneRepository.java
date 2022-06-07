/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import it.csi.deleghe.deleghebe.model.DeleDEnumerazione;
import it.csi.deleghe.deleghebe.service.exception.BeServiceException;
import it.csi.deleghe.deleghebe.util.LogUtil;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.Map;

public class DeleDEnumerazioneRepository {

   @Inject
   private LogUtil log;

   @Inject
   private EntityManager em;

   public DeleDEnumerazione ricercaDeleDEnumerazioneByEnumCod(String enumCod)  {
      final String METHOD_NAME = "ricercaDeleDEnumerazioneByEnumCod";
      log.debug(METHOD_NAME, "get enumeration for: enumCod=%s", enumCod);

      StringBuilder sb = new StringBuilder();
      sb.append("SELECT d FROM DeleDEnumerazione d ");
      sb.append("WHERE d.dataCancellazione IS NULL ");

      Map<String, Object> params = new HashMap<>();
      populateStatoFilter(sb, params, enumCod);

      String jpql = sb.toString();
      log.debug(METHOD_NAME, "jpql: %s", jpql);
      TypedQuery<DeleDEnumerazione> query = em.createQuery(jpql, DeleDEnumerazione.class);
      params.entrySet().stream().forEach(e -> {
         String key = e.getKey();
         Object value = e.getValue();
         query.setParameter(key, value);
         log.debug(METHOD_NAME, "Param: key: %s, value: %s", key, value);
      });
      DeleDEnumerazione result;
      try {
         result = query.getSingleResult();
      } catch(NonUniqueResultException nure) {
         String msg = "Trovato piu' di un risultato per il codice "+ enumCod;
         log.error(METHOD_NAME, msg, nure);
         throw new BeServiceException("DA.R01", msg);
      } catch(NoResultException nre) {
         String msg = "Nessun risultato trovato per il codice "+ enumCod;
         log.error(METHOD_NAME, msg, nre);
         throw new BeServiceException("DA.R02", msg);
      }
      log.debug(METHOD_NAME, "result enumCod: %s", result.getEnumCod());
      return result;

   }

   private void populateStatoFilter(StringBuilder sb, Map<String, Object> params, String enumCod) {
      if(StringUtils.isNotBlank(enumCod)) {
         sb.append("AND d.enumCod = :enumCod ");
         params.put("enumCod", enumCod);
      }
   }
}
