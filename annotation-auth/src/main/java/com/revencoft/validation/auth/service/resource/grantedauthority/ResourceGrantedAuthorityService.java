/**
 * 
 */
package com.revencoft.validation.auth.service.resource.grantedauthority;

import java.util.Collection;

import com.revencoft.validation.auth.GrantedAuthority;
import com.revencoft.validation.auth.service.AuthorityService;


/**
 * 获取资源所对应的权限集合
 * @author mengqingyan
 * @version 2.0
 */
public interface ResourceGrantedAuthorityService extends
		AuthorityService<Collection<GrantedAuthority>>,ResourceAuthorityService {
}