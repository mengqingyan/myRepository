/**
 * 
 */
package com.revencoft.validation.cache.validationsupport;

import java.lang.annotation.Annotation;
import java.util.List;

import com.revencoft.validation.core.AnnotationsHolder;
import com.revencoft.validation.persistent.annotation.handler.ValidateAnnotationHandler;
import com.revencoft.validation.persistent.annotation.support.FieldAndAnnotationsHolder;

/**
 * @author mengqingyan
 * @version 2.0
 */
public interface ValidationCache {
	
	String CACHE_PREFIX = "validation_cache_";
	
	String FIELD_ANNOTATION = CACHE_PREFIX + "field_annotation";
	
	String ANNOTATION_HANDLERS = CACHE_PREFIX + "annotation_handler";
	
	String SUPPORT_ANNOTATION_HANDLER = CACHE_PREFIX + "support_annotation_handler";
	
	String OBJECT_ANNO_NAMES = CACHE_PREFIX + "obj_anno_names";
	
	String CUSTOM_HANDLERS = CACHE_PREFIX + "custom_handlers";
	
	String CUSTOM_OBJECT_ANNO_NAMES = CACHE_PREFIX + "custom_object_anno_names";

	/**
	 * 将targetClazz类中的field封装成FieldAndAnnotationsHolder集合
	 * @param targetClazz
	 * @param globleHolder 表示该Class，在父对象中对应的field实例上标注的注解集合
	 * @return targetClazz类中的field-annotations集合，若无则返回empty list
	 * @throws Exception
	 */
	List<FieldAndAnnotationsHolder> getAnnotatedFieldsFromCache(Class<? extends Object> targetClazz, AnnotationsHolder globleHolder) throws Exception;

	/**
	 * 根据annotation获取相应的处理器
	 * @param annotation
	 * @return
	 */
	List<ValidateAnnotationHandler> getSupportedAnnotationHandlerFromCache(Annotation annotation);
	
	/**
	 * 从缓存中获取所有注解处理器
	 * @return
	 */
	List<ValidateAnnotationHandler> getAnnotationHandlers();
	
	/**
	 * 将所有注解处理器放入缓存
	 * @param handlers
	 */
	void setAnnotationHandlers(List<ValidateAnnotationHandler> handlers);
	
	
	List<String> getObjectAnnotationNames();
	
	/**
	 * 设置标注对象的注解的名称
	 * @param objectAnnotationNames
	 */
	void setObjectAnnotationNames(List<String> objectAnnotationNames);
	
	public void setCustomHandlers(List<ValidateAnnotationHandler> customHandlers);

	public void setCustomObjectAnnotationNames(List<String> customObjectAnnotationNames);

	/**
	 * 应用自定义配置
	 */
	void refreshCustomConfig();

	/**
	 * 清除无效内存
	 */
	void refresh();
}
