package com.revencoft.example.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注方法，打印方法执行耗时多少
 * @author mengqingyan
 * @version
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = { ElementType.METHOD })
public @interface TimerLog {
	
	public LEVEL value() default LEVEL.debug;
	
	/**
	 * 打印级别
	 * @author mengqingyan
	 * @version
	 */
	public enum LEVEL {
		error,warn,info,debug
	}
}
