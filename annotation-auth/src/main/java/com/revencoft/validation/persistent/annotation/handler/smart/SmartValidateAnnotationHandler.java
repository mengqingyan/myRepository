/**
 * 
 */
package com.revencoft.validation.persistent.annotation.handler.smart;

import java.lang.annotation.Annotation;

/**
 * @author mengqingyan
 * @version 
 */
public interface SmartValidateAnnotationHandler extends
		ValidateAnnotationHandler<Annotation> {

	
	/**
	 * 是否支持处理该annotation
	 * @param annotaion
	 * @return
	 */
	public boolean supports(Class<? extends Annotation> annotationType);
	
}
