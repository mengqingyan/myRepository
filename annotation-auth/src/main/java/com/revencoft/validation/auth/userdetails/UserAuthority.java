/**
 * 
 */
package com.revencoft.validation.auth.userdetails;

import java.util.Collection;

import com.revencoft.validation.auth.GrantedAuthority;
import com.revencoft.validation.auth.GrantedResource;

/**
 * @author mengqingyan
 * @version 
 */
public interface UserAuthority {

	Collection<GrantedAuthority> getAuthorities();
	
	Collection<GrantedResource> getResources();
	/**
	 * 状态是否合法
	 * @return
	 */
	boolean isEnable();
}
