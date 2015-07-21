/**
 * 
 */
package com.revencoft.hystrix_webservice.service.hystrix.command;

import java.lang.reflect.Field;
import java.util.Properties;

import org.springframework.util.ReflectionUtils;

/**
 * @author mengqingyan
 * @version 
 */
public class CommandProperty {
	
	private static final String PROP_PREFIX = "hystrix."; 
	
	public String groupKey;
	public String commandKey;
	/**
	 * ms
	 */
	public int protectionTimeWhenShortCircuit;
	public int failPercentageForShortCircuit;
	public int rollingWindowInMilliseconds;
	public int rollingWindowBuckets;
	public int thresholdForShortCircuit;
	public int executionTimeoutInMilliseconds;
	public int threadPoolCoreSize;
	public int threadPoolQueneSize;
	public int executionIsolationSemaphoreMaxConcurrentRequests;
	
	public CommandProperty(Properties properties) throws Exception {
		parseProperties(properties);
	}
	
	
	private void parseProperties(Properties properties) throws Exception {
		Class<? extends CommandProperty> clazz = this.getClass();
		Field[] fields = clazz.getFields();
		if(fields == null) {
			return;
		}
		
		for (Field field : fields) {
			String val = properties.getProperty(PROP_PREFIX + field.getName());
			if(val != null) {
				//not essential for public field
				ReflectionUtils.makeAccessible(field);
				if(int.class.equals(field.getType())) {
					ReflectionUtils.setField(field, this, Integer.valueOf(val));
				} else {
					ReflectionUtils.setField(field, this, val);
				}
			}
		}
	}
	
}
