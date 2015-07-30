package com.revencoft.validation.persistent.annotation.support;

import java.lang.reflect.Field;

import org.springframework.util.Assert;

/**
 * @author mengqingyan
 * @version:
 * @date: 2015年3月25日
 * @desc:
 */

public final class FieldValueEntity implements FieldValueHolder {
	
	private Field field;
	
	private Object fieldValue;
	
	private Object target;
	

	public FieldValueEntity(Field field, Object fieldValue, Object target) {
		super();
		Assert.notNull(field, "the Field can't be null!");
		Assert.notNull(target, "the target can't be null!");
		
		this.field = field;
		this.fieldValue = fieldValue;
		this.target = target;
	}

	public Object getFieldValue() {
		return fieldValue;
	}

	public Object getTarget() {
		return target;
	}

	public Field getField() {
		return field;
	}

}
