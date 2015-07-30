/**
 * 
 */
package com.revencoft.validation.persistent.annotation.decider;

import java.lang.annotation.Annotation;

import com.opensymphony.xwork2.ActionInvocation;
import com.revencoft.validation.exception.ValidationException;

/**
 * @author mengqingyan
 * @version 2.0
 * @param <E>
 * @param <T>
 */
public interface CacheableAnnotationDecider<K, V> extends AnnotationDecider {

	boolean decideToExec(ActionInvocation invocation, Annotation annotation,
			Object target, CacheCallBack<K, V> callBack) throws ValidationException;
	
	public interface CacheCallBack<K, V> {
		
		void addCacheableData(K key, V value);
		
	}
}
