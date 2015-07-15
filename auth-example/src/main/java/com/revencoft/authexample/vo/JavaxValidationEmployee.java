/**
 * 
 */
package com.revencoft.authexample.vo;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;


/**
 * @author mengqingyan
 * @version 
 */
public class JavaxValidationEmployee {
	
	@NotEmpty
	@Size(max=10)
	private String username = "default";
	@NotEmpty
	@Size(min=6)
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
