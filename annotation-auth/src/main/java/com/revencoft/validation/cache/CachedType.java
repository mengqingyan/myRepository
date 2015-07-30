/**
 * 
 */
package com.revencoft.validation.cache;

import java.util.Collection;

/**
 * @author mengqingyan
 * @version 
 * @param <T>
 * @param <T>
 */
public interface CachedType<T> {

	boolean isCachedCollection();
	
	@SuppressWarnings("rawtypes")
	Class<? extends Collection> getCachedCollectionType();
	
	Class<T> getBasicType();
	
	void setBasicType(Class<T> basicType);
}
