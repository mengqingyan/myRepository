/**
 * 
 */
package com.revencoft.validation.persistent.validator;

import com.opensymphony.xwork2.ActionInvocation;

/**
 * 数据校验器
 * @author mengqingyan
 * @version 
 */
public interface PropertyValidator {

	/**
	 * @param invocation
	 */
	void validate(ActionInvocation invocation) throws Exception;

}
