/**
 * 
 */
package com.revencoft.validation.auth.service.user.grantedauthority;

/**
 * 加载用户权限信息
 * @author mengqingyan
 * @version 
 * @param <T>
 */
public interface AuthoritiesLoader<T> {

	
	/**
	 * 从数据库或其他地方加载用户权限信息
	 * @param userToken
	 * @return
	 */
	T loadGrantedAuthorities(Object userToken);
	
}
