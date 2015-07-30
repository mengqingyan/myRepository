package com.revencoft.validation.persistent.annotation.support;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

import com.revencoft.validation.core.AnnotationsHolder;

/**
 * @author mengqingyan
 * @since 2.0
 */

public interface FieldAndAnnotationsHolder extends AnnotationsHolder {
	
	
	Field getField();
	/**
	 * target对应的field的值
	 * @param target
	 * @return
	 * @throws Exception 
	 */
	Object getFieldValue(Object target) throws Exception;

	List<Annotation> getObjectAnnotations();
	
	void setObjectAnnotations(List<Annotation> annotations);
	
	boolean containsObjAnnotation();
	
	boolean isBaseDataType() throws Exception;
	
	boolean equals(Object obj);
	
	int hashCode();
	
	String toString();
	
}
