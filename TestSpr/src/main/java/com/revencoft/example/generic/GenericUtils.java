/**
 * 
 */
package com.revencoft.example.generic;

import java.lang.reflect.Array;

import com.revencoft.example.generic.factory.GenericFactory;

/**
 * @author mengqingyan
 * @version 
 */
public final class GenericUtils {

	private GenericUtils(){}
	
	public static <T> boolean testInstance(Class<T> clazz, Object obj) {
		return clazz.isInstance(obj);
	}
	
	public static <T> T createGeneric(Class<T> clazz) {
		T t=null;
		try {
			t = clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return t;
	}
	
	public static <T> T createGeneric(GenericFactory<T> factory) {
		return factory.create();
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T[] createGenericArray(Class<T> clazz, int size) {
		T[] array;
		array = (T[]) Array.newInstance(clazz, size);
		return array;
	}
}
