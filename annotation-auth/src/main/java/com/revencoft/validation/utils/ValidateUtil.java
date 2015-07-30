package com.revencoft.validation.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

/**
 * @author mengqingyan
 * @version:
 * @since 1.0
 */

public final class ValidateUtil {
	
	private ValidateUtil() {}

	/**  
	  * 判断一个类是否为基本数据类型。  
	  * @param clazz 要判断的类。  
	  * @return true 表示为基本数据类型。  
	  */ 
	 public static boolean isBaseDataType(Class<?> clazz)  throws Exception 
	 {   
	     return 
	     (   
	         clazz.equals(String.class) ||   
	         clazz.equals(Integer.class)||   
	         clazz.equals(Byte.class) ||   
	         clazz.equals(Long.class) ||   
	         clazz.equals(Double.class) ||   
	         clazz.equals(Float.class) ||   
	         clazz.equals(Character.class) ||   
	         clazz.equals(Short.class) ||   
	         clazz.equals(BigDecimal.class) ||   
	         clazz.equals(BigInteger.class) ||   
	         clazz.equals(Boolean.class) ||   
	         clazz.equals(Date.class) ||
	         clazz.isPrimitive()   
	     );   
	 }
	 
	 public static Object readFieldValue(Object target, Field field) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		 Assert.notNull(target, String.format("target object can't be null when get value of [%s]", field));
		 
		 PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(target.getClass(), field.getName());
		 if(pd == null) {
			 return null;
		 }
		 Method get = pd.getReadMethod();
		 return get.invoke(target);
	 }
}
