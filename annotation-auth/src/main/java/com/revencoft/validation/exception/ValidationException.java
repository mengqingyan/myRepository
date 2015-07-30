/**
 * 
 */
package com.revencoft.validation.exception;

import java.lang.annotation.Annotation;

/**
 * @author mengqingyan
 * @version 
 * @since 1.0
 */
public class ValidationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Annotation errorAnno;
	
	protected String validationKeyStr;
	
	public ValidationException() {
		super();
	}

	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValidationException(String message) {
		super(message);
	}

	public Annotation getErrorAnno() {
		return errorAnno;
	}

	public ValidationException setErrorAnno(Annotation errorAnno) {
		this.errorAnno = errorAnno;
		return this;
	}
	
	public String getValidationKeyStr() {
		if(validationKeyStr == null) {
			return null;
		}
		return validationKeyStr.replaceAll("/", "\\.").replaceFirst("\\.", "");
	}

	public ValidationException setValidationKeyStr(String ValidationKeyStr) {
		this.validationKeyStr = ValidationKeyStr;
		return this;
	}
	
	public String getDefaultErrorMsg() {
		return null;
	}
	
	public String getDefaultErrorMsgKeyPrefix() {
		return null;
	}
	
}
