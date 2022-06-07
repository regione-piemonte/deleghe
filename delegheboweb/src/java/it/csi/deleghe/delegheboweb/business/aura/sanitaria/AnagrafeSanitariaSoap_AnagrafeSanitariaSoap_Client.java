/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.deleghe.delegheboweb.business.aura.sanitaria;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.14
 * 2021-11-11T14:11:23.543+01:00
 * Generated source version: 2.7.14
 * 
 */
public final class AnagrafeSanitariaSoap_AnagrafeSanitariaSoap_Client {

    private static final QName SERVICE_NAME = new QName("http://AnagrafeSanitaria.central.services.auraws.aura.csi.it", "AnagrafeSanitaria");

    private AnagrafeSanitariaSoap_AnagrafeSanitariaSoap_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = AnagrafeSanitaria.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        AnagrafeSanitaria ss = new AnagrafeSanitaria(wsdlURL, SERVICE_NAME);
        AnagrafeSanitariaSoap port = ss.getAnagrafeSanitariaSoap();  
        
        {

        java.lang.String _getProfiloSanitario_aurAid = "";
        it.csi.deleghe.delegheboweb.business.aura.sanitaria.SoggettoAuraMsg _getProfiloSanitario__return = port.getProfiloSanitario(_getProfiloSanitario_aurAid);



        }

        System.exit(0);
    }

}
