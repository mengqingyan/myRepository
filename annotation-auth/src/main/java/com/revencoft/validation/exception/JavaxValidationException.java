/**
 * 
 */
package com.revencoft.validation.exception;

import java.util.Set;

import javax.validation.ConstraintViolation;


/**
 * 若无验证错误提示，violations为an empty set 
 * @author mengqingyan
 * @version 
 */
public class JavaxValidationException extends ValidationException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final Set<ConstraintViolation<Object>> violations;

	/**
	 * @param res
	 */
	public JavaxValidationException(Set<ConstraintViolation<Object>> violations, String actionFieldName) {
		this.violations = violations;
		this.validationKeyStr = actionFieldName;
	}

	public Set<ConstraintViolation<Object>> getViolations() {
		return violations;
	}

	
}
