package com.revencoft.validation.persistent.annotation.handler;

import java.lang.annotation.Annotation;

import com.revencoft.validation.persistent.annotation.support.FieldValueHolder;

/**
 * 注解处理器
 * @author mengqingyan
 * @since 1.0
 */

public interface ValidateAnnotationHandler {
	
	public String DEFAULT_ERROR_MSG = "the value of [%s],annotated with [%s], is [%s],and it's invalid!";
	public String DEFAULT_SUCC_MSG = "the value of [%s],annotated with [%s], is [%s],and it's valid!";

	/**
	 * 是否支持处理该annotation
	 * @param annotaion
	 * @return
	 */
	public boolean supports(Annotation annotation);
	
	
	/**
	 * 
	 * @param fieldValueHolder field相关
	 * @param annotation 该field上的注解
	 * @throws Exception
	 */
	public void validate(FieldValueHolder fieldValueHolder, Annotation annotation) throws Exception;
}
