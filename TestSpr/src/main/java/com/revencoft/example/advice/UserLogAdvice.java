/**
 * 
 */
package com.revencoft.example.advice;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;

/**
 * @author mengqingyan
 * @version 
 */
public class UserLogAdvice implements MethodInterceptor {
	
	private final Logger log = Logger.getLogger(getClass());
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Method method = invocation.getMethod();
		Object[] arguments = invocation.getArguments();
		Class<? extends MethodInvocation> class1 = invocation.getClass();
		AccessibleObject staticPart = invocation.getStaticPart();
		Object this1 = invocation.getThis();
		
		log.info("begin log before proceed!");
		
		log.info("method: " + method);
		log.info("arguments: " + arguments);
		log.info("class1: " + class1);
		log.info("staticPart: " + staticPart);
		log.info("this1: " + this1);
		
		Object result = invocation.proceed();
		
		log.info("end.reult is: " + result);
		return result;
	}

}
