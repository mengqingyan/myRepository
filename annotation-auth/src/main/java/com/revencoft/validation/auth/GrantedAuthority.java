/**
 * 
 */
package com.revencoft.validation.auth;

import java.io.Serializable;

/**
 * 表示访问资源所需的用户权限
 * @author mengqingyan
 * @version 2.0
 */
public interface GrantedAuthority extends Serializable{

	
	String getAuthority();
}
