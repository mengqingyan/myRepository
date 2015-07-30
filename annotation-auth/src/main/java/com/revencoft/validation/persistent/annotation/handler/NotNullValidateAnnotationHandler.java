package com.revencoft.validation.persistent.annotation.handler;

import java.lang.annotation.Annotation;

import org.springframework.util.Assert;

import com.revencoft.validation.persistent.annotation.NotNull;

/**
 * @author mengqingyan
 * @since 1.2
 */

public class NotNullValidateAnnotationHandler extends
		AbstractValidateAnnotationHandler {

	public boolean supports(Annotation annotation) {
		Assert.notNull(annotation, "the annotation can't be null!");
		return NotNull.class.equals(annotation.annotationType());
	}

	@Override
	boolean validate(Object value, Annotation annotation) {
		if (value == null) {
			return false;
		}
		return true;
	}

}
