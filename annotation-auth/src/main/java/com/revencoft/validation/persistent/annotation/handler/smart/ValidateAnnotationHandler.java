/**
 * 
 */
package com.revencoft.validation.persistent.annotation.handler.smart;

import java.lang.annotation.Annotation;

import com.revencoft.validation.persistent.annotation.support.FieldValueHolder;

/**
 * @author mengqingyan
 * @version 
 */
public interface ValidateAnnotationHandler<A extends Annotation> {

	
	/**
	 * 
	 * @param fieldValueHolder field相关
	 * @param annotation 该field上的注解
	 * @throws Exception
	 */
	public void validate(FieldValueHolder fieldValueHolder, A annotation) throws Exception;;
}
