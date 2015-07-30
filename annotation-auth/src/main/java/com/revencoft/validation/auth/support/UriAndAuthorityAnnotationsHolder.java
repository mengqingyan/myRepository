/**
 * 
 */
package com.revencoft.validation.auth.support;

import java.util.Collection;

import com.revencoft.validation.auth.GrantedAuthority;
import com.revencoft.validation.core.AnnotationsHolder;
import com.revencoft.validation.persistent.annotation.decider.AnnotationDecider;

/**
 * @author mengqingyan
 * @version 2.0
 */
public interface UriAndAuthorityAnnotationsHolder extends AnnotationsHolder {

	/**
	 * 资源
	 * @return
	 */
	String getUri();
	
	/**
	 * 访问该资源所需的权限
	 * @return
	 */
	Collection<GrantedAuthority> getAuthorities();
	
	AnnotationDecider getAnnotationDecider();
}
