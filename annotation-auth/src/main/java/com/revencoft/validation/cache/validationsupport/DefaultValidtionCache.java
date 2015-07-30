/**
 * 
 */
package com.revencoft.validation.cache.validationsupport;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.revencoft.validation.cache.CachesHolder;
import com.revencoft.validation.cache.ListCache;
import com.revencoft.validation.core.AnnotationsHolder;
import com.revencoft.validation.persistent.annotation.handler.ValidateAnnotationHandler;
import com.revencoft.validation.persistent.annotation.support.FieldAndAnnotationsEntity;
import com.revencoft.validation.persistent.annotation.support.FieldAndAnnotationsHolder;
import com.revencoft.validation.utils.ValidateUtil;

/**
 * @author mengqingyan
 * @version 2.0
 */
public class DefaultValidtionCache implements ValidationCache {
	
	private CachesHolder<String> caches = CachesHolder.getInstance();
	
	
	@Override
	public List<FieldAndAnnotationsHolder> getAnnotatedFieldsFromCache(
			Class<? extends Object> targetClazz,
			AnnotationsHolder globleHolder) throws Exception {
		
		//key:targetClass name; value:
		ListCache<String, FieldAndAnnotationsHolder> cache = caches.getNamedListCache(FIELD_ANNOTATION, FieldAndAnnotationsHolder.class);
		
		String clazzName = targetClazz.getName();
		
		if(!cache.contains(clazzName)) {
			//细粒度的锁可以防止代码重复执行，提高程序执行效率；
			//不影响并发
			synchronized (clazzName) {
				if(!cache.contains(clazzName)) {
					List<Annotation> clazzAnnotations = new ArrayList<Annotation>();
					if(!ValidateUtil.isBaseDataType(targetClazz) && globleHolder != null){
						List<Annotation> annotations = globleHolder.getAnnotations();
						List<String> include = getObjectAnnotationNames();
						for (Annotation annotation : annotations) {
							if(include.contains(annotation.annotationType().getName())) {
								clazzAnnotations.add(annotation);
							}
						}
					}
					
					List<FieldAndAnnotationsHolder> fieldList = new ArrayList<FieldAndAnnotationsHolder>();
					cache.put(clazzName, fieldList);
					
					Field[] fields = targetClazz.getDeclaredFields();
					
					for(Field field : fields) {
						List<Annotation> annotations = new ArrayList<Annotation>();
						annotations.addAll(Arrays.asList(field.getAnnotations()));
						annotations.addAll(clazzAnnotations);
						if(!annotations.isEmpty()) {
							FieldAndAnnotationsHolder holder = new FieldAndAnnotationsEntity(field);
							holder.setAnnotations(annotations); 
							if(clazzAnnotations != null && !clazzAnnotations.isEmpty())
								holder.setObjectAnnotations(clazzAnnotations);
							fieldList.add(holder);
						}
					}
					return fieldList;
				}
			}
		}
		return cache.get(clazzName);
	}

	@Override
	public List<ValidateAnnotationHandler> getSupportedAnnotationHandlerFromCache(
			Annotation annotation) {
		ListCache<String, ValidateAnnotationHandler> cache = caches.getNamedListCache(SUPPORT_ANNOTATION_HANDLER, ValidateAnnotationHandler.class);
		String annotationType = annotation.annotationType().getName();
		if(!cache.contains(annotationType)) {
			//细粒度的锁可以防止代码重复执行，提高程序执行效率；
			//不影响并发
			synchronized (annotationType) {
				if(!cache.contains(annotationType)) {
					List<ValidateAnnotationHandler> handlersRes = new ArrayList<ValidateAnnotationHandler>();
					cache.put(annotationType, handlersRes);
					List<ValidateAnnotationHandler> handlers = getAnnotationHandlers();
					if(handlers  != null) {
						for(ValidateAnnotationHandler handler : handlers) {
							if(handler.supports(annotation)) {
								handlersRes.add(handler);
							}
						}
					}
					return handlersRes;
				}
			}
		}
		return cache.get(annotationType);
	}


	@Override
	public List<ValidateAnnotationHandler> getAnnotationHandlers() {
		ListCache<String, ValidateAnnotationHandler> cache = caches.getNamedListCache(ANNOTATION_HANDLERS, ValidateAnnotationHandler.class);
		return cache.get(ANNOTATION_HANDLERS);
	}

	@Override
	public void setAnnotationHandlers(List<ValidateAnnotationHandler> handlers) {
		ListCache<String, ValidateAnnotationHandler> cache = caches.getNamedListCache(ANNOTATION_HANDLERS, ValidateAnnotationHandler.class);
		cache.put(ANNOTATION_HANDLERS,handlers); 
	}

	@Override
	public List<String> getObjectAnnotationNames() {
		ListCache<String, String> cache = caches.getNamedListCache(OBJECT_ANNO_NAMES, String.class);
		return cache.get(OBJECT_ANNO_NAMES);
	}

	@Override
	public void setObjectAnnotationNames(List<String> objectAnnotationNames) {
		ListCache<String, String> cache = caches.getNamedListCache(OBJECT_ANNO_NAMES, String.class);
		cache.put(OBJECT_ANNO_NAMES, objectAnnotationNames);
	}

	@Override
	public void setCustomHandlers(List<ValidateAnnotationHandler> customHandlers) {
		ListCache<String, ValidateAnnotationHandler> cache = caches.getNamedListCache(CUSTOM_HANDLERS, ValidateAnnotationHandler.class);
		cache.put(CUSTOM_HANDLERS, customHandlers);
	}

	@Override
	public void setCustomObjectAnnotationNames(List<String> customObjectAnnotationNames) {
		ListCache<String, String> cache = caches.getNamedListCache(CUSTOM_OBJECT_ANNO_NAMES, String.class);
		cache.put(CUSTOM_OBJECT_ANNO_NAMES, customObjectAnnotationNames);
	}

	@Override
	public void refreshCustomConfig() {
		ListCache<String, String> cusAnnoNameCache = caches.getNamedListCache(CUSTOM_OBJECT_ANNO_NAMES, String.class);
		ListCache<String, ValidateAnnotationHandler> cusHandlerCache = caches.getNamedListCache(CUSTOM_HANDLERS, ValidateAnnotationHandler.class);
		
		if(!cusAnnoNameCache.isEmpty()) {
			ListCache<String, String> annoNameCache = caches.getNamedListCache(OBJECT_ANNO_NAMES, String.class);
			annoNameCache.putList(OBJECT_ANNO_NAMES, cusAnnoNameCache.get(CUSTOM_OBJECT_ANNO_NAMES));
		}
		
		if(!cusHandlerCache.isEmpty()) {
			ListCache<String, ValidateAnnotationHandler> handlerCache = caches.getNamedListCache(ANNOTATION_HANDLERS, ValidateAnnotationHandler.class);
			handlerCache.putList(ANNOTATION_HANDLERS, cusHandlerCache.get(CUSTOM_HANDLERS));
		}
	}

	@Override
	public void refresh() {
		caches.clearCache(CACHE_PREFIX);
	}

}
