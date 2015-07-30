/**
 * 
 */
package com.revencoft.validation.cache;

import java.util.Set;

/**
 * @author mengqingyan
 * @version 
 */
public interface Cache<K, V> {
	
	boolean contains(K key);
	
	V put(K key, V value);
	
	V get(K key);
	
	V remove(K key);
	
	Set<K> keySet();
	
	void clear();
	
	boolean isEmpty();
	
	int size();
	
//	<T> CachedType<T> getCachedType();

}
