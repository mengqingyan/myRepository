package com.revencoft.example.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 默认指定对象字段加密解密
 * 
 * 加密解密字段的类型为java.lang.String的类型且需要提供setter和getter器，否则忽略
 * 
 * @author YiLiang
 * 
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = { ElementType.FIELD })
public @interface SecurityAnnotation {
	boolean value() default true;
}
