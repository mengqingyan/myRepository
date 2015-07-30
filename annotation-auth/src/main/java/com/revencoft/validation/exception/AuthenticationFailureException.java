/**
 * 
 */
package com.revencoft.validation.exception;

/**
 * @author mengqingyan
 * @version 2.0
 */
public class AuthenticationFailureException extends ValidationException {
	
	private static final String DEFAULT_AUTH_ERROR_MESSAGE = "对不起，无权限访问！";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String ERROR_MSG_KEYPREFIX = "auth.error.";
	
	/**
	 * 
	 */
	public AuthenticationFailureException() {
		super();
	}

	/**
	 * @param message
	 */
	public AuthenticationFailureException(String message) {
		super(message);
	}
	
	public AuthenticationFailureException(String message, String actionName) {
		super(message);
		this.validationKeyStr = actionName;
	}

	@Override
	public String getDefaultErrorMsg() {
		return DEFAULT_AUTH_ERROR_MESSAGE;
	}
	
	@Override
	public String getDefaultErrorMsgKeyPrefix() {
		return ERROR_MSG_KEYPREFIX;
	}

}
