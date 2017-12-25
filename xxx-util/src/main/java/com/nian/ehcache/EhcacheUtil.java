package com.nian.ehcache;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.search.Attribute;
import net.sf.ehcache.search.Query;
import net.sf.ehcache.search.Result;
import net.sf.ehcache.search.Results;

/**
 * 
 * spring 集成 ehcache
 * 
 * @author nianxiaoling[14110310]
 * @date 2015年8月19日 下午8:40:58
 */
public class EhcacheUtil {
	protected static Logger logger = LogManager.getLogger(EhcacheUtil.class);

	private CacheManager manager = null;
	private static EhcacheUtil ehCache;

	private EhcacheUtil() {
		URL url = EhcacheUtil.class.getClassLoader().getResource(
				"config/ehcache.xml");
		manager = CacheManager.create(url);
	}

	public static EhcacheUtil getInstance() {
		if (ehCache != null)
			return ehCache;
		synchronized (EhcacheUtil.class) {
			if (ehCache == null) {
				ehCache = new EhcacheUtil();
			}
			return ehCache;
		}
	}

	public static List<Element> searchKey(String cacheName, String keyPattern) {
		Cache cache = getInstance().manager.getCache(cacheName);
		Attribute<String> name = cache.getSearchAttribute("key");

		Query query = cache.createQuery();
		query = query.addCriteria(name.ilike(keyPattern + "*"));
		query.includeKeys();
		Results results = query.execute();

		List<Result> resultList = results.all();

		List<Element> keys = new ArrayList<Element>();
		if (resultList.size() > 0) {
			for (Result r : resultList) {
				Element value = cache.get(r.getKey().toString());
				keys.add(value);
			}
		}
		return keys;

	}

	public static void put(String cacheName, String key, Object value) {
		try {

			Cache cache = getInstance().manager.getCache(cacheName);
			if (cache == null)
				return;
			Element element = new Element(key, value);
			cache.put((Element) element.clone());
		} catch (Exception e) {
			logger.error("ehcache操作失败" + e.getMessage());
		}
	}

	public static <T> T get(String cacheName, String key) {
		try {
			Cache cache = getInstance().manager.getCache(cacheName);
			if (cache == null)
				return null;
			Element value = cache.get(key);

			if (value == null)
				return null;
			Element valueClone = (Element) value.clone();
			return (T) valueClone.getObjectValue();
		} catch (Exception e) {
			logger.error("ehcache操作失败" + e.getMessage());
			return null;
		}
	}

	public static Cache get(String cacheName) {
		try {
			return getInstance().manager.getCache(cacheName);
		} catch (Exception e) {
			logger.error("ehcache操作失败=" + e.getMessage());
			return null;
		}
	}

	public static void remove(String cacheName, String key) {
		try {
			Cache cache = getInstance().manager.getCache(cacheName);

			if (cache == null)
				return;
			cache.remove(key);
		} catch (Exception e) {
			logger.error("ehcache操作失败=" + e.getMessage());
		}
	}

	public static void put(CacheElement cacheElement, Object value) {
		put(cacheElement.getCache(), cacheElement.getElement(), value);
	}

	public static <T> T get(CacheElement cacheElement) {
		return get(cacheElement.getCache(), cacheElement.getElement());
	}

	public static void remove(CacheElement cacheElement) {
		remove(cacheElement.getCache(), cacheElement.getElement());
	}

	
	


}
