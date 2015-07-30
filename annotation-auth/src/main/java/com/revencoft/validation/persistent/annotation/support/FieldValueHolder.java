package com.revencoft.validation.persistent.annotation.support;

import java.lang.reflect.Field;

/**
 * @author mengqingyan
 * @version:
 * @date: 2015年3月25日
 * @desc:
 */

public interface FieldValueHolder {

	/**
	 * @return field的值
	 */
	Object getFieldValue();
	
	/**
	 * 
	 * @return 目标对象
	 */
	Object getTarget();
	
	/**
	 * 
	 * @return field反射域
	 */
	Field getField();
}
