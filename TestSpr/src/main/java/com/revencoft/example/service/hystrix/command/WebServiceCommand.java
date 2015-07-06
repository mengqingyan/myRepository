/**
 * 
 */
package com.revencoft.example.service.hystrix.command;

import org.aspectj.lang.ProceedingJoinPoint;

import com.netflix.hystrix.HystrixCommand;

/**
 * @author mengqingyan
 * @version 
 */
public class WebServiceCommand extends HystrixCommand<Object> {

	private ProceedingJoinPoint joinPoint;
	
	public static final Object DEFAULT_ERROR_FALLBACK = null;

	/**
	 * @param setter
	 */
	public WebServiceCommand(com.netflix.hystrix.HystrixCommand.Setter setter) {
		super(setter);
	}

	@Override
	protected Object run() throws Exception {
		try {
			Object[] args = joinPoint.getArgs();
			return this.joinPoint.proceed(args);
		} catch (Throwable e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Override
	protected Object getFallback() {
		return DEFAULT_ERROR_FALLBACK;
	}

	/**
	 * @param joinPoint
	 */
	public void setJoinPoint(ProceedingJoinPoint joinPoint) {
		this.joinPoint = joinPoint;
	}

}
