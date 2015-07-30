/**
 * 
 */
package com.revencoft.validation.persistent.annotation.support;

import java.util.List;

/**
 * @author mengqingyan
 * @version 
 */
public interface ValidateObjectSupport {

	
	List<String> getObjectAnnotationNames();
	
	/**
	 * 设置标注对象的注解的名称
	 * @param objectAnnotationNames
	 */
	void setObjectAnnotationNames(List<String> objectAnnotationNames);
}
