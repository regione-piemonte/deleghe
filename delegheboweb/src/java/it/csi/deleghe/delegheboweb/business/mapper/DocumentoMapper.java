/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.business.mapper;

import it.csi.deleghe.delegheboweb.dto.delegheboweb.Documento;

public class DocumentoMapper extends BaseMapper<Documento, it.csi.deleghe.deleghebe.ws.model.Documento> {

	@Override
	public it.csi.deleghe.deleghebe.ws.model.Documento to(Documento source) {
		if(source == null) {
			return null;
		}
		it.csi.deleghe.deleghebe.ws.model.Documento result = new it.csi.deleghe.deleghebe.ws.model.Documento();
		
		result.setId(source.getId());
		result.setDesc(source.getDesc());
		result.setDocumentoTipo(new DocumentoTipoMapper().to(source.getTipo()));		
		result.setFile(new DocumentoFileMapper().to(source.getFile()));
		result.setDataScadenzaDoc(source.getDataScadenzaDoc());
		

		result.setDataCancellazione(source.getDataCancellazione());
		result.setDataCreazione(source.getDataCreazione());
		result.setDataModifica(source.getDataModifica());
		result.setLoginOperazione(source.getLoginOperazione());
		

		result.setAutorita(source.getAutorita());
		result.setDataRilascio(source.getDataRilascio());
		result.setDataScadenzaDoc(source.getDataScadenzaDoc());
		result.setDocDesc(source.getDocDesc());
		result.setNumeroDocumento(source.getNumeroDocumento());

		
		return result;
		
	}

	@Override
	public Documento from(it.csi.deleghe.deleghebe.ws.model.Documento dest) {
		if(dest == null) {
			return null;
		}
		Documento result = new Documento();
		result.setId(dest.getId());
		result.setDesc(dest.getDesc());
		result.setTipo(new DocumentoTipoMapper().from(dest.getDocumentoTipo()));		
		result.setFile(new DocumentoFileMapper().from(dest.getFile()));
		result.setDataScadenzaDoc(dest.getDataScadenzaDoc());		


		result.setDataCancellazione(dest.getDataCancellazione());
		result.setDataCreazione(dest.getDataCreazione());
		result.setDataModifica(dest.getDataModifica());
		result.setLoginOperazione(dest.getLoginOperazione());
		

		result.setAutorita(dest.getAutorita());
		result.setDataRilascio(dest.getDataRilascio());
		result.setDataScadenzaDoc(dest.getDataScadenzaDoc());
		result.setDocDesc(dest.getDocDesc());
		result.setNumeroDocumento(dest.getNumeroDocumento());


		
		return result;
	}

}
