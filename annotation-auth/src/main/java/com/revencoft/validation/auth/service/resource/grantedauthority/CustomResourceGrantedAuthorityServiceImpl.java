/**
 * 
 */
package com.revencoft.validation.auth.service.resource.grantedauthority;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.revencoft.validation.auth.GrantedAuthority;
import com.revencoft.validation.auth.SimpleGrantedAuthority;

/**
 * 自定义测试类
 * @author mengqingyan
 * @version 
 */
public class CustomResourceGrantedAuthorityServiceImpl extends
		AbstractResourceGrantedAuthorityService {

	@Override
	protected Map<String, Collection<GrantedAuthority>> loadCustomResourceAuthority() {
		
		Map<String, Collection<GrantedAuthority>> res_auths = new HashMap<String, Collection<GrantedAuthority>>();
		
		Set<GrantedAuthority> user_auth = new HashSet<GrantedAuthority>();
		user_auth.add(new SimpleGrantedAuthority("user"));
		user_auth.add(new SimpleGrantedAuthority("admin"));
		
		Set<GrantedAuthority> admin_auth = new HashSet<GrantedAuthority>();
		admin_auth.add(new SimpleGrantedAuthority("admin"));
		admin_auth.add(new SimpleGrantedAuthority("test"));
		
		res_auths.put("/user_toStep\\d\\.do$", user_auth);
		res_auths.put("/user_doInit.*\\.do$", admin_auth);
		return res_auths;
	}

}
