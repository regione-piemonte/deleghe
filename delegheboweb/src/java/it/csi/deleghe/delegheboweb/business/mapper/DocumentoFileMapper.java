/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.business.mapper;

import it.csi.deleghe.delegheboweb.dto.delegheboweb.DocumentoFile;

public class DocumentoFileMapper extends BaseMapper<DocumentoFile, it.csi.deleghe.deleghebe.ws.model.DocumentoFile> {

	@Override
	public it.csi.deleghe.deleghebe.ws.model.DocumentoFile to(DocumentoFile source) {
		if(source == null) {
			return null;
		}
		it.csi.deleghe.deleghebe.ws.model.DocumentoFile result = new it.csi.deleghe.deleghebe.ws.model.DocumentoFile();
		result.setNome(source.getNome());
		result.setBase64File(source.getBase64File());
		result.setDimensioneInBytes(source.getDimensioneInBytes());
		result.setBytes(source.getBytes());
		
		result.setDataCancellazione(source.getDataCancellazione());
		result.setDataCreazione(source.getDataCreazione());
		result.setDataModifica(source.getDataModifica());
		result.setLoginOperazione(source.getLoginOperazione());

		
		return result;
	}

	@Override
	public DocumentoFile from(it.csi.deleghe.deleghebe.ws.model.DocumentoFile dest) {
		if(dest == null) {
			return null;
		}
		DocumentoFile result = new DocumentoFile();
		result.setNome(dest.getNome());
		result.setBase64File(dest.getBase64File());
		result.setDimensioneInBytes(dest.getDimensioneInBytes());
		result.setBytes(dest.getBytes());
		result.setFileId(dest.getFileId());
		
		result.setDataCancellazione(dest.getDataCancellazione());
		result.setDataCreazione(dest.getDataCreazione());
		result.setDataModifica(dest.getDataModifica());
		result.setLoginOperazione(dest.getLoginOperazione());

		
		return result;
	}

}
