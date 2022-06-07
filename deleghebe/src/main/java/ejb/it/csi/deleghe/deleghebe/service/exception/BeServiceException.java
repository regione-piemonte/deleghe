/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.service.exception;

public class BeServiceException extends RuntimeException {

	private static final long serialVersionUID = -3237360703269592843L;

	private final String code;
	private final String exceptionName;
	
	public BeServiceException (String code, String message) {
		this(code, message, null);
	}
	
	public BeServiceException (String code, String message, String exceptionName) {
		super(message);
		this.code = code;
		this.exceptionName = exceptionName;
	}

	public String getCode() {
		return code;
	}

	public String getExceptionName() {
		return exceptionName;
	}
}
