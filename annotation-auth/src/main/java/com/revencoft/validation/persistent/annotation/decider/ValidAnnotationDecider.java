package com.revencoft.validation.persistent.annotation.decider;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.Assert;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptorUtil;
import com.opensymphony.xwork2.util.TextParseUtil;
import com.opensymphony.xwork2.util.ValueStack;
import com.revencoft.validation.exception.UnsupportAnnotationException;
import com.revencoft.validation.exception.FieldValidationException;
import com.revencoft.validation.exception.ValidationException;
import com.revencoft.validation.persistent.annotation.Valid;
import com.revencoft.validation.persistent.annotation.Valid.ExpressionType;

/**
 * @author mengqingyan
 * @version:
 * @since 1.0
 */

public class ValidAnnotationDecider implements AnnotationDecider {

	private static final Logger log = Logger.getLogger(ValidAnnotationDecider.class);
	private static final String SPEL_CONTEXT = "SPEL_CONTEXT_KEY";


	public Class<? extends Annotation> getDecidedAnnotationType() {
		return Valid.class;
	}

	public boolean decideToExec(ActionInvocation invocation,
			Annotation annotation, Object target) throws ValidationException {
		if(annotation == null) {
			//continue
			return true;
		}
		if(!annotation.annotationType().equals(getDecidedAnnotationType())) {
			throw new UnsupportAnnotationException("not support annotation[" + annotation + "]!");
		}
		
		Valid valid = (Valid) annotation;
		
		String method = invocation.getProxy().getMethod();
		String methods = valid.methods();
		
		if(StringUtils.isNotBlank(methods)) {
			boolean applyMethod = MethodFilterInterceptorUtil.applyMethod(Collections.<String> emptySet(),
					TextParseUtil.commaDelimitedStringToSet(methods), method);
			if(!applyMethod) {
				return false;
			}
		}
        
		String[] conditions = valid.conditions();
		
		for (String condition : conditions) {
			boolean passed = parse(invocation, condition, valid.parseType(), target);
			if(!passed) {
				return false;
			}
		}
		
		return true; 
	}

	/**
	 * 解析condition
	 * @param invocation
	 * @param condition
	 * @param parseType
	 * @param target
	 * @return
	 */
	private boolean parse(ActionInvocation invocation, String condition, ExpressionType parseType, Object target) {
		switch (parseType) {
		case OGNL:
			return isApplyByOGNL(invocation.getStack(), condition, target);
		case SPEL:
			
			return isApplyBySPEL(invocation, condition, target);

		default:
			break;
		}
		return false;
	}
	
	/**
	 * 通过解析spring的el表达式，判断是否进行校验
	 * @param invocation
	 * @param condition
	 * @param target
	 * @return
	 */
	protected boolean isApplyBySPEL(ActionInvocation invocation, String condition, Object target) {
		
		Assert.notNull(condition, "必须指定condition的值！");
		
		ExpressionParser parser = new SpelExpressionParser();
		Expression exp = parser.parseExpression(condition);
		HttpServletRequest request = ServletActionContext.getRequest();
		EvaluationContext context = (EvaluationContext) request.getAttribute(SPEL_CONTEXT);
		if(context == null) {
			context = new StandardEvaluationContext(target);
			Map<String, Object> contextMap = invocation.getStack().getContext();
			Set<Entry<String, Object>> entrySet = contextMap.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				context.setVariable(entry.getKey(), entry.getValue());
			}
			request.setAttribute(SPEL_CONTEXT, context);
		}
		
		return exp.getValue(context, boolean.class);
	}
	
	/**
	 * 通过解析OGNL表达式，判断是否进行校验
	 * @param object
	 * @return
	 * boolean
	 */
	protected boolean isApplyByOGNL(ValueStack stack, String condition, Object object) {

		Assert.notNull(condition, "必须指定condition的值！");
		
		Boolean answer = Boolean.FALSE;
		Object obj = null;

		try {
			obj = getFieldValue(stack, condition, object);
		} catch (FieldValidationException e) {
			log.error("获取值栈数据出错：", e);
		} catch (Exception e) {
		}
		if ((obj != null) && (obj instanceof Boolean)) {
			answer = (Boolean) obj;
		} else {
			log.warn(obj + " 应该是布尔类型！");
		}

		return answer.booleanValue();
	}


	 protected Object getFieldValue(ValueStack stack, String name, Object object) throws FieldValidationException {

	        boolean pop = false;

			if (!stack.getRoot().contains(object)) {
	            stack.push(object);
	            pop = true;
	        }

	        Object retVal = stack.findValue(name);

	        if (pop) {
	            stack.pop();
	        }

	        return retVal;
	    }

	
}
