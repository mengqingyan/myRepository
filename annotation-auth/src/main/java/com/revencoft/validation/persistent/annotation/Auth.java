package com.revencoft.validation.persistent.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * action 方法执行权限限制
 * @author mengqingyan
 * @version 2.0
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = { ElementType.METHOD })
public @interface Auth {

	//角色字符串，以","隔开
	String value();
}
