package com.revencoft.validation.persistent.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author mengqingyan
 * @version:
 * @date: 2015年3月19日
 * @desc:
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = { ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface NotNull {

}
