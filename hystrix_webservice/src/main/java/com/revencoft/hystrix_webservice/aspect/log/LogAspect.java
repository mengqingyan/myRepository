/**
 * 
 */
package com.revencoft.hystrix_webservice.aspect.log;

import java.lang.reflect.Method;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.revencoft.hystrix_webservice.annotation.TimerLog;
import com.revencoft.hystrix_webservice.annotation.TimerLog.LEVEL;

/**
 * @author mengqingyan
 * @version 
 */
@Component
@Aspect
@Order(Ordered.LOWEST_PRECEDENCE)
public class LogAspect {
	
	private final Logger log = Logger.getLogger(getClass());
	
	@Pointcut("@annotation(com.revencoft.hystrix_webservice.annotation.TimerLog)")
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
		Level priority = null;
		LEVEL value = timerLog.value();
		switch (value) {
		case DEBUG:
			priority = Level.DEBUG;
			break;
		case INFO:
			priority = Level.INFO;
			break;
		case WARN:
			priority = Level.WARN;
			break;
		case ERROR:
			priority = Level.ERROR;
			break;
		default:
			priority = Level.DEBUG;
		}
		Object[] args = joinPoint.getArgs();
		
		Level logLevel = log.getEffectiveLevel();
		if(logLevel.toInt() > priority.toInt()) {
			return joinPoint.proceed(args);
		}
		
		long start = System.currentTimeMillis();

		Object result = joinPoint.proceed(args);
		
		long end = System.currentTimeMillis();
		log.log(priority, String.format(
				"method[%s] has been executed,time elapsed by ms: %s", method.toString(),
				(end - start)));
		return result;
	}
	
}
