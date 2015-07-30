/**
 * 
 */
package com.revencoft.validation.auth.decider;

import com.opensymphony.xwork2.ActionInvocation;
import com.revencoft.validation.exception.ValidationException;

/**
 * @author mengqingyan
 * @version 2.0
 */
public interface AuthenticationDecider {

	/**
	 * 权限验证
	 * @param invocation
	 * @throws ValidationException
	 */
	public abstract void authenticate(ActionInvocation invocation)
			throws ValidationException;

}