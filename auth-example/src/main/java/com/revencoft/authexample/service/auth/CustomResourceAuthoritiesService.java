/**
 * 
 */
package com.revencoft.authexample.service.auth;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revencoft.authexample.db.CacheDB;
import com.revencoft.authexample.db.CacheDB.ResourceAndAuth;
import com.revencoft.validation.auth.GrantedAuthority;
import com.revencoft.validation.auth.SimpleGrantedAuthority;
import com.revencoft.validation.auth.service.resource.grantedauthority.AbstractResourceGrantedAuthorityService;

/**
 * 自定义资源对应权限信息加载类，用于从数据库或配置文件等处加载信息
 * @author mengqingyan
 * @version 
 */
@Service
public class CustomResourceAuthoritiesService extends
		AbstractResourceGrantedAuthorityService {
	
	@Autowired
	private CacheDB db;

	@Override
	protected Map<String, Collection<GrantedAuthority>> loadCustomResourceAuthority() {
		
		Set<Integer> keys = db.resourceAuth.keySet();
		if(keys == null || keys.isEmpty()) {
			return null;
		}
		
		Map<String, Collection<GrantedAuthority>> resAuths = new HashMap<String, Collection<GrantedAuthority>>();
		
		for (Iterator<Integer> iterator = keys.iterator(); iterator.hasNext();) {
			Integer key = iterator.next();
			ResourceAndAuth resourceAndAuth = db.resourceAuth.get(key);
			Integer uriId = resourceAndAuth.getUriId();
			Integer roleId = resourceAndAuth.getRoleId();
			String uri = db.resource.get(uriId);
			String role = db.auth.get(roleId);
			if(StringUtils.isNotBlank(uri) && StringUtils.isNotBlank(role)) {
				if(resAuths.containsKey(uri)) {
					Collection<GrantedAuthority> auth = resAuths.get(uri);
					auth.add(new SimpleGrantedAuthority(role));
				} else {
					Set<GrantedAuthority> auths = new HashSet<GrantedAuthority>();
					resAuths.put(uri, auths);
					auths.add(new SimpleGrantedAuthority(role));
				}
			}
		}
		if(resAuths.isEmpty()) {
			return null;
		}
		return resAuths;
	}
	
	@PostConstruct
	public void init() {
		annotationOnlyWhenPresent = false;
	}
	

}
