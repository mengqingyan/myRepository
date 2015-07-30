/**
 * 
 */
package com.revencoft.validation.auth.service.resource.grantedauthority;

import java.util.Collection;

import com.revencoft.validation.auth.GrantedAuthority;
import com.revencoft.validation.cache.Cache;

/**
 * @author mengqingyan
 * @version 2.0
 */
public interface ResourceAuthorityService {

	/**
	 * 获取所有资源对应的所有权限集合；<br/>
	 * @return 返回Cache结构，便于进行缓存管理
	 */
	Cache<String, Collection<GrantedAuthority>> getAllResourceAuthorities();
	
	/**
	 * 获取单一资源对应的权限
	 * @param uri
	 * @return
	 */
//	Collection<GrantedAuthority> getResourceGrantedAuthority(String uri);
}
