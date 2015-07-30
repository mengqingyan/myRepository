package com.revencoft.validation.exception;

import java.lang.annotation.Annotation;

/**
 * @author mengqingyan
 * @version: 2.0
 * @desc:
 */

public class FieldValidationException extends ValidationException {

	private static final String DEFAULT_VALIDATION_ERROR_MESSAGE = "表单数据格式有误,请重新填写!";
	
	private static final String ERROR_MSG_KEYPREFIX = "validation.error.";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public FieldValidationException() {
		super();
	}

	public FieldValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public FieldValidationException(String message) {
		super(message);
	}

	public FieldValidationException(Annotation errorAnno, String message) {
		super(message);
		setErrorAnno(errorAnno);
	}
	
	

	public FieldValidationException(String message, String fieldName, Annotation errorAnno) {
		super(message);
		setErrorAnno(errorAnno);
		this.validationKeyStr = fieldName;
	}
	
	@Override
	public String getDefaultErrorMsg() {
		return DEFAULT_VALIDATION_ERROR_MESSAGE;
	}

	@Override
	public String getDefaultErrorMsgKeyPrefix() {
		return ERROR_MSG_KEYPREFIX;
	}
	
	

}
