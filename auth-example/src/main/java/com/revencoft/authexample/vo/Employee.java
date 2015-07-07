/**
 * 
 */
package com.revencoft.authexample.vo;

import com.revencoft.validation.persistent.annotation.MinLength;
import com.revencoft.validation.persistent.annotation.NotEmpty;

/**
 * @author mengqingyan
 * @version 
 */
public class Employee {
	
	@NotEmpty
	private String username = "default";
	@NotEmpty
	@MinLength(6)
	private String password = "default";
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
