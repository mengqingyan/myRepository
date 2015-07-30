package com.revencoft.validation.persistent.annotation.handler;

import java.lang.annotation.Annotation;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import com.revencoft.validation.exception.FieldValidationException;
import com.revencoft.validation.persistent.annotation.Regex;
import com.revencoft.validation.persistent.annotation.Regex.Format;

/**
 * 字段为空时，不进行正则校验
 * @author mengqingyan
 * @since 1.2.2
 */

public class RegexValidateAnnotationHandler extends
		AbstractValidateAnnotationHandler {

	private static final Logger log = Logger
			.getLogger(RegexValidateAnnotationHandler.class);

	public boolean supports(Annotation annotation) {
		Assert.notNull(annotation, "the annotation can't be null!");
		return Regex.class.equals(annotation.annotationType());
	}

	@Override
	boolean validate(Object value, Annotation annotation)
			throws FieldValidationException {
		if(value == null) {
			return true;
		}
		
		if (value instanceof String) {
			
			if("".equals(value)) {
				return true;
			}
			
			Regex regex = (Regex) annotation;
			String patternStr = regex.pattern();
			boolean checkResult = false;
			if (!"".equals(patternStr)) {
				checkResult = regexCheck(value.toString(), patternStr);
			} else {
				Format format = regex.regex();
				if (format == Format.none) {
					String msg = "you must assign a regex to the annotation[@Regex]!";
					log.error(msg);
					throw new FieldValidationException(regex, msg);
				} else {
					checkResult = regexCheck(value.toString(),
							format.getRegex());
				}
			}
			if (checkResult) {
				return true;
			}
		}
		return false;
	}

	private boolean regexCheck(String value, String patternStr) {
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(value);
		return matcher.find();
	}

}
