/**
 * 
 */
package com.revencoft.validation.persistent.validator;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.util.TextParseUtil;
import com.revencoft.validation.cache.validationsupport.DefaultValidtionCache;
import com.revencoft.validation.cache.validationsupport.ValidationCache;
import com.revencoft.validation.core.AnnotationsHolder;
import com.revencoft.validation.persistent.annotation.decider.AnnotationDecider;
import com.revencoft.validation.persistent.annotation.support.FieldAndAnnotationsHolder;

/**
 * @author mengqingyan
 * @version
 */
public abstract class AbstractCacheableConditionalPropertyValidator implements
		CacheablePropertyValidator {

	protected Set<String> includeValidationMethods = Collections.emptySet();
	protected AnnotationDecider validDecider;
	protected ValidationCache cache = new DefaultValidtionCache();

	@Override
	public void validate(ActionInvocation invocation) throws Exception {
		
		List<FieldAndAnnotationsHolder> toValidActionFields = getToValidActionFields(invocation);
		Object action = invocation.getAction();
		for (FieldAndAnnotationsHolder holder : toValidActionFields) {
			Object target = holder.getFieldValue(action);
			doValidate(target, holder, invocation);
		}
	}

	/**
	 * 
	 * @param target 目标验证对象
	 * @param holder 封装了该对象作为父对象的field，其上标注的注解集合
	 * @param invocation
	 * @throws Exception
	 */
	protected abstract void doValidate(Object target, AnnotationsHolder holder,
			ActionInvocation invocation) throws Exception;

	/**
	 * 遍历action的field，获取需要执行验证的field;若无需执行验证，返回empty list
	 * @param invocation
	 * @return
	 * @throws Exception 
	 */
	protected List<FieldAndAnnotationsHolder> getToValidActionFields(ActionInvocation invocation) throws Exception {
		
		String method = invocation.getProxy().getMethod();
		if (!includeValidationMethods.isEmpty()
				&& !includeValidationMethods.contains(method)) {
			return Collections.emptyList();
		}

		Object action = invocation.getAction();
		List<FieldAndAnnotationsHolder> holders = cache
				.getAnnotatedFieldsFromCache(action.getClass(), null);
		
		List<FieldAndAnnotationsHolder> toValidHolders = new ArrayList<FieldAndAnnotationsHolder>();
		boolean continueValidate = false;
		for (FieldAndAnnotationsHolder holder : holders) {
			Annotation validAnno = holder.contains(validDecider
					.getDecidedAnnotationType());

			if (validAnno != null) {
				continueValidate = validDecider.decideToExec(invocation,
						validAnno, action);
				if(continueValidate) {
					toValidHolders.add(holder);
				}
			}
		}
		return toValidHolders;

	}

	@Override
	public void refresh() {
		cache.refresh();
	}

	@Override
	public void refreshCustomConfig() {
		cache.refreshCustomConfig();
	}

	public void setIncludeValidationMethods(String includeValidationMethods) {
		this.includeValidationMethods = TextParseUtil
				.commaDelimitedStringToSet(includeValidationMethods);
	}

	public void setValidDecider(AnnotationDecider validDecider) {
		this.validDecider = validDecider;
	}

	public void setCache(ValidationCache cache) {
		this.cache = cache;
	}
}
