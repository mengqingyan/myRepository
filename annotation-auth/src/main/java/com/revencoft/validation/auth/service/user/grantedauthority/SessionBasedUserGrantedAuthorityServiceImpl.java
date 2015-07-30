/**
 * 
 */
package com.revencoft.validation.auth.service.user.grantedauthority;

import java.util.Collection;

import com.revencoft.validation.auth.GrantedAuthority;
import com.revencoft.validation.auth.service.AbstractSessionBasedAuthorityService;


/**
 * 从session中加载用户权限信息
 * @author mengqingyan
 * @version 
 */
public class SessionBasedUserGrantedAuthorityServiceImpl extends
		AbstractSessionBasedAuthorityService<Collection<GrantedAuthority>> implements
		UserGrantedAuthorityService {

}
