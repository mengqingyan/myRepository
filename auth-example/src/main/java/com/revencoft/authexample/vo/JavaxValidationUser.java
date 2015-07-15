/**
 * 
 */
package com.revencoft.authexample.vo;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author mengqingyan
 * @version 
 */
public class JavaxValidationUser {
	
	private Integer id;
	@NotEmpty
	@Size(min=4, max=10)
	private String username;
	@NotEmpty
	@Size(min=4, max=17)
	private String password;
	
	/**
	 * 注解标注对Employee进行属性校验<br/>
	 * （向emp设置值时，由struts进行实例化，此时才进行校验，不设置值时不校验）
	 */
	@Valid
	private JavaxValidationEmployee emp;
	
	@NotEmpty
	private String address;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public JavaxValidationEmployee getEmp() {
		return emp;
	}

	public void setEmp(JavaxValidationEmployee emp) {
		this.emp = emp;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
