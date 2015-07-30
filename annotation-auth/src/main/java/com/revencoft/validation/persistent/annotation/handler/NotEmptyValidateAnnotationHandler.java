package com.revencoft.validation.persistent.annotation.handler;

import java.lang.annotation.Annotation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.revencoft.validation.persistent.annotation.NotEmpty;

/**
 * 非空验证
 * @author mengqingyan
 * @since 1.2
 */

public class NotEmptyValidateAnnotationHandler extends
		AbstractValidateAnnotationHandler {

	public boolean supports(Annotation annotation) {

		Assert.notNull(annotation, "the annotation can't be null!");

		return NotEmpty.class.equals(annotation.annotationType());
	}

	@Override
	boolean validate(Object value, Annotation annotation) {
		if (value != null && value instanceof String) {
			if (StringUtils.isNotBlank(value.toString())) {
				return true;
			}
		}
		return false;
	}

}
