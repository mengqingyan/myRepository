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
public class OpenAccCommand extends HystrixCommand<Object> {

	private ProceedingJoinPoint joinPoint;

	/**
	 * @param setter
	 */
	public OpenAccCommand(com.netflix.hystrix.HystrixCommand.Setter setter) {
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
		return "NULL";
	}

	/**
	 * @param joinPoint
	 */
	public void setJoinPoint(ProceedingJoinPoint joinPoint) {
		this.joinPoint = joinPoint;
	}

}
