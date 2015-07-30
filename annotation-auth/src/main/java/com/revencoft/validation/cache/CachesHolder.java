/**
 * 
 */
package com.revencoft.validation.cache;

import java.util.List;
import java.util.Set;

import org.springframework.util.Assert;

import com.revencoft.validation.exception.CachedTypeConflictException;



/**
 * @author mengqingyan
 * @version 
 * @param <E> key
 */
public class CachesHolder<E> {

	private Cache<E, Cache<?, ?>> caches;
	
	private static final String tip = "cached type[%s] conflicts with the type[%s] you want!";
	
	private static final CachesHolder<Object> cachesHolder = new CachesHolder<Object>();
	
	private CachesHolder() {
		this.caches = new SimpleCache<E, Cache<?,?>>(3, 1);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> CachesHolder<T> getInstance() {
		return (CachesHolder<T>) cachesHolder;
	}
	
	/**
	 * 
	 * @param <K>
	 * @param <V, K>
	 * @param cacheName cache名称
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public <K, V> Cache<K, V> getNamedCache(E cacheName) {
		if(caches.contains(cacheName)) {
			Cache<?, ?> cache = caches.get(cacheName);
			return (Cache<K, V>) cache;
		}
		
		Cache<K, V> newCache = createCache();
		this.caches.put(cacheName, newCache);
		
		return newCache;
	}
	
	/**
	 * 获取ListCache
	 * @param <K>
	 * @param <K>
	 * @param cacheName cache名称
	 * @param basicType ListCache缓存的basic类型
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <K, T> ListCache<K, T> getNamedListCache(E cacheName, Class<T> basicType) {
		Assert.notNull(basicType, "basicType cannot be null!");
		Assert.notNull(cacheName, "cacheName cannot be null!");
		if(caches.contains(cacheName)) {
			Cache<?, ?> cache = caches.get(cacheName);
			if(cache instanceof ListCache) {
				ListCache listCache = (ListCache) cache;
				Class<?> existsType = listCache.getBasicType();
				if(existsType != basicType) {
					throw new CachedTypeConflictException(String.format(tip, existsType, basicType));
				}
				return (ListCache<K, T>) cache;
			} else {
				throw new CachedTypeConflictException(String.format(tip, cache, "ListCache"));
			}
		}
		
		ListCache<K, T> lc = createListCache(basicType);
		this.caches.put(cacheName, lc);
		return lc;
		
	}
	
	protected <K, T> ListCache<K, T> createListCache(Class<T> basicType) {
		ListCache<K, T> c =  new ListCacheImpl<K, T>(new SimpleCache<K, List<T>>());
		c.setBasicType(basicType);
		return c;
	}
	
	private <K, V> Cache<K, V> createCache() {
		return new SimpleCache<K, V>();
	}

	/**
	 * @param cachePrefix
	 */
	public void clearCache(E cachePrefix) {
		Set<E> keySet = caches.keySet();
		if(keySet == null || cachePrefix == null) {
			return;
		}
		for (E e : keySet) {
			if(cachePrefix instanceof String) {
				String eStr = (String) e;
				if(eStr.startsWith(cachePrefix.toString())) {
					caches.remove(e);
				}
			} else {
				if(e.equals(cachePrefix)) {
					caches.remove(e);
				}
			}
		}
	}
}
