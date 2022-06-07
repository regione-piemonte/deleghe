/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.deleghe.deleghebe.ws.msg;

import javax.xml.bind.annotation.XmlType;


@XmlType
public class SaveFiltri extends ServiceRequest {

    
	/**
	 * 
	 * 
	 * FILTRI NUOVO SERVIZIO BO 		
	 *	
    <xs:element form="unqualified" minOccurs="0" name="idOperatore" type="xs: string"/>
	<xs:element form="unqualified" minOccurs="0" name="statoDichiarazione" type="xs: string"/> 
	<xs:element form="unqualified" minOccurs="0" name="tipoDichiarazione" type="xs: string"/> 
	<xs:element form="unqualified" minOccurs="0" name="dataInserimentoDa" type="xs: string"/> 
	<xs:element form="unqualified" minOccurs="0" name="dataInserimentoA" type="xs: string"/> 
	<xs:element form="unqualified" minOccurs="0" name="Ruolo" type="xs: string"/> 
	<xs:element form="unqualified" minOccurs="0" name="codiceFiscale" type="xs: string"/> 
    
     */

}
