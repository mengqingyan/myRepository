/**
 * 
 */
package com.revencoft.validation.auth.service;

import java.util.Map;

import com.opensymphony.xwork2.ActionInvocation;
import com.revencoft.validation.auth.service.user.grantedauthority.AuthoritiesLoader;
import com.revencoft.validation.constant.ValidationConstant;

/**
 * 用户初始访问web项目的时候，使用该类，加载用户权限信息
 * @author mengqingyan
 * @version 
 * @param <T>
 */
public abstract class AbstractDBAuthorityService<T> implements
		AuthorityService<T>, AuthoritiesLoader<T> {

	/**
	 * 在用户初始访问web项目时，要将用户的唯一标识信息（String 类型）放入session，
	 * key为{@link com.revencoft.validation.constant.ValidationConstant.sessionUserTokenKey}
	 */
	@Override
	public T loadAuthority(ActionInvocation invocation) {
		Map<String, Object> session = invocation.getInvocationContext().getSession();
		String userToken = (String) session.get(ValidationConstant.sessionUserTokenKey);
		
		return loadGrantedAuthorities(userToken);
	}
	
	
}
