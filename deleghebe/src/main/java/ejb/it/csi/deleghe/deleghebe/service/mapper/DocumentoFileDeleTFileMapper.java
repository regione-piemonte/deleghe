/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service.mapper;

import java.math.BigDecimal;
import java.util.Base64;

import it.csi.deleghe.deleghebe.model.DeleTFile;
import it.csi.deleghe.deleghebe.ws.model.DocumentoFile;

public class DocumentoFileDeleTFileMapper implements Mapper<DocumentoFile, DeleTFile> {



	
	
	@Override
	public DeleTFile to(DocumentoFile src) {
		if(src==null) {
			return null;
		}
		DeleTFile res = new DeleTFile();
		
		res.setFile(src.getBase64File().getBytes());
		res.setFileName(src.getNome());
		res.setFileSize(new BigDecimal(src.getDimensioneInBytes()));
	 
		return res;
	}

	@Override
	public DocumentoFile  from(DeleTFile dest) {
		if(dest==null) {
			return null;
		}
		DocumentoFile res = new DocumentoFile();

		res.setBytes(dest.getFile());
		res.setDimensioneInBytes(dest.getFileSize().intValue());
		res.setNome(dest.getFileName());
		res.setBase64File(Base64.getEncoder().encodeToString(dest.getFile()));
		res.setFileId(dest.getFileId());
	

		return res;
	}


}
