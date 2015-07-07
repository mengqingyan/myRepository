/**
 * 
 */
package com.revencoft.authexample.vo;

import com.revencoft.validation.persistent.annotation.MaxLength;
import com.revencoft.validation.persistent.annotation.MinLength;
import com.revencoft.validation.persistent.annotation.NotEmpty;
import com.revencoft.validation.persistent.annotation.Valid;

/**
 * @author mengqingyan
 * @version 
 */
public class User {
	
	private Integer id;
	@NotEmpty
	@MinLength(6)
	private String username;
	@NotEmpty
	@MinLength(4)
	@MaxLength(17)
	private String password;
	
	/**
	 * 注解标注对Employee进行属性校验<br/>
	 * （向emp设置值时，由struts进行实例化，此时才进行校验，不设置值时不校验）
	 */
	@Valid
	private Employee emp;
	
	@NotEmpty
	private String address;
	
	/**
	 * 
	 */
	public User() {
	}
	/**
	 * @param username
	 * @param password
	 */
	public User(Integer id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
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

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Employee getEmp() {
		return emp;
	}
	public void setEmp(Employee emp) {
		this.emp = emp;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Override
	public String toString() {
		return String.format("User [id=%s, username=%s, password=%s]", id,
				username, password);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
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
		User other = (User) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	
}
