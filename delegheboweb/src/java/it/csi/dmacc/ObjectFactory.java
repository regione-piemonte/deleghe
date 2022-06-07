/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dmacc;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import it.csi.dma.ParametriLogin;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.csi.dmacc package. 
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

    private final static QName _GetTokenInformationRequest_QNAME = new QName("http://dmacc.csi.it/", "getTokenInformationRequest");
    private final static QName _GetTokenInformation2Request_QNAME = new QName("http://dmacc.csi.it/", "getTokenInformation2Request");
    private final static QName _GetTokenInformation2Response_QNAME = new QName("http://dmacc.csi.it/", "getTokenInformation2Response");
    private final static QName _GetTokenInformationResponse_QNAME = new QName("http://dmacc.csi.it/", "getTokenInformationResponse");
    private final static QName _ParametriLogin_QNAME = new QName("http://dmacc.csi.it/", "parametriLogin");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.csi.dmacc
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Errori }
     * 
     */
    public Errori createErrori() {
        return new Errori();
    }

    /**
     * Create an instance of {@link GetTokenInformation2Request }
     * 
     */
    public GetTokenInformation2Request createGetTokenInformation2Request() {
        return new GetTokenInformation2Request();
    }

    /**
     * Create an instance of {@link GetTokenInformationRequest }
     * 
     */
    public GetTokenInformationRequest createGetTokenInformationRequest() {
        return new GetTokenInformationRequest();
    }

    /**
     * Create an instance of {@link GetTokenInformation2Response }
     * 
     */
    public GetTokenInformation2Response createGetTokenInformation2Response() {
        return new GetTokenInformation2Response();
    }

    /**
     * Create an instance of {@link GetTokenInformationResponse }
     * 
     */
    public GetTokenInformationResponse createGetTokenInformationResponse() {
        return new GetTokenInformationResponse();
    }

    /**
     * Create an instance of {@link ServiceResponse }
     * 
     */
    public ServiceResponse createServiceResponse() {
        return new ServiceResponse();
    }

    /**
     * Create an instance of {@link EnsMessagebody }
     * 
     */
    public EnsMessagebody createEnsMessagebody() {
        return new EnsMessagebody();
    }

    /**
     * Create an instance of {@link EnsResponse }
     * 
     */
    public EnsResponse createEnsResponse() {
        return new EnsResponse();
    }

    /**
     * Create an instance of {@link EnsRequest }
     * 
     */
    public EnsRequest createEnsRequest() {
        return new EnsRequest();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTokenInformationRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dmacc.csi.it/", name = "getTokenInformationRequest")
    public JAXBElement<GetTokenInformationRequest> createGetTokenInformationRequest(GetTokenInformationRequest value) {
        return new JAXBElement<GetTokenInformationRequest>(_GetTokenInformationRequest_QNAME, GetTokenInformationRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTokenInformation2Request }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dmacc.csi.it/", name = "getTokenInformation2Request")
    public JAXBElement<GetTokenInformation2Request> createGetTokenInformation2Request(GetTokenInformation2Request value) {
        return new JAXBElement<GetTokenInformation2Request>(_GetTokenInformation2Request_QNAME, GetTokenInformation2Request.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTokenInformation2Response }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dmacc.csi.it/", name = "getTokenInformation2Response")
    public JAXBElement<GetTokenInformation2Response> createGetTokenInformation2Response(GetTokenInformation2Response value) {
        return new JAXBElement<GetTokenInformation2Response>(_GetTokenInformation2Response_QNAME, GetTokenInformation2Response.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTokenInformationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dmacc.csi.it/", name = "getTokenInformationResponse")
    public JAXBElement<GetTokenInformationResponse> createGetTokenInformationResponse(GetTokenInformationResponse value) {
        return new JAXBElement<GetTokenInformationResponse>(_GetTokenInformationResponse_QNAME, GetTokenInformationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ParametriLogin }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dmacc.csi.it/", name = "parametriLogin")
    public JAXBElement<ParametriLogin> createParametriLogin(ParametriLogin value) {
        return new JAXBElement<ParametriLogin>(_ParametriLogin_QNAME, ParametriLogin.class, null, value);
    }

}
