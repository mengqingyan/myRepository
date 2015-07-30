package com.revencoft.validation.cache;

import java.util.Collection;
import java.util.List;
import java.util.Set;


/**
 * @author mengqingyan
 * @version: 2.0
 * @desc: cache结构为String-List, String-List ...
 */

public class ListCacheImpl<K, T> implements ListCache<K, T>{

	private Cache<K, List<T>> cache;
	
	private Class<T> cachedType;
	/**
	 * @param cache
	 */
	public ListCacheImpl(Cache<K, List<T>> cache) {
		super();
		this.cache = cache;
	}

	@Override
	public boolean contains(K key) {
		return this.cache.contains(key);
	}

	@Override
	public List<T> put(K key, List<T> value) {
		return this.cache.put(key, value);
	}

	@Override
	public List<T> get(K key) {
		return this.cache.get(key);
	}
	@Override
	public boolean isCachedCollection() {
		return true;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class<? extends Collection> getCachedCollectionType() {
		return List.class;
	}
	
	@Override
	public Class<T> getBasicType() {
		return cachedType;
	}
	
	public void setCachedType(Class<T> cachedType) {
		this.cachedType = cachedType;
	}

	@Override
	public String toString() {
		return String.format("ListCache [cache=%s]", cache);
	}

	@Override
	public void setBasicType(Class<T> basicType) {
		this.cachedType = basicType;
	}

	@Override
	public void putList(K key, List<T> list) {
		if(list == null || list.isEmpty() || key == null) {
			return;
		}
		if(!contains(key)) {
			put(key, list);
			return;
		}
		
		List<T> lists = get(key);
		lists.addAll(list);
	}

	@Override
	public boolean isEmpty() {
		return cache.isEmpty();
	}

	@Override
	public List<T> remove(K key) {
		return cache.remove(key);
	}

	@Override
	public void clear() {
		cache.clear();
	}

	@Override
	public Set<K> keySet() {
		return cache.keySet();
	}

	@Override
	public int size() {
		return cache.size();
	}

}
