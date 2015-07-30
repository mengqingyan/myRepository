/**
 * 
 */
package com.revencoft.validation.utils;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.revencoft.validation.auth.GrantedAuthority;
import com.revencoft.validation.auth.SimpleGrantedAuthority;
import com.revencoft.validation.persistent.annotation.Auth;

/**
 * @author mengqingyan
 * @version 
 */
public final class AuthorityUtil {

	private AuthorityUtil() {
	}
	
	/**
	 * 将注解中的值封装成权限集合
	 * @param auth
	 * @param annotationType
	 * @return
	 */
	public static Collection<GrantedAuthority> getGrantedAuthorities(
			Annotation auth, Class<? extends Annotation> annotationType) {
		if(auth == null || auth.annotationType() != annotationType) {
			return null;
		}
		Auth authAnno = (Auth) auth;
		String roleStr = authAnno.value();
		String[] roles = StringUtils.split(roleStr, ",");
		if(roles == null || roles.length == 0) {
			return null;
		}
		Set<GrantedAuthority> authrities = new HashSet<GrantedAuthority>();
		for(String role : roles) {
			authrities.add(new SimpleGrantedAuthority(role));
		}
		return authrities;
	}
}
