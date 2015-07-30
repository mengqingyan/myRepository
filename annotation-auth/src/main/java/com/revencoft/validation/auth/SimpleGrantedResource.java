/**
 * 
 */
package com.revencoft.validation.auth;

import org.springframework.util.Assert;

/**
 * @author mengqingyan
 * @version 
 */
public class SimpleGrantedResource implements GrantedResource {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final String resource;
	
	/**
	 * @param resource
	 */
	public SimpleGrantedResource(String resource) {
		Assert.hasText(resource, "A granted resource textual representation is required");
		this.resource = resource;
	}

	@Override
	public String getResource() {
		return resource;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((resource == null) ? 0 : resource.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleGrantedResource other = (SimpleGrantedResource) obj;
		if (resource == null) {
			if (other.resource != null)
				return false;
		} else if (!resource.equals(other.resource))
			return false;
		return true;
	}

	
}
