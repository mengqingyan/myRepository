/**
 * 
 */
package com.revencoft.validation.core;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author mengqingyan
 * @version 2.0
 */
public class SimpleAnnotationHolder implements AnnotationsHolder {

	protected List<Annotation> annotations;
	
	
	/**
	 * 
	 */
	public SimpleAnnotationHolder() {
		super();
	}
	/**
	 * @param annotations
	 */
	public SimpleAnnotationHolder(List<Annotation> annotations) {
		super();
		this.annotations = annotations;
	}
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Annotation> T contains(Class<T> clazz) {
		for (Annotation annotation : annotations) {
			if(annotation.annotationType().equals(clazz)) {
				return (T) annotation;
			}
		}
		return null;
	}
	@Override
	public List<Annotation> getAnnotations() {
		return annotations;
	}

	@Override
	public void setAnnotations(List<Annotation> annotations) {
		this.annotations = annotations;
	}

}
