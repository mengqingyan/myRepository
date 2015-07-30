package com.revencoft.validation.persistent.annotation.handler;

import java.lang.annotation.Annotation;

import org.springframework.util.Assert;

import com.revencoft.validation.persistent.annotation.Min;

/**
 * @author mengqingyan
 * @since 1.2
 */

public class MinValidateAnnotationHandler extends
		AbstractValidateAnnotationHandler {

	public boolean supports(Annotation annotation) {

		Assert.notNull(annotation, "the annotation can't be null!");
		return Min.class.equals(annotation.annotationType());
	}

	@Override
	boolean validate(Object value, Annotation annotation) {
		int min = ((Min) annotation).value();
		if (value != null && value instanceof Number) {
			Number n = (Number) value;
			if (n.doubleValue() >= min) {
				return true;
			}
		}
		return false;
	}

}
