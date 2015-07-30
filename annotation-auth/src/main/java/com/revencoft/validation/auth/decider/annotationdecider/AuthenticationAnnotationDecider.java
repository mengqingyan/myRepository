/**
 * 
 */
package com.revencoft.validation.auth.decider.annotationdecider;

import com.revencoft.validation.auth.decider.AuthenticationDecider;
import com.revencoft.validation.persistent.annotation.decider.AnnotationDecider;

/**
 * 权限认证
 * @author mengqingyan
 * @version 2.0
 */
public interface AuthenticationAnnotationDecider extends AnnotationDecider, AuthenticationDecider {

	/**
	 * 设置注解判决器
	 * @param validDecider
	 */
	void setValidDecider(AnnotationDecider validDecider);
	
	
}
