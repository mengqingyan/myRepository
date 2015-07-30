/**
 * 
 */
package com.revencoft.annotation_auth.reflect;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.GenericTypeResolver;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;

/**
 * @author mengqingyan
 * @version 
 */
public class GenericTypeArgumentTest {

	@Test
	public void testGenericType() {
		
		ScheduledAnnotationBeanPostProcessor p = new ScheduledAnnotationBeanPostProcessor();
		Class<?> typeArg = GenericTypeResolver.resolveTypeArgument(p.getClass(), ApplicationListener.class);
		Assert.assertTrue(typeArg == ContextRefreshedEvent.class);
	}
}
