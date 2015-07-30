/**
 * 
 */
package com.revencoft.validation.cache;

import java.util.List;

/**
 * @author mengqingyan
 * @version 
 * @param <T>
 */
public interface ListCache<K, T> extends Cache<K, List<T>>, CachedType<T> {

	void putList(K key, List<T> list);
}
