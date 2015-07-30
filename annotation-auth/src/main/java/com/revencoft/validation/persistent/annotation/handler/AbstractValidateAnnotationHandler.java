package com.revencoft.validation.persistent.annotation.handler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import com.revencoft.validation.exception.FieldValidationException;
import com.revencoft.validation.persistent.annotation.support.FieldValueHolder;

/**
 * @author mengqingyan
 * @since 1.2
 */

public abstract class AbstractValidateAnnotationHandler implements
		ValidateAnnotationHandler {

	private static final Logger log = Logger
			.getLogger(AbstractValidateAnnotationHandler.class);

	public void validate(FieldValueHolder fieldValueHolder,
			Annotation annotation) throws Exception {
		Assert.notNull(fieldValueHolder, "the FieldValueHolder can't be null!");
		Assert.notNull(annotation, "the annotation can't be null!");

		Object value = fieldValueHolder.getFieldValue();
		Field field = fieldValueHolder.getField();

		log.debug("begin " + annotation.annotationType().getSimpleName()
				+ " validation;field[" + field.getName() + "]'s value is ["
				+ value + "]");

		boolean passed = validate(value, annotation);

		if (passed) {
			return;
		}

		String errMsg = String.format(DEFAULT_ERROR_MSG, field, annotation,
				value);
		log.error(errMsg);
		throw new FieldValidationException(errMsg, field.getName(), annotation);

	}

	/**
	 * 
	 * @param value
	 *            field's value
	 * @param annotation
	 *            field's annotation
	 * @return 是否验证通过
	 * @throws FieldValidationException
	 */
	abstract boolean validate(Object value, Annotation annotation)
			throws FieldValidationException;

}
