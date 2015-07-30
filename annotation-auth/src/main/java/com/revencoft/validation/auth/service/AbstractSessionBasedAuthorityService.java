/**
 * 
 */
package com.revencoft.validation.auth.service;

import java.util.Map;

import com.opensymphony.xwork2.ActionInvocation;
import com.revencoft.validation.constant.ValidationConstant;

/**
 * @author mengqingyan
 * @version 
 * @param <T>
 */
public abstract class AbstractSessionBasedAuthorityService<T> implements AuthorityService<T> {

	
	AuthorityService<T> dbService;
	
	/**
	 * 需要将用户权限信息放入session，key为{@link com.revencoft.validation.constant.ValidationConstant.sessionAuthKey} 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T loadAuthority(ActionInvocation invocation) {
	
		Map<String, Object> session = invocation.getInvocationContext().getSession();
		Object authorities = session.get(ValidationConstant.sessionAuthKey);
		
		if(authorities == null) {
			if(dbService != null) {
				authorities = dbService.loadAuthority(invocation);
				if(authorities != null) {
					session.put(ValidationConstant.sessionAuthKey, authorities);
				}
			}
		}
		return (T) authorities;
	}
	
	public void setDbService(AuthorityService<T> dbService) {
		this.dbService = dbService;
	}

}