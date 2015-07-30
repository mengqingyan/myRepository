/**
 * 
 */
package com.revencoft.validation.exception.handler;

import com.opensymphony.xwork2.ActionInvocation;

/**
 * @author mengqingyan
 * @version 
 */
public interface ValidationExceptionHandler {

	/**
	 * @param invocation
	 * @param e
	 */
	void processValidationException(ActionInvocation invocation, Exception e);

}
