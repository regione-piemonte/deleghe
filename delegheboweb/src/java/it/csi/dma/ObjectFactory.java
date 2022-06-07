/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.dma;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import it.csi.dmacc.Errori;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.csi.dma package. 
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

    private final static QName _Richiedente_QNAME = new QName("http://dma.csi.it/", "richiedente");
    private final static QName _FunzionalitaAbilitate_QNAME = new QName("http://dma.csi.it/", "funzionalitaAbilitate");
    private final static QName _ParametriLogin_QNAME = new QName("http://dma.csi.it/", "parametriLogin");
    private final static QName _IpBrowser_QNAME = new QName("http://dma.csi.it/", "ipBrowser");
    private final static QName _Codifiche_QNAME = new QName("http://dma.csi.it/", "codifiche");
    private final static QName _Token_QNAME = new QName("http://dma.csi.it/", "token");
    private final static QName _Errore_QNAME = new QName("http://dma.csi.it/", "errore");
    private final static QName _Applicazione_QNAME = new QName("http://dma.csi.it/", "applicazione");
    private final static QName _Errori_QNAME = new QName("http://dma.csi.it/", "errori");
    private final static QName _CodiceFiscaleAssistito_QNAME = new QName("http://dma.csi.it/", "codiceFiscaleAssistito");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.csi.dma
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FunzionalitaAbilitate }
     * 
     */
    public FunzionalitaAbilitate createFunzionalitaAbilitate() {
        return new FunzionalitaAbilitate();
    }

    /**
     * Create an instance of {@link ParametriLogin }
     * 
     */
    public ParametriLogin createParametriLogin() {
        return new ParametriLogin();
    }

    /**
     * Create an instance of {@link Richiedente }
     * 
     */
    public Richiedente createRichiedente() {
        return new Richiedente();
    }

    /**
     * Create an instance of {@link Errore }
     * 
     */
    public Errore createErrore() {
        return new Errore();
    }

    /**
     * Create an instance of {@link Codifica }
     * 
     */
    public Codifica createCodifica() {
        return new Codifica();
    }

    /**
     * Create an instance of {@link Funzionalita }
     * 
     */
    public Funzionalita createFunzionalita() {
        return new Funzionalita();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Richiedente }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dma.csi.it/", name = "richiedente")
    public JAXBElement<Richiedente> createRichiedente(Richiedente value) {
        return new JAXBElement<Richiedente>(_Richiedente_QNAME, Richiedente.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FunzionalitaAbilitate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dma.csi.it/", name = "funzionalitaAbilitate")
    public JAXBElement<FunzionalitaAbilitate> createFunzionalitaAbilitate(FunzionalitaAbilitate value) {
        return new JAXBElement<FunzionalitaAbilitate>(_FunzionalitaAbilitate_QNAME, FunzionalitaAbilitate.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ParametriLogin }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dma.csi.it/", name = "parametriLogin")
    public JAXBElement<ParametriLogin> createParametriLogin(ParametriLogin value) {
        return new JAXBElement<ParametriLogin>(_ParametriLogin_QNAME, ParametriLogin.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dma.csi.it/", name = "ipBrowser")
    public JAXBElement<String> createIpBrowser(String value) {
        return new JAXBElement<String>(_IpBrowser_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Codifica }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dma.csi.it/", name = "codifiche")
    public JAXBElement<Codifica> createCodifiche(Codifica value) {
        return new JAXBElement<Codifica>(_Codifiche_QNAME, Codifica.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dma.csi.it/", name = "token")
    public JAXBElement<String> createToken(String value) {
        return new JAXBElement<String>(_Token_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Errore }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dma.csi.it/", name = "errore")
    public JAXBElement<Errore> createErrore(Errore value) {
        return new JAXBElement<Errore>(_Errore_QNAME, Errore.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dma.csi.it/", name = "applicazione")
    public JAXBElement<String> createApplicazione(String value) {
        return new JAXBElement<String>(_Applicazione_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Errori }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dma.csi.it/", name = "errori")
    public JAXBElement<Errori> createErrori(Errori value) {
        return new JAXBElement<Errori>(_Errori_QNAME, Errori.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dma.csi.it/", name = "codiceFiscaleAssistito")
    public JAXBElement<String> createCodiceFiscaleAssistito(String value) {
        return new JAXBElement<String>(_CodiceFiscaleAssistito_QNAME, String.class, null, value);
    }

}
