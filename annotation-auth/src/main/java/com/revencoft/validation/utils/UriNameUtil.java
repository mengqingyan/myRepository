/**
 * 
 */
package com.revencoft.validation.utils;

import com.opensymphony.xwork2.ActionProxy;

/**
 * @author mengqingyan
 * @version 
 */
public final class UriNameUtil {
	
	private static final String separator = "/";
	private UriNameUtil() {
		
	}

	public static String getFullActionName(ActionProxy aProxy) {
		return aProxy.getNamespace() + separator + aProxy.getActionName();
	}
}
