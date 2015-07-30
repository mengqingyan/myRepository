/**
 * 
 */
package com.revencoft.validation.auth.service.user.grantedauthority;

import java.util.Collection;

import com.revencoft.validation.auth.GrantedAuthority;
import com.revencoft.validation.auth.service.AuthorityService;


/**
 * 获取用户权限信息
 * @author mengqingyan
 * @version 2.0
 */
public interface UserGrantedAuthorityService extends AuthorityService<Collection<GrantedAuthority>> {
}