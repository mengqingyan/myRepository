/**
 * 
 */
package com.revencoft.validation.persistent.validator;

/**
 * @author mengqingyan
 * @version 
 */
public interface CacheablePropertyValidator extends PropertyValidator{

	/**
	 * 清除缓存
	 */
	void refresh();
	/**
	 * 用于验证器的扩展,加载自定义验证器等
	 */
	void refreshCustomConfig();
	
}
