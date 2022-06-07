/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.csi.deleghe.deleghebe.model.CsiLogAudit;
import it.csi.deleghe.deleghebe.util.LogUtil;

@Stateless
public class CsiLogAuditRepository {

	@Inject
	private LogUtil log;

	@Inject
	private EntityManager em;

	public void inserisciLog(CsiLogAudit cisLogAudit) {

		em.persist(cisLogAudit);
	}

	public CsiLogAudit getAuditID() {

		TypedQuery<Integer> query = em.createQuery("SELECT max(auditId) FROM CsiLogAudit tab ", Integer.class);
		Integer key = query.getSingleResult();
		return em.find(CsiLogAudit.class, key);
	}

	public CsiLogAudit getUUID(UUID uuid) {
		final String METHOD_NAME = "getUUID";

		Map<String, Object> params = new HashMap<>();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT tab FROM CsiLogAudit tab ");
		sb.append("WHERE tab.uuid = :uuid ");
		params.put("uuid", uuid);
		String jpql = sb.toString();
		TypedQuery<CsiLogAudit> query = em.createQuery(jpql, CsiLogAudit.class);
		params.entrySet().stream().forEach(e -> {
			String key = e.getKey();
			Object value = e.getValue();
			query.setParameter(key, value);
			log.debug(METHOD_NAME, "Param: key: %s, value: %s", key, value);
		});

		CsiLogAudit result = query.getSingleResult();

		return result;
	}

}
