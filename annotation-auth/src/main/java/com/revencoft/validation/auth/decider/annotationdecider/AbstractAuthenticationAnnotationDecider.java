/**
 * 
 */
package com.revencoft.validation.auth.decider.annotationdecider;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.revencoft.validation.auth.service.user.grantedauthority.UserGrantedAuthorityService;
import com.revencoft.validation.core.AnnotationsHolder;
import com.revencoft.validation.core.SimpleAnnotationHolder;
import com.revencoft.validation.exception.AuthenticationFailureException;
import com.revencoft.validation.exception.ValidationException;
import com.revencoft.validation.persistent.annotation.decider.AnnotationDecider;
import com.revencoft.validation.utils.UriNameUtil;

/**
 * @author mengqingyan
 * @version 2.0
 */
public abstract class AbstractAuthenticationAnnotationDecider implements AuthenticationAnnotationDecider {
	
	protected AnnotationDecider validDecider;
	protected final Logger log = Logger.getLogger(this.getClass());
	protected UserGrantedAuthorityService userAuthoritiesService;

	@Override
	public void authenticate(ActionInvocation invocation)
			throws ValidationException {
		Object targetAction = invocation.getAction();
		String fActionName = UriNameUtil.getFullActionName(invocation.getProxy());
		AnnotationsHolder annoHolder = createAnnotationsHolder(invocation);
		Annotation validAnno = annoHolder.contains(validDecider.getDecidedAnnotationType());
		if(null != validAnno) {
			boolean toExec = validDecider.decideToExec(invocation, validAnno, targetAction);
			if(!toExec) {
				return;
			}
		}
		Annotation authAnno = annoHolder.contains(getDecidedAnnotationType());
		if(authAnno != null) {
			boolean isAuthValid = decideToExec(invocation, authAnno, targetAction);
			if(!isAuthValid) {
				throw new AuthenticationFailureException("禁止访问链接[" + fActionName + "]!", fActionName).setErrorAnno(authAnno);
			}
		}
	}
	
	
	protected AnnotationsHolder createAnnotationsHolder(ActionInvocation invocation) throws ValidationException {
		
		Object targetAction = invocation.getAction();
		ActionProxy proxy = invocation.getProxy();
		String methodName = proxy.getMethod();
		
		Method method;
		try {
			method = targetAction.getClass().getDeclaredMethod(methodName, new Class[0]);
		} catch (Exception e) {
			String errMsg = "获取method[" + methodName + "]失败！";
			log .error(errMsg, e);
			throw new AuthenticationFailureException(errMsg);
		}
		Annotation[] annotations = method.getDeclaredAnnotations();
		List<Annotation> annos = Arrays.asList(annotations);
		return new SimpleAnnotationHolder(annos);
	}
	
	
	@Override
	public void setValidDecider(AnnotationDecider validDecider) {
		this.validDecider = validDecider;
	}


	public void setUserAuthoritiesService(
			UserGrantedAuthorityService userAuthoritiesService) {
		this.userAuthoritiesService = userAuthoritiesService;
	}
	
	
}
