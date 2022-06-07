/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.deleghe.deleghebe.ws.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

import it.csi.deleghe.deleghebe.ws.msg.GetDeleganti;
import it.csi.deleghe.deleghebe.ws.msg.GetDelegantiResponse;
import it.csi.deleghe.deleghebe.ws.msg.GetDelegati;
import it.csi.deleghe.deleghebe.ws.msg.GetDelegatiResponse;
import it.csi.deleghe.deleghebe.ws.msg.IsAlive;
import it.csi.deleghe.deleghebe.ws.msg.IsAliveResponse;
import it.csi.deleghe.deleghebe.ws.msg.RinunciaDelegato;
import it.csi.deleghe.deleghebe.ws.msg.RinunciaDelegatoResponse;
import it.csi.deleghe.deleghebe.ws.msg.SaveDelegati;
import it.csi.deleghe.deleghebe.ws.msg.SaveDelegatiResponse;
import it.csi.deleghe.deleghebe.ws.msg.ServiceResponse;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.csi.deleghe.deleghebe.ws package. 
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

    private final static QName _SaveDelegatiResponse_QNAME = new QName("http://deleghebe.csi.it/", "saveDelegatiResponse");
    private final static QName _Codifica_QNAME = new QName("http://deleghebe.csi.it/", "codifica");

    private final static QName _ServizioDelega_QNAME = new QName("http://deleghebe.csi.it/", "servizioDelega");
    private final static QName _CittadinoDeleghe_QNAME = new QName("http://deleghebe.csi.it/", "cittadinoDeleghe");
    private final static QName _Cittadino_QNAME = new QName("http://deleghebe.csi.it/", "cittadino");
    private final static QName _GetDelegati_QNAME = new QName("http://deleghebe.csi.it/", "getDelegati");

    private final static QName _GetDeleganti_QNAME = new QName("http://deleghebe.csi.it/", "getDeleganti");
    private final static QName _IsAliveResponse_QNAME = new QName("http://deleghebe.csi.it/", "isAliveResponse");
    private final static QName _Richiedente_QNAME = new QName("http://deleghebe.csi.it/", "richiedente");
    private final static QName _IsAlive_QNAME = new QName("http://deleghebe.csi.it/", "isAlive");
    private final static QName _GetDelegatiResponse_QNAME = new QName("http://deleghebe.csi.it/", "getDelegatiResponse");

    private final static QName _RinunciaDelegato_QNAME = new QName("http://deleghebe.csi.it/", "rinunciaDelegato");
    private final static QName _GetDelegantiResponse_QNAME = new QName("http://deleghebe.csi.it/", "getDelegantiResponse");
    private final static QName _SaveDelegati_QNAME = new QName("http://deleghebe.csi.it/", "saveDelegati");

    private final static QName _RinunciaDelegatoResponse_QNAME = new QName("http://deleghebe.csi.it/", "rinunciaDelegatoResponse");
    private final static QName _GetDelegatiDelegato_QNAME = new QName("", "delegato");
    private final static QName _GetDelegatiCodiceServizio_QNAME = new QName("", "codiceServizio");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.csi.deleghe.deleghebe.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DelegaServizio }
     * 
     */
    public DelegaServizio createServizioDelega() {
        return new DelegaServizio();
    }

    /**
     * Create an instance of {@link Richiedente }
     * 
     */
    public Richiedente createRichiedente() {
        return new Richiedente();
    }

    /**
     * Create an instance of {@link Codifica }
     * 
     */
    public Codifica createCodifica() {
        return new Codifica();
    }


    /**
     * Create an instance of {@link Cittadino }
     * 
     */
    public Cittadino createCittadino() {
        return new Cittadino();
    }

    /**
     * Create an instance of {@link DelegaCittadino }
     * 
     */
    public DelegaCittadino createCittadinoDeleghe() {
        return new DelegaCittadino();
    }



    /**
     * Create an instance of {@link RuoloDeleghe }
     * 
     */
    public RuoloDeleghe createRuoloDeleghe() {
        return new RuoloDeleghe();
    }

    /**
     * Create an instance of {@link Errore }
     * 
     */
    public Errore createErrore() {
        return new Errore();
    }

    /**
     * Create an instance of {@link ApplicazioneRichiedente }
     * 
     */
    public ApplicazioneRichiedente createApplicazioneRichiedente() {
        return new ApplicazioneRichiedente();
    }

    /**
     * Create an instance of {@link Applicazione }
     * 
     */
    public Applicazione createApplicazione() {
        return new Applicazione();
    }

    /**
     * Create an instance of {@link Deleganti }
     * 
     */
    public Deleganti createDeleganti() {
        return new Deleganti();
    }

    /**
     * Create an instance of {@link TipoFileDocumento }
     * 
     */
    public TipoFileDocumento createTipoFileDocumento() {
        return new TipoFileDocumento();
    }

    /**
     * Create an instance of {@link StrutturaSanitaria }
     * 
     */
    public StrutturaSanitaria createStrutturaSanitaria() {
        return new StrutturaSanitaria();
    }

    /**
     * Create an instance of {@link StatoDiNascita }
     * 
     */
    public StatoDiNascita createStatoDiNascita() {
        return new StatoDiNascita();
    }

    /**
     * Create an instance of {@link UnitaOperativaSanitaria }
     * 
     */
    public UnitaOperativaSanitaria createUnitaOperativaSanitaria() {
        return new UnitaOperativaSanitaria();
    }

    /**
     * Create an instance of {@link AziendaSanitaria }
     * 
     */
    public AziendaSanitaria createAziendaSanitaria() {
        return new AziendaSanitaria();
    }

    /**
     * Create an instance of {@link ComuneDiNascita }
     * 
     */
    public ComuneDiNascita createComuneDiNascita() {
        return new ComuneDiNascita();
    }

    /**
     * Create an instance of {@link Delegati }
     * 
     */
    public Delegati createDelegati() {
        return new Delegati();
    }

    /**
     * Create an instance of {@link Profilo }
     * 
     */
    public Profilo createProfilo() {
        return new Profilo();
    }

    /**
     * Create an instance of {@link TipoEpisodio }
     * 
     */
    public TipoEpisodio createTipoEpisodio() {
        return new TipoEpisodio();
    }



    /**
     * Create an instance of {@link IsAlive }
     * 
     */
    public IsAlive createIsAlive() {
        return new IsAlive();
    }

    /**
     * Create an instance of {@link GetDelegatiResponse }
     * 
     */
    public GetDelegatiResponse createGetDelegatiResponse() {
        return new GetDelegatiResponse();
    }

    /**
     * Create an instance of {@link SaveDelegatiResponse }
     * 
     */
    public SaveDelegatiResponse createSaveDelegatiResponse() {
        return new SaveDelegatiResponse();
    }

    /**
     * Create an instance of {@link GetDelegantiResponse }
     * 
     */
    public GetDelegantiResponse createGetDelegantiResponse() {
        return new GetDelegantiResponse();
    }

    /**
     * Create an instance of {@link SaveDelegati }
     * 
     */
    public SaveDelegati createSaveDelegati() {
        return new SaveDelegati();
    }



    /**
     * Create an instance of {@link GetDeleganti }
     * 
     */
    public GetDeleganti createGetDeleganti() {
        return new GetDeleganti();
    }

    /**
     * Create an instance of {@link RinunciaDelegatoResponse }
     * 
     */
    public RinunciaDelegatoResponse createRinunciaDelegatoResponse() {
        return new RinunciaDelegatoResponse();
    }

    /**
     * Create an instance of {@link IsAliveResponse }
     * 
     */
    public IsAliveResponse createIsAliveResponse() {
        return new IsAliveResponse();
    }

    /**
     * Create an instance of {@link GetDelegati }
     * 
     */
    public GetDelegati createGetDelegati() {
        return new GetDelegati();
    }

    /**
     * Create an instance of {@link RinunciaDelegato }
     * 
     */
    public RinunciaDelegato createRinunciaDelegato() {
        return new RinunciaDelegato();
    }

    /**
     * Create an instance of {@link ServiceResponse }
     * 
     */
    public ServiceResponse createServiceResponse() {
        return new ServiceResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveDelegatiResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://deleghebe.csi.it/", name = "saveDelegatiResponse")
    public JAXBElement<SaveDelegatiResponse> createSaveDelegatiResponse(SaveDelegatiResponse value) {
        return new JAXBElement<SaveDelegatiResponse>(_SaveDelegatiResponse_QNAME, SaveDelegatiResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Codifica }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://deleghebe.csi.it/", name = "codifica")
    public JAXBElement<Codifica> createCodifica(Codifica value) {
        return new JAXBElement<Codifica>(_Codifica_QNAME, Codifica.class, null, value);
    }



    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DelegaServizio }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://deleghebe.csi.it/", name = "servizioDelega")
    public JAXBElement<DelegaServizio> createServizioDelega(DelegaServizio value) {
        return new JAXBElement<DelegaServizio>(_ServizioDelega_QNAME, DelegaServizio.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DelegaCittadino }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://deleghebe.csi.it/", name = "cittadinoDeleghe")
    public JAXBElement<DelegaCittadino> createCittadinoDeleghe(DelegaCittadino value) {
        return new JAXBElement<DelegaCittadino>(_CittadinoDeleghe_QNAME, DelegaCittadino.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Cittadino }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://deleghebe.csi.it/", name = "cittadino")
    public JAXBElement<Cittadino> createCittadino(Cittadino value) {
        return new JAXBElement<Cittadino>(_Cittadino_QNAME, Cittadino.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDelegati }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://deleghebe.csi.it/", name = "getDelegati")
    public JAXBElement<GetDelegati> createGetDelegati(GetDelegati value) {
        return new JAXBElement<GetDelegati>(_GetDelegati_QNAME, GetDelegati.class, null, value);
    }



    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDeleganti }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://deleghebe.csi.it/", name = "getDeleganti")
    public JAXBElement<GetDeleganti> createGetDeleganti(GetDeleganti value) {
        return new JAXBElement<GetDeleganti>(_GetDeleganti_QNAME, GetDeleganti.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsAliveResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://deleghebe.csi.it/", name = "isAliveResponse")
    public JAXBElement<IsAliveResponse> createIsAliveResponse(IsAliveResponse value) {
        return new JAXBElement<IsAliveResponse>(_IsAliveResponse_QNAME, IsAliveResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Richiedente }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://deleghebe.csi.it/", name = "richiedente")
    public JAXBElement<Richiedente> createRichiedente(Richiedente value) {
        return new JAXBElement<Richiedente>(_Richiedente_QNAME, Richiedente.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsAlive }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://deleghebe.csi.it/", name = "isAlive")
    public JAXBElement<IsAlive> createIsAlive(IsAlive value) {
        return new JAXBElement<IsAlive>(_IsAlive_QNAME, IsAlive.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDelegatiResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://deleghebe.csi.it/", name = "getDelegatiResponse")
    public JAXBElement<GetDelegatiResponse> createGetDelegatiResponse(GetDelegatiResponse value) {
        return new JAXBElement<GetDelegatiResponse>(_GetDelegatiResponse_QNAME, GetDelegatiResponse.class, null, value);
    }



    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RinunciaDelegato }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://deleghebe.csi.it/", name = "rinunciaDelegato")
    public JAXBElement<RinunciaDelegato> createRinunciaDelegato(RinunciaDelegato value) {
        return new JAXBElement<RinunciaDelegato>(_RinunciaDelegato_QNAME, RinunciaDelegato.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDelegantiResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://deleghebe.csi.it/", name = "getDelegantiResponse")
    public JAXBElement<GetDelegantiResponse> createGetDelegantiResponse(GetDelegantiResponse value) {
        return new JAXBElement<GetDelegantiResponse>(_GetDelegantiResponse_QNAME, GetDelegantiResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveDelegati }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://deleghebe.csi.it/", name = "saveDelegati")
    public JAXBElement<SaveDelegati> createSaveDelegati(SaveDelegati value) {
        return new JAXBElement<SaveDelegati>(_SaveDelegati_QNAME, SaveDelegati.class, null, value);
    }


    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RinunciaDelegatoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://deleghebe.csi.it/", name = "rinunciaDelegatoResponse")
    public JAXBElement<RinunciaDelegatoResponse> createRinunciaDelegatoResponse(RinunciaDelegatoResponse value) {
        return new JAXBElement<RinunciaDelegatoResponse>(_RinunciaDelegatoResponse_QNAME, RinunciaDelegatoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Cittadino }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "delegato", scope = GetDelegati.class)
    public JAXBElement<Cittadino> createGetDelegatiDelegato(Cittadino value) {
        return new JAXBElement<Cittadino>(_GetDelegatiDelegato_QNAME, Cittadino.class, GetDelegati.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Codifica }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "codiceServizio", scope = GetDelegati.class)
    public JAXBElement<Codifica> createGetDelegatiCodiceServizio(Codifica value) {
        return new JAXBElement<Codifica>(_GetDelegatiCodiceServizio_QNAME, Codifica.class, GetDelegati.class, value);
    }

}
