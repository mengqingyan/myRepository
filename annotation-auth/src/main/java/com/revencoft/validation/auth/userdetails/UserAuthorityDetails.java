/**
 * 
 */
package com.revencoft.validation.auth.userdetails;

import java.util.Collection;

import com.revencoft.validation.auth.GrantedAuthority;
import com.revencoft.validation.auth.GrantedResource;

/**
 * @author mengqingyan
 * @version 2.0
 */
public class UserAuthorityDetails implements UserAuthority {
	
	private Collection<GrantedAuthority> authorities;
	
	private Collection<GrantedResource> resources;
	
	private boolean enable;

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public Collection<GrantedResource> getResources() {
		return resources;
	}

	@Override
	public boolean isEnable() {
		return enable;
	}

	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public void setResources(Collection<GrantedResource> resources) {
		this.resources = resources;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	
	

}
