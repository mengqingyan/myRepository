/**
 * 
 */
package com.revencoft.annotation_auth.smarthandler;

import java.lang.annotation.Annotation;

import org.junit.Assert;
import org.junit.Test;

import com.revencoft.validation.persistent.annotation.Min;
import com.revencoft.validation.persistent.annotation.SqlFilter;
import com.revencoft.validation.persistent.annotation.handler.smart.GenericValidationAnnotationHandlerAdapter;
import com.revencoft.validation.persistent.annotation.handler.smart.SmartValidateAnnotationHandler;

/**
 * @author mengqingyan
 * @version 
 */
public class SmartvalidationAnnotationHandlerTest {
	
	@Test
	public void testSmartSqlFilterAnnotationHandler() {
		SmartSqlFilterAnnotationHandler sqlHandler = new SmartSqlFilterAnnotationHandler();
		
		SmartValidateAnnotationHandler adapter = new GenericValidationAnnotationHandlerAdapter(sqlHandler);
		
		Assert.assertTrue(adapter.supports(SqlFilter.class));
		Assert.assertFalse(adapter.supports(Annotation.class));
		Assert.assertFalse(adapter.supports(Min.class));
	
	}

}
