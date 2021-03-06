package com.revencoft.validation.persistent.annotation.handler;

import java.lang.annotation.Annotation;

import org.springframework.util.Assert;

import com.revencoft.validation.persistent.annotation.MinLength;

/**
 * @author mengqingyan
 * @since 1.2
 */

public class MinLengthValidateAnnotationHandler extends
		AbstractValidateAnnotationHandler {

	public boolean supports(Annotation annotation) {

		Assert.notNull(annotation, "the annotation can't be null!");

		return MinLength.class.equals(annotation.annotationType());
	}

	@Override
	boolean validate(Object value, Annotation annotation) {
		int minLen = ((MinLength) annotation).value();
		if (value != null && value instanceof String) {
			if (value.toString().length() >= minLen) {
				return true;
			}
		}
		return false;
	}

}
