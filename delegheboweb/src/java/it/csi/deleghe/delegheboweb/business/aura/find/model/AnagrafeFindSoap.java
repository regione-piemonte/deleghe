/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.deleghe.delegheboweb.business.aura.find.model;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "AnagrafeFindSoap", targetNamespace = "http://AnagrafeFind.central.services.auraws.aura.csi.it")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface AnagrafeFindSoap {


    /**
     * 
     * @param profiliRequest
     * @return
     *     returns it.csi.aura.auraws.services.central.anagrafefind.DatiAnagraficiMsg
     */
    @WebMethod(operationName = "FindProfiliAnagrafici", action = "http://AnagrafeFind.central.services.auraws.aura.csi.it/AURA.WS.AnagrafeFind.FindProfiliAnagrafici")
    @WebResult(name = "FindProfiliAnagraficiResult", targetNamespace = "http://AnagrafeFind.central.services.auraws.aura.csi.it")
    @RequestWrapper(localName = "FindProfiliAnagrafici", targetNamespace = "http://AnagrafeFind.central.services.auraws.aura.csi.it", className = "it.csi.aura.auraws.services.central.anagrafefind.FindProfiliAnagrafici")
    @ResponseWrapper(localName = "FindProfiliAnagraficiResponse", targetNamespace = "http://AnagrafeFind.central.services.auraws.aura.csi.it", className = "it.csi.aura.auraws.services.central.anagrafefind.FindProfiliAnagraficiResponse")
    public DatiAnagraficiMsg findProfiliAnagrafici(
          @WebParam(name = "profiliRequest", targetNamespace = "http://AnagrafeFind.central.services.auraws.aura.csi.it")
                FindProfiliAnagraficiRequest profiliRequest);

}
