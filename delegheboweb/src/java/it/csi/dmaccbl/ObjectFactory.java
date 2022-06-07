/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dmaccbl;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import it.csi.dmacc.GetTokenInformation2Request;
import it.csi.dmacc.GetTokenInformationRequest;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.csi.dmaccbl package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetTokenInformationRequest_QNAME = new QName("http://dmaccbl.csi.it/", "getTokenInformationRequest");
    private final static QName _GetTokenInformation2Request_QNAME = new QName("http://dmaccbl.csi.it/", "getTokenInformation2Request");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.csi.dmaccbl
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTokenInformationRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dmaccbl.csi.it/", name = "getTokenInformationRequest")
    public JAXBElement<GetTokenInformationRequest> createGetTokenInformationRequest(GetTokenInformationRequest value) {
        return new JAXBElement<GetTokenInformationRequest>(_GetTokenInformationRequest_QNAME, GetTokenInformationRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTokenInformation2Request }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dmaccbl.csi.it/", name = "getTokenInformation2Request")
    public JAXBElement<GetTokenInformation2Request> createGetTokenInformation2Request(GetTokenInformation2Request value) {
        return new JAXBElement<GetTokenInformation2Request>(_GetTokenInformation2Request_QNAME, GetTokenInformation2Request.class, null, value);
    }

}
