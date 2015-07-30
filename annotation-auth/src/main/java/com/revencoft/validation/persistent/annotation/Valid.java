package com.revencoft.validation.persistent.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注验证某个VO,可以指定在什么条件下，进行验证；默认无过滤条件
 * @author mengqingyan
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = { ElementType.FIELD, ElementType.METHOD})
public @interface Valid {
	//method过滤
	String methods() default "";
	
	//SPEL或OGNL字符串，运算后结果为true或false
	String[] conditions() default {};
	
	//指定condition的解析方式
	ExpressionType parseType() default ExpressionType.SPEL;
	
	public enum ExpressionType {
		OGNL, SPEL
	}
}
