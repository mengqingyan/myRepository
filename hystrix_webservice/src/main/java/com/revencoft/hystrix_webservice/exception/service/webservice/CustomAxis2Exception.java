/**
 * 
 */
package com.revencoft.hystrix_webservice.exception.service.webservice;

import org.springframework.http.HttpStatus;

/**
 * @author mengqingyan
 * @version 
 */
public class CustomAxis2Exception extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpStatus httpStatus;

	/**
	 * 
	 */
	public CustomAxis2Exception() {
		super();
	}

	/**
	 * @param message
	 */
	public CustomAxis2Exception(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}

	/**
	 * @return
	 */
	public HttpStatus getStatusCode() {
		return this.httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
	
	

}
