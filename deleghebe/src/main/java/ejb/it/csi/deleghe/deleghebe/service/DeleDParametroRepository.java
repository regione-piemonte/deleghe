/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.csi.deleghe.deleghebe.model.DeleDParametro;
import it.csi.deleghe.deleghebe.util.LogUtil;

@Stateless
public class DeleDParametroRepository extends BaseRepository {

    @Inject
    private LogUtil log;
    @Inject
    private EntityManager em;
    
    public List<DeleDParametro> ricercaParametri ()  {
    	final String METHOD_NAME = "ricercaParametri";

    	StringBuilder sb = new StringBuilder();
    	sb.append("SELECT d FROM DeleDParametro d ");
    	sb.append("WHERE d.dataCancellazione IS NULL ");
    			
    	String jpql = sb.toString();
    	log.debug(METHOD_NAME, "jpql: %s", jpql);
    	TypedQuery<DeleDParametro> query = em.createQuery(jpql, DeleDParametro.class);

    	return query.getResultList();
    }

    public void aggiornaParametro (DeleDParametro dParametro)  {
		setModifica(dParametro, dParametro.getLoginOperazione());
    	em.merge(dParametro);
    }

}
