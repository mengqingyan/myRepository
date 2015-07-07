/**
 * 
 */
package com.revencoft.authexample.service.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.revencoft.authexample.db.CacheDB;
import com.revencoft.authexample.db.CacheDB.UserAndAuth;
import com.revencoft.authexample.vo.User;
import com.revencoft.validation.auth.GrantedAuthority;
import com.revencoft.validation.auth.SimpleGrantedAuthority;
import com.revencoft.validation.auth.service.user.grantedauthority.AuthoritiesLoader;

/**
 * 用户service
 * @author mengqingyan
 * @version 
 */

@Component
public class UserAuthService implements AuthoritiesLoader<Collection<GrantedAuthority>>{

	@Autowired
	private CacheDB db;
	
	public void insert(User user) {
		int size = db.userDB.size();
		user.setId(size + 1);
		db.userDB.put(user.getId(), user);
	}
	
	public User get(String username) {
		
		if(StringUtils.isBlank(username)) {
			return null;
		}
		
		Set<Integer> keySet = db.userDB.keySet();
		
		for (Iterator<Integer> iterator = keySet.iterator(); iterator.hasNext();) {
			Integer key = (Integer) iterator.next();
			User user = db.userDB.get(key);
			if(username.equals(user.getUsername())) {
				return user;
			}
		}
		return null;
	}
	
	public void del(String username) {
		User user = get(username);
		db.userDB.remove(user.getId());
	}
	
	public void saveOrUpdate(User user) {
		insert(user);
	}
	
	public boolean containsUser(User user) {
		if(user == null) {
			return false;
		}
		User userFromDB = get(user.getUsername());
		if(user.equals(userFromDB)) {
			return true;
		}
		return false;
	}
	

	@Override
	public Collection<GrantedAuthority> loadGrantedAuthorities(Object userToken) {
		if(userToken == null || !(userToken instanceof String)) {
			return null;
		}
		String userName = (String) userToken;
		User user = get(userName);
		if(user == null) {
			return null;
		}
		
		Integer id = user.getId();
		
		List<Integer> roleIds = new ArrayList<Integer>();
		
		Set<Integer> keySet = db.userAuth.keySet();
		for (Iterator<Integer> iterator = keySet.iterator(); iterator.hasNext();) {
			Integer key = (Integer) iterator.next();
			UserAndAuth userAndAuth = db.userAuth.get(key);
			if(userAndAuth.getUserId() == id) {
				roleIds.add(userAndAuth.getRoleId());
			}
		}
		
		if(roleIds.isEmpty()) {
			return null;
		}
		
		Set<GrantedAuthority> auths = new HashSet<GrantedAuthority>();;
		
		for(Integer roleId : roleIds) {
			String role = db.auth.get(roleId);
			auths.add(new SimpleGrantedAuthority(role));
		}
		return auths;
	}
}
