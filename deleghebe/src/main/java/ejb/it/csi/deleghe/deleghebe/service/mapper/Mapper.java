/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service.mapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Interfaccia di base dei Mapping.
 * <br/>
 * NB: Non utilizzare reflection o framework automatici tipo apache commons
 * beanutils l'obiettivo di aver qui tutti i campi scritti a codice è quello di
 * avere immediata evidenza a livello di compilazione di eventuali errori nel
 * caso gli oggetti di da mappare cambino.
 * 
 * <br/>
 * NB: nel caso in un oggetto sia contenuto un altro oggetto si consiglia di
 * annidare i Mapper richiamando da qui un ulteriore mapper dell'oggetto
 * eventualmente annidato.
 * 
 * @author xyz xyz
 *
 * @param <S>
 *            sorgente
 * @param <D>
 *            destinazione
 */
public interface Mapper<S,D> {
	
	public D to(S source);
	
	public S from(D dest);
	
	
	default public List<D> toList(List<S> list){
		if(list==null) {
			return null;
		}
		return list.stream().map(s -> to(s)).collect(Collectors.toList());
	}
	
	default public List<S> fromList(List<D> list){
		if(list==null) {
			return null;
		}
		return list.stream().map(s -> from(s)).collect(Collectors.toList());
	}
	
}
