/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service.mapper;

import java.util.Date;

import javax.inject.Inject;

import it.csi.deleghe.deleghebe.model.DeleTDocumento;
import it.csi.deleghe.deleghebe.ws.model.Documento;
import it.csi.deleghe.deleghebe.ws.model.DocumentoTipo;

public class DocumentoDeleTDocumentoMapper implements Mapper<Documento, DeleTDocumento> {



	@Inject
	private RuoloOperazioneDeleDRuoloOpMapper ruoloOperazioneDeleDRuoloOpMapper;
	@Inject
	private DocumentoFileDeleTFileMapper documentoFileDeleTFileMapper;
	@Inject
	private DocumentoTipoDeleDDocumentoTipoMapper documentoTipoDeleDDocumentoTipoMapper;

	
	@Override
	public DeleTDocumento to(Documento src) {
		if(src==null) {
			return null;
		}
		DeleTDocumento res = new DeleTDocumento();
		
		res.setDeleDDocumentoTipo(documentoTipoDeleDDocumentoTipoMapper.to(src.getDocumentoTipo()));
		
		res.setDeleTFile(documentoFileDeleTFileMapper.to(src.getFile()));

		res.setDeleDRuoloOp(ruoloOperazioneDeleDRuoloOpMapper.to(src.getRuoloOperazione()));

		res.setAutorita(src.getAutorita());
		
		res.setDataScadenzaDoc(src.getDataScadenzaDoc());
		res.setNumeroDocumento(src.getNumeroDocumento());
		res.setDataRilascio(src.getDataRilascio());
		res.setDocDesc(src.getDocDesc());
		Date today = new Date();
		res.setDataCreazione(today);
		res.setDataModifica(today);
		res.setLoginOperazione("FITTIZ99I20Z219I");
	 
		return res;
	}

	@Override
	public Documento  from(DeleTDocumento dest) {
		if(dest==null) {
			return null;
		}
		Documento res = new Documento();

		res.setDesc(dest.getDocDesc());
		res.setDocumentoTipo(documentoTipoDeleDDocumentoTipoMapper.from(dest.getDeleDDocumentoTipo()));
		res.setFile(documentoFileDeleTFileMapper.from(dest.getDeleTFile()));
		//nuovi campi
		res.setAutorita(dest.getAutorita());
		res.setDataScadenzaDoc(dest.getDataScadenzaDoc());
		res.setNumeroDocumento(dest.getNumeroDocumento());
		res.setDataRilascio(dest.getDataRilascio());
		res.setDocDesc(dest.getDocDesc());
		
		res.setRuoloOperazione(ruoloOperazioneDeleDRuoloOpMapper.from(dest.getDeleDRuoloOp()));
		res.setId(dest.getDocId());

		return res;
	}


}
