/**
 * 
 */
package com.revencoft.validation.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author mengqingyan
 * @version 
 * @since 2.0
 */
public class AbstractGenericTypeGetter<T> {

	protected Class<?> getGenericType(int index) {
		Type genType = getClass().getGenericSuperclass();
		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		if (index >= params.length || index < 0) {
			throw new RuntimeException("Index outof bounds");
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}
		return (Class<?>) params[index];
	}
	
}
