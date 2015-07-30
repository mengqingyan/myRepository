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
public interface AnnotationsHolder {

	/**
	 * 是否包含该类型的注解
	 * @param clazz
	 * @return 注解实例或null
	 */
	public abstract <T extends Annotation> T contains(Class<T> clazz);

	/**
	 * 返回field上标注的注解集合，若没有，返回empty list
	 * @return
	 */
	public abstract List<Annotation> getAnnotations();

	public abstract void setAnnotations(List<Annotation> annotations);

}