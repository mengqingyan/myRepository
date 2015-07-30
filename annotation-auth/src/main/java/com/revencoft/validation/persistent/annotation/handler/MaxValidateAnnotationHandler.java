package com.revencoft.validation.persistent.annotation.handler;

import java.lang.annotation.Annotation;

import org.springframework.util.Assert;

import com.revencoft.validation.persistent.annotation.Max;

/**
 * @author mengqingyan
 * @since 1.2
 */

public class MaxValidateAnnotationHandler extends
		AbstractValidateAnnotationHandler {

	public boolean supports(Annotation annotation) {

		Assert.notNull(annotation, "the annotation can't be null!");
		return Max.class.equals(annotation.annotationType());
	}

	@Override
	boolean validate(Object value, Annotation annotation) {
		int max = ((Max) annotation).value();
		if (value != null && value instanceof Number) {
			Number n = (Number) value;
			if (n.doubleValue() <= max) {
				return true;
			}
		}
		return false;
	}

}
