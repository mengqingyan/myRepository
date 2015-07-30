/**
 * 
 */
package com.revencoft.validation.cache;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author mengqingyan
 * @version 2.0
 */
public class SimpleCache<K, V> implements Cache<K, V> {
	
	private ConcurrentMap<K, V> map;
	
	/**
	 * 
	 */
	public SimpleCache() {
		super();
		map = new ConcurrentHashMap<K, V>();
	}
	public SimpleCache(int initialCapacity, float loadFactor) {
		super();
		map = new ConcurrentHashMap<K, V>(initialCapacity, loadFactor);
	}

	@Override
	public boolean contains(K key) {
		return map.containsKey(key);
	}

	@Override
	public V put(K key, V value) {
		return map.put(key, value);
	}

	@Override
	public V get(K key) {
		return map.get(key);
	}
	@Override
	public String toString() {
		return String.format("SimpleCache [map=%s]", map);
	}
	
	@Override
	public boolean isEmpty() {
		
		return map.isEmpty();
	}
	
	@Override
	public V remove(K key) {
		return map.remove(key);
	}

	@Override
	public void clear() {
		map.clear();
	}
	
	@Override
	public Set<K> keySet() {
		return map.keySet();
	}
	
	@Override
	public int size() {
		return map.size();
	}
	
	
}
