/**
 * 
 */
package com.revencoft.validation.auth.decider.annotationdecider;

import java.lang.annotation.Annotation;

import com.opensymphony.xwork2.ActionInvocation;
import com.revencoft.validation.exception.ValidationException;
import com.revencoft.validation.persistent.annotation.Auth;
import com.revencoft.validation.persistent.annotation.decider.CacheableAnnotationDecider;

/**
 * 权限判决器
 * @author mengqingyan
 * @version 2.0
 */
public class AuthAnnotationDecider extends AbstractAuthenticationAnnotationDecider{

	private CacheableAnnotationDecider<?, ?> cacheableAuthenticationDecider;
	
	@Override
	public Class<? extends Annotation> getDecidedAnnotationType() {
		return Auth.class;
	}

	/**
	 * @param invocation
	 * @param annotation @Auth注解的实例
	 * @param target action实例
	 */
	@Override
	public boolean decideToExec(ActionInvocation invocation,
			Annotation annotation, Object target)
			throws ValidationException {
		return cacheableAuthenticationDecider.decideToExec(invocation, annotation, target, null);
	}
	
	public void setCacheableAuthenticationDecider(
			CacheableAnnotationDecider<?, ?> cacheableAuthenticationDecider) {
		this.cacheableAuthenticationDecider = cacheableAuthenticationDecider;
	}
	
	
}
