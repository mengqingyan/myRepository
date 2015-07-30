/**
 * 
 */
package com.revencoft.validation.auth;

import java.io.Serializable;

/**
 * 表示用户所能访问的资源
 * @author mengqingyan
 * @version 2.0
 */
public interface GrantedResource extends Serializable {
	
	String getResource();
}
