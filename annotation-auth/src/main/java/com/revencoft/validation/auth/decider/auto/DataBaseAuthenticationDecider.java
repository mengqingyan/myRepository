/**
 * 
 */
package com.revencoft.validation.auth.decider.auto;

import java.util.Collection;

import com.opensymphony.xwork2.ActionInvocation;
import com.revencoft.validation.auth.GrantedAuthority;
import com.revencoft.validation.auth.decider.AuthenticationDecider;
import com.revencoft.validation.auth.service.resource.grantedauthority.ResourceGrantedAuthorityService;
import com.revencoft.validation.auth.service.user.grantedauthority.UserGrantedAuthorityService;
import com.revencoft.validation.exception.AuthenticationFailureException;
import com.revencoft.validation.exception.ValidationException;
import com.revencoft.validation.utils.UriNameUtil;

/**
 * 基于数据库中对资源的权限配置信息，自动完成访问资源的权限控制
 * @author mengqingyan
 * @version 2.0
 */
public class DataBaseAuthenticationDecider implements AuthenticationDecider {

	private UserGrantedAuthorityService userAuthService;
	
	private ResourceGrantedAuthorityService resourceAuthService;
	
	@Override
	public void authenticate(ActionInvocation invocation)
			throws ValidationException {
		
		String fActionName = UriNameUtil.getFullActionName(invocation.getProxy());
		Collection<GrantedAuthority> resourceGrantedAuthority = resourceAuthService.loadAuthority(invocation);
		
		if(resourceGrantedAuthority == null || resourceGrantedAuthority.isEmpty()) {
			return;
		}
		
		Collection<GrantedAuthority> userAuthority = userAuthService.loadAuthority(invocation);
		
		if(userAuthority == null || userAuthority.isEmpty()) {
			throw new AuthenticationFailureException("用户权限为空！");
		}
			
		boolean accessed = authenticate(userAuthority, resourceGrantedAuthority);
		
		if(!accessed) {
			throw new AuthenticationFailureException("用户无权限访问" + fActionName + "！", fActionName);
		}

	}
	/**
	 * @param userAuthority
	 * @param resourceGrantedAuthority
	 */
	private boolean authenticate(Collection<GrantedAuthority> userAuthority, Collection<GrantedAuthority> resourceGrantedAuthority) {

//		if(resourceGrantedAuthority == null || resourceGrantedAuthority.isEmpty()) {
//			return true;
//		}
//		if(userAuthority == null || userAuthority.isEmpty()) {
//			return false;
//		}
		for (GrantedAuthority auth : userAuthority) {
			if(resourceGrantedAuthority.contains(auth)) {
				return true;
			}
		}
		return false;
	}
	
	public void setUserAuthService(UserGrantedAuthorityService userAuthService) {
		this.userAuthService = userAuthService;
	}
	public void setResourceAuthService(
			ResourceGrantedAuthorityService resourceAuthService) {
		this.resourceAuthService = resourceAuthService;
	}
	
}
