/**
 * 
 */
package com.revencoft.validation.persistent.annotation.handler.smart;

import java.lang.annotation.Annotation;

import org.springframework.aop.support.AopUtils;
import org.springframework.core.GenericTypeResolver;
import org.springframework.util.Assert;

import com.revencoft.validation.persistent.annotation.support.FieldValueHolder;

/**
 * @author mengqingyan
 * @version 
 */
public class GenericValidationAnnotationHandlerAdapter implements
		SmartValidateAnnotationHandler {

	@SuppressWarnings("rawtypes")
	private final ValidateAnnotationHandler delegate;
	
	
	/**
	 * @param handler
	 */
	@SuppressWarnings("rawtypes")
	public GenericValidationAnnotationHandlerAdapter(
			ValidateAnnotationHandler delegate) {
		Assert.notNull(delegate, "Delegate ValidateAnnotationHandler must not be null");
		this.delegate = delegate;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void validate(FieldValueHolder fieldValueHolder,
			Annotation annotation) throws Exception {
		this.delegate.validate(fieldValueHolder, annotation);
	}

	@Override
	public boolean supports(Class<? extends Annotation> annotationType) {
		Class<?> typeArg = GenericTypeResolver.resolveTypeArgument(this.delegate.getClass(), ValidateAnnotationHandler.class);
		if (typeArg == null || typeArg.equals(Annotation.class)) {
			Class<?> targetClass = AopUtils.getTargetClass(this.delegate);
			if (targetClass != this.delegate.getClass()) {
				typeArg = GenericTypeResolver.resolveTypeArgument(targetClass, ValidateAnnotationHandler.class);
			}
		}
		return (typeArg == null || typeArg.isAssignableFrom(annotationType));
	}

}
