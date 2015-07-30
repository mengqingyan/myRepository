package com.revencoft.validation.persistent.annotation.decider;

import java.lang.annotation.Annotation;

import com.opensymphony.xwork2.ActionInvocation;
import com.revencoft.validation.exception.UnsupportAnnotationException;
import com.revencoft.validation.exception.ValidationException;

/**
 * 注解判决器,以注解为拦截点，判断是否执行执行下面的注解操作
 * @author mengqingyan
 * @version: 
 * @since 1.0
 */

public interface AnnotationDecider {
	
	/**
	 * 得到进行限定条件的注解
	 * @return
	 */
	Class<? extends Annotation> getDecidedAnnotationType();

	/**
	 * 是否继续执行
	 * @param invocation
	 * @param annotation
	 * @param target
	 * @return
	 * @throws UnsupportAnnotationException
	 */

	boolean decideToExec(ActionInvocation invocation, Annotation annotation,
			Object target) throws ValidationException;

}
