/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.business.mapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Interfaccia di base dei Mapping.
 * 
 * @author xyz xyz
 *
 * @param <S> sorgente
 * @param <D> destinazione
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
