/**
 * 
 */
package com.revencoft.validation.auth.service.user.grantedauthority;

import java.util.Collection;

import com.revencoft.validation.auth.GrantedAuthority;
import com.revencoft.validation.auth.service.AbstractDBAuthorityService;

/**
 * 自定义示例，加载用户角色信息（从数据库等地方加载）
 * @author mengqingyan
 * @version 
 */
public class CustomUserGrantedAuthorityServiceImpl extends
		AbstractDBAuthorityService<Collection<GrantedAuthority>> {

	private AuthoritiesLoader<Collection<GrantedAuthority>> authoritiesLoader;
	
	@Override
	public Collection<GrantedAuthority> loadGrantedAuthorities(
			Object userToken) {
		
//		if(!"test".equals(userToken)) {
//			return null;
//		}
//		
//		//可以从数据库中加载
//		
//		Set<GrantedAuthority> auths = new HashSet<GrantedAuthority>();
////		auths.add(new SimpleGrantedAuthority("admin"));
//		auths.add(new SimpleGrantedAuthority("user"));
//		return auths;
		
		return authoritiesLoader.loadGrantedAuthorities(userToken);
	}

	public void setAuthoritiesLoader(
			AuthoritiesLoader<Collection<GrantedAuthority>> authoritiesLoader) {
		this.authoritiesLoader = authoritiesLoader;
	}
}
