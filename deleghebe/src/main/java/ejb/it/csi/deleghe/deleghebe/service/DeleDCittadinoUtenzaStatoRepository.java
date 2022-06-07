/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import it.csi.deleghe.deleghebe.model.DeleDCittadinoUtenzaStato;
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

public class DeleDCittadinoUtenzaStatoRepository {


   @Inject
   private LogUtil log;

   @Inject
   private EntityManager em;


   public DeleDCittadinoUtenzaStato ricercaServiziByCitUtenzaStatoCod(String citUtenzaStatoCod)  {
      final String METHOD_NAME = "ricercaServiziByCitUtenzaStatoCod";
      log.debug(METHOD_NAME, "get service for: citUtenzaStatoCod=%s", citUtenzaStatoCod);

      StringBuilder sb = new StringBuilder();
      sb.append("SELECT d FROM DeleDCittadinoUtenzaStato d ");
      sb.append("WHERE d.dataCancellazione IS NULL ");

      Map<String, Object> params = new HashMap<>();
      populateStatoFilter(sb, params, citUtenzaStatoCod.toString());

      String jpql = sb.toString();
      log.debug(METHOD_NAME, "jpql: %s", jpql);
      TypedQuery<DeleDCittadinoUtenzaStato> query = em.createQuery(jpql, DeleDCittadinoUtenzaStato.class);
      params.entrySet().stream().forEach(e -> {
         String key = e.getKey();
         Object value = e.getValue();
         query.setParameter(key, value);
         log.debug(METHOD_NAME, "Param: key: %s, value: %s", key, value);
      });

      DeleDCittadinoUtenzaStato result;
      try {
         result = query.getSingleResult();
      } catch(NonUniqueResultException nure) {
         String msg = "Trovato piu' di un risultato per il codice "+ citUtenzaStatoCod;
         log.error(METHOD_NAME, msg, nure);
         throw new BeServiceException("DA.R01", msg);
      } catch(NoResultException nre) {
         String msg = "Nessun risultato trovato per il codice "+ citUtenzaStatoCod;
         log.error(METHOD_NAME, msg, nre);
         throw new BeServiceException("DA.R02", msg);
      }
      return result;

   }

   private void populateStatoFilter(StringBuilder sb, Map<String, Object> params, String citUtenzaStatoCod) {
      if(StringUtils.isNotBlank(citUtenzaStatoCod)) {
         sb.append("AND d.citustatoCod = :citUtenzaStatoCod ");
         params.put("citUtenzaStatoCod", citUtenzaStatoCod);
      }
   }

}
