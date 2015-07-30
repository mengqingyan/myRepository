package com.revencoft.validation.persistent.annotation.handler;

import java.lang.annotation.Annotation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.opensymphony.xwork2.ActionContext;
import com.revencoft.validation.exception.FieldValidationException;
import com.revencoft.validation.persistent.annotation.ValidImageCode;

/**
 * 图片验证码验证
 * @author mengqingyan
 * @since 1.2
 */

public class ValidImageCodeAnnotationHandler extends
		AbstractValidateAnnotationHandler {

	public boolean supports(Annotation annotation) {
		Assert.notNull(annotation, "the annotation can't be null!");
		return ValidImageCode.class.equals(annotation.annotationType());
	}

	@Override
	boolean validate(Object value, Annotation annotation)
			throws FieldValidationException {
		ValidImageCode codeAnnotation = (ValidImageCode) annotation;
		String imageSessionKey = codeAnnotation.sessionKey();
		Object imageCodeObj = ActionContext.getContext().getSession()
				.get(imageSessionKey);
		if (imageCodeObj != null && value != null) {
			if (StringUtils.equalsIgnoreCase(imageCodeObj.toString(),
					value.toString())) {
				return true;
			}
		}
		return false;
	}

}
