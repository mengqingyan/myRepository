/**
 * 
 */
package com.revencoft.validation.persistent.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.beans.factory.InitializingBean;

import com.opensymphony.xwork2.ActionInvocation;
import com.revencoft.validation.core.AnnotationsHolder;
import com.revencoft.validation.exception.JavaxValidationException;
import com.revencoft.validation.persistent.annotation.support.FieldAndAnnotationsHolder;

/**
 * @author mengqingyan
 * @version 
 */
public class JavaxPropertyValidator extends
		AbstractCacheableConditionalPropertyValidator implements InitializingBean{
	
	private Validator validator;

	/**
	 * @param target action中的field实例
	 * @param holder action中的field对应的注解集合
	 */
	@Override
	protected void doValidate(Object target, AnnotationsHolder holder,
			ActionInvocation invocation) throws Exception {
		FieldAndAnnotationsHolder fieldAnnoHolder = (FieldAndAnnotationsHolder) holder;
		Set<ConstraintViolation<Object>> violations = validator.validate(target);
		if(!violations.isEmpty()) {
			throw new JavaxValidationException(violations, fieldAnnoHolder.getField().getName());
		}
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

}
