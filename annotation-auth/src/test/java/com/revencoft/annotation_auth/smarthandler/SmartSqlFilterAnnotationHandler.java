package com.revencoft.annotation_auth.smarthandler;

import java.lang.reflect.Field;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import com.revencoft.validation.exception.FieldValidationException;
import com.revencoft.validation.persistent.annotation.SqlFilter;
import com.revencoft.validation.persistent.annotation.handler.smart.ValidateAnnotationHandler;
import com.revencoft.validation.persistent.annotation.support.FieldValueHolder;
import com.revencoft.validation.utils.StrFilter;

/**
 * @author mengqingyan
 * @since 1.0
 */

public class SmartSqlFilterAnnotationHandler implements ValidateAnnotationHandler<SqlFilter> {
	
	private static final Logger log = Logger.getLogger(SmartSqlFilterAnnotationHandler.class);
	

	@Override
	public void validate(FieldValueHolder fieldValueHolder, SqlFilter annotation)
			throws Exception {
		Assert.notNull(fieldValueHolder, "the FieldValueHolder can't be null!");
		Assert.notNull(annotation, "the annotation can't be null!");
		
        Object value = fieldValueHolder.getFieldValue(); 
        Field field = fieldValueHolder.getField();
        
		log.debug("begin sqlFilter;field[" + field .getName() + "]'s value is [" + value +"]");
        if(value != null){
        	if (!StrFilter.paramCheck(value.toString())) {
				throw new FieldValidationException("非法参数,有sql注入风险 ["+field.getName()+":"+value+"]", field.getName(), annotation);
			}
        }

	}
}
