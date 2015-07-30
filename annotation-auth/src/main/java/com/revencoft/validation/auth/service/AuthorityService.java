/**
 * 
 */
package com.revencoft.validation.auth.service;

import com.opensymphony.xwork2.ActionInvocation;

/**
 * @author mengqingyan
 * @version 
 */
public interface AuthorityService<T> {

	/**
	 * 获取权限信息
	 * @param invocation
	 * @return
	 */
	public abstract T loadAuthority(
			ActionInvocation invocation);

}