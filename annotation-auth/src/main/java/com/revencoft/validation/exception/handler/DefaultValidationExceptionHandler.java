/**
 * 
 */
package com.revencoft.validation.exception.handler;

import java.lang.annotation.Annotation;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.TextProvider;
import com.opensymphony.xwork2.inject.Inject;
import com.revencoft.validation.exception.JavaxValidationException;
import com.revencoft.validation.exception.ValidationException;

/**
 * @author mengqingyan
 * @version 
 */
public class DefaultValidationExceptionHandler implements
		ValidationExceptionHandler {

	private final Logger log = Logger.getLogger(getClass());
	
	private TextProvider textProvider;
	
	@Override
	public void processValidationException(ActionInvocation invocation,
			Exception e) {
		
		if(e instanceof JavaxValidationException) {
			JavaxValidationException je = (JavaxValidationException) e;


			Set<ConstraintViolation<Object>> violations = je.getViolations();
			String prefix = je.getValidationKeyStr() + ".";
			for (ConstraintViolation<Object> violation : violations) {
				addFieldError(invocation, prefix + violation.getPropertyPath().toString(), violation.getMessage());
			}
			return;
		}
		
		if(e instanceof ValidationException) {
			ValidationException ve = (ValidationException) e;
			log.error(ve.getMessage());
			String msg = findAccurateErrorMsg(invocation, ve);
			setActionErrorMsg(invocation, msg);
		} else {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param invocation 
	 * @param propertyPath
	 * @param message
	 */
	private void addFieldError(ActionInvocation invocation, String propertyPath, String message) {
		Object action = invocation.getAction();
		if(action instanceof ActionSupport) {
			ActionSupport ba = (ActionSupport) action;
			ba.addFieldError(propertyPath, message);;
		}
	}

	private String findAccurateErrorMsg(ActionInvocation invocation, ValidationException ve) {
		Annotation errorAnno = ve.getErrorAnno();
		String msg = null;
		if(errorAnno != null) {
			msg = getErrorMessage(invocation, ve.getDefaultErrorMsgKeyPrefix() + errorAnno.annotationType()
					.getSimpleName() + "." + ve.getValidationKeyStr(), null);
			if(msg != null) {
				return msg;
			} else {
				msg = getErrorMessage(invocation, ve.getDefaultErrorMsgKeyPrefix() + errorAnno.annotationType()
						.getSimpleName(), null);
				if(msg != null) {
					return msg;
				}
			}
		}
		return getErrorMessage(invocation, ve.getDefaultErrorMsgKeyPrefix() + ve.getValidationKeyStr(), ve.getDefaultErrorMsg());
	}

	protected void setActionErrorMsg(ActionInvocation invocation, String errMsg) {
		Object action = invocation.getAction();
		if(action instanceof ActionSupport) {
			ActionSupport ba = (ActionSupport) action;
			ba.addActionError(errMsg);
		}
	}
	
	protected String getErrorMessage(ActionInvocation invocation, String msgKey, String default_error_msg) {
        Object action = invocation.getAction();
        if (action instanceof TextProvider) {
            return ((TextProvider) action).getText(msgKey, default_error_msg);
        }
        return textProvider.getText(msgKey, default_error_msg);
    }
	
	@Inject
    public void setTextProvider(TextProvider textProvider) {
        this.textProvider = textProvider;
    }

}
