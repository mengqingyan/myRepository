/**
 * 
 */
package com.revencoft.example.aspect;

import java.lang.reflect.Method;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.revencoft.example.annotation.TimerLog;
import com.revencoft.example.annotation.TimerLog.LEVEL;

/**
 * @author mengqingyan
 * @version 
 */
@Component
@Aspect
@Order(Ordered.LOWEST_PRECEDENCE)
public class LogAspect {
	
	private final Logger log = Logger.getLogger(getClass());
	
	@Pointcut("@annotation(com.revencoft.example.annotation.TimerLog)")
	public void timerLog() {}
	
	
	@Around("timerLog()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		
		MethodSignature methodSig = (MethodSignature) joinPoint.getSignature();
		Method method = methodSig.getMethod();
		TimerLog timerLog = method.getAnnotation(TimerLog.class);
		return proccedWithLog(timerLog, joinPoint, method);
	}


	/**
	 * @param timerLog
	 * @param joinPoint
	 * @param method
	 * @return
	 * @throws Throwable
	 */
	private Object proccedWithLog(TimerLog timerLog,
			ProceedingJoinPoint joinPoint, Method method) throws Throwable {
		Priority priority = null;
		LEVEL value = timerLog.value();
		switch (value) {
		case debug:
			priority = Level.DEBUG;
			break;
		case info:
			priority = Level.INFO;
			break;
		case warn:
			priority = Level.WARN;
			break;
		case error:
			priority = Level.ERROR;
			break;
		default:
			priority = Level.DEBUG;
		}
		Object[] args = joinPoint.getArgs();
		long start = System.currentTimeMillis();

		Object result = joinPoint.proceed(args);
		
		long end = System.currentTimeMillis();
		log.log(priority, String.format(
				"method[%s] has been executed,time elapsed by ms: %s", method.toString(),
				(end - start)));
		return result;
	}
	
}
