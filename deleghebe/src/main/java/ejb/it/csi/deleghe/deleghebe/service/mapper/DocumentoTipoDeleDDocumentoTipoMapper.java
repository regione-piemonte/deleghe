/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service.mapper;

import javax.inject.Inject;

import it.csi.deleghe.deleghebe.model.DeleDDocumentoTipo;
import it.csi.deleghe.deleghebe.service.DeleDDocumentoTipoRepository;
import it.csi.deleghe.deleghebe.ws.model.DocumentoTipo;

public class DocumentoTipoDeleDDocumentoTipoMapper implements Mapper<DocumentoTipo, DeleDDocumentoTipo> {



	
	@Inject
	private RuoloOperazioneDeleDRuoloOpMapper ruoloOperazioneDeleDRuoloOpMapper;
	@Inject
	private DeleDDocumentoTipoRepository deleDDocumentoTipoRepository;
	
	@Override
	public DeleDDocumentoTipo to(DocumentoTipo src) {
		if(src==null || src.getCodice()==null || src.getCodice().isEmpty()) {
			return null;
		}
		
		DeleDDocumentoTipo documentoTipo=deleDDocumentoTipoRepository.ricercaServiziByDocumentoTipoCod(src.getCodice());
		
		DeleDDocumentoTipo res = new DeleDDocumentoTipo();

		if(documentoTipo != null) {
			res=documentoTipo;
		}
		
		return res;
	}

	@Override
	public DocumentoTipo from(DeleDDocumentoTipo dest) {
		if(dest==null) {
			return null;
		}
		DocumentoTipo res = new DocumentoTipo();
		
		res.setCodice(dest.getDocTipoCod());
		res.setDescrizione(dest.getDocTipoDesc());
		res.setRuoloOperazione(ruoloOperazioneDeleDRuoloOpMapper.from(dest.getDeleDRuoloOp()));
		
		return res;
	}

}
