package com.huixiaoer.ehcache.listener;

import java.util.Properties;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.event.CacheManagerEventListener;
import net.sf.ehcache.event.CacheManagerEventListenerFactory;

public class EhCacheManagerEventListenerFactory extends
		CacheManagerEventListenerFactory {

	@Override
	public CacheManagerEventListener createCacheManagerEventListener(
			CacheManager cacheManager, Properties properties) {
		return new EhCacheManagerEventListener(cacheManager);
	}
	
	
	

}
