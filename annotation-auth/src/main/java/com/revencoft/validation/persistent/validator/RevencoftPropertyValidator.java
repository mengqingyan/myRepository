/**
 * 
 */
package com.revencoft.validation.persistent.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.opensymphony.xwork2.ActionInvocation;
import com.revencoft.validation.core.AnnotationsHolder;
import com.revencoft.validation.exception.FieldValidationException;
import com.revencoft.validation.persistent.annotation.NotNull;
import com.revencoft.validation.persistent.annotation.handler.ValidateAnnotationHandler;
import com.revencoft.validation.persistent.annotation.support.FieldAndAnnotationsHolder;
import com.revencoft.validation.persistent.annotation.support.FieldValueEntity;
import com.revencoft.validation.persistent.annotation.support.FieldValueHolder;
import com.revencoft.validation.utils.ValidateUtil;

/**
 * @author mengqingyan
 * @version
 */
public class RevencoftPropertyValidator extends
		AbstractCacheableConditionalPropertyValidator implements InitializingBean {

	@Override
	protected void doValidate(Object target, AnnotationsHolder holder,
			ActionInvocation invocation) throws Exception {
		if (target == null) {
			if (holder == null) {
				throw new FieldValidationException("the holder can't be null!");
			}
			boolean needNotNull = holder.contains(NotNull.class) != null;
			if (needNotNull) {
				throw new FieldValidationException(
						"the validated object can't be null!");
			} else {
				return;
			}
		}

		List<FieldAndAnnotationsHolder> subHolders = cache
				.getAnnotatedFieldsFromCache(target.getClass(), holder);

		for (FieldAndAnnotationsHolder subHolder : subHolders) {

			Annotation validAnno = subHolder.contains(validDecider
					.getDecidedAnnotationType());

			boolean isOnlyForObjAnno = false;
			boolean continueValidate = true;
			if (validAnno != null) {
				continueValidate = validDecider.decideToExec(invocation,
						validAnno, target);
				if (!continueValidate) {
					continue;
				}
			} else if (!subHolder.isBaseDataType()) {
				continue;
			}
//			if (!continueValidate) {
//				boolean containsObjAnno = subHolder.containsObjAnnotation();
//				if (!containsObjAnno)
//					continue;
//				else
//					isOnlyForObjAnno = true;
//			}

			if (subHolder.isBaseDataType()) {
				validateForBaseDataType(target, subHolder, isOnlyForObjAnno);
			} else {
				Object value = subHolder.getFieldValue(target);
				doValidate(value, subHolder, invocation);
			}

		}

	}

	/**
	 * @param target
	 * @param subHolder
	 * @param isOnlyForObjAnno
	 * @throws Exception 
	 */
	private void validateForBaseDataType(Object target,
			FieldAndAnnotationsHolder holder, boolean isOnlyForObjAnno) throws Exception {
		
		Field field = holder.getField();
		Object value = ValidateUtil.readFieldValue(target, field);
		FieldValueHolder fValueHolder = new FieldValueEntity(field, value, target);
		
		List<Annotation> annotations = null;
		if(isOnlyForObjAnno) {
			annotations = holder.getObjectAnnotations();
		} else {
			annotations = holder.getAnnotations();
		}
		if(annotations == null) {
			return;
		}
		for (Annotation annotation : annotations) {
			List<ValidateAnnotationHandler> handlers = cache.getSupportedAnnotationHandlerFromCache(annotation);
			for (ValidateAnnotationHandler handler : handlers) {
				handler.validate(fValueHolder, annotation);
			}
		}
	}

	public void setHandlers(List<ValidateAnnotationHandler> handlers) {
		cache.setAnnotationHandlers(handlers);
	}
	
	public void setObjectAnnotationNames(List<String> objectAnnotationNames) {
		cache.setObjectAnnotationNames(objectAnnotationNames);
	}

	public void setCustomHandlers(List<ValidateAnnotationHandler> customHandlers) {
		cache.setCustomHandlers(customHandlers);
	}

	public void setCustomObjectAnnotationNames(
			List<String> customObjectAnnotationNames) {
		cache.setCustomObjectAnnotationNames(customObjectAnnotationNames);
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(validDecider, "validDecider can't be null!");
		cache.refreshCustomConfig();
	}
	
}
