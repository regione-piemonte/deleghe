/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.business.delegheboweb;

import it.csi.deleghe.delegheboweb.business.delegheboweb.impl.CittadiniApiServiceImpl;
import it.csi.deleghe.delegheboweb.business.delegheboweb.impl.ComuniApiImpl;
import it.csi.deleghe.delegheboweb.business.delegheboweb.impl.DelegheCodificheApiImpl;
import it.csi.deleghe.delegheboweb.business.delegheboweb.impl.DelgheBackOfficeApiImpl;
import it.csi.deleghe.delegheboweb.business.delegheboweb.impl.NazioniApiImpl;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class RestApplication extends Application {


    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<Class<?>>();
        resources.add(CittadiniApiServiceImpl.class);
        resources.add(DelgheBackOfficeApiImpl.class);
        resources.add(DelegheCodificheApiImpl.class);
        resources.add(ComuniApiImpl.class);
        resources.add(NazioniApiImpl.class);

        return resources;
    }




}