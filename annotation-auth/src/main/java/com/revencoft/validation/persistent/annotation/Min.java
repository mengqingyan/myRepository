package com.revencoft.validation.persistent.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注最小值
 * @author mengqingyan
 * 2015年3月19日
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = { ElementType.FIELD })
public @interface Min {
	int value() default 0;
}
