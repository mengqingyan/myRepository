/**
 * 
 */
package com.revencoft.validation.utils.matcher;

import javax.servlet.http.HttpServletRequest;

/**
 * @author mengqingyan
 * @version 
 */
public interface RequestMatcher {

	
	boolean matches(HttpServletRequest request);
}
