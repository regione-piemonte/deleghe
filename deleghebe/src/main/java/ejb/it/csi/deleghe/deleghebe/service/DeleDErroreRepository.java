/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import it.csi.deleghe.deleghebe.model.DeleDParametro;
import it.csi.deleghe.deleghebe.util.LogUtil;

@Stateless
public class DeleDErroreRepository {

    @Inject
    private LogUtil log;

    @Inject
    private EntityManager em;
    
    /**
     * ANCORA DA SVILUPPARE PER ADESSO NON ESISTONO RECORD A DB
     */
    
    public DeleDParametro ricercaServiziByErroreCod(String erroreCod)  {
    	
    	/**
         * 
         * ANCORA DA SVILUPPARE PER ADESSO NON ESISTONO RECORD A DB
         * 
         */
    	
    	return null;
    }
    

}
