/**
 * 
 */
package com.revencoft.validation.auth.decider.annotationdecider;

import java.lang.annotation.Annotation;
import java.util.Collection;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.revencoft.validation.auth.GrantedAuthority;
import com.revencoft.validation.auth.support.UriAndAuthorityAnnotationsHolder;
import com.revencoft.validation.cache.Cache;
import com.revencoft.validation.cache.CachesHolder;
import com.revencoft.validation.exception.AuthenticationFailureException;
import com.revencoft.validation.exception.ValidationException;
import com.revencoft.validation.persistent.annotation.decider.CacheableAnnotationDecider;
import com.revencoft.validation.utils.UriNameUtil;


/**
 * @author mengqingyan
 * @version 
 */
public abstract class AbstractCacheableAuthenticationAnnotationDecider extends
		AbstractAuthenticationAnnotationDecider implements CacheableAnnotationDecider<String, UriAndAuthorityAnnotationsHolder>{
	
	protected CachesHolder<String> caches = CachesHolder.getInstance();
	protected final String AUTH_ACTION_KEY = this.getClass() + ".auth.action.key";
	
	protected final Cache<String, UriAndAuthorityAnnotationsHolder> authCache = caches.getNamedCache(AUTH_ACTION_KEY);
	
	public void authenticate(ActionInvocation invocation) throws ValidationException {
		
		ActionProxy proxy = invocation.getProxy();
		String fActionName = UriNameUtil.getFullActionName(proxy);
		if(!authCache.contains(fActionName)) {
			super.authenticate(invocation);
			return;
		}
		
		UriAndAuthorityAnnotationsHolder uriAuthoritiesHolder = authCache.get(fActionName);
		
		Object targetAction = invocation.getAction();
		Annotation validAnno = uriAuthoritiesHolder.contains(validDecider.getDecidedAnnotationType());
		if(null != validAnno) {
			//是否执行权限验证
			boolean toExec = validDecider.decideToExec(invocation, validAnno, targetAction);
			if(!toExec) {
				return;
			}
		}
		//权限验证
		Collection<GrantedAuthority> authorities = uriAuthoritiesHolder.getAuthorities();
		if(authorities == null || authorities.isEmpty()) {
			return;
		}
		
		
		
		Collection<GrantedAuthority> userAuthorities = userAuthoritiesService.loadAuthority(invocation);
		if(userAuthorities == null || userAuthorities.isEmpty()) {
			throw new AuthenticationFailureException("禁止访问action[" + fActionName + "]!", fActionName);
		}
		boolean isAuthValid = false;
		for (GrantedAuthority authority : userAuthorities) {
			if(authorities.contains(authority)) {
				isAuthValid = true;
				break;
			}
		}
		if(!isAuthValid) {
			throw new AuthenticationFailureException("禁止访问action[" + fActionName + "]!", fActionName);
		}
	}
}

