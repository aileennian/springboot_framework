package com.nian.ehcache.listener;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Status;
import net.sf.ehcache.event.CacheManagerEventListener;

public class EhCacheManagerEventListener implements CacheManagerEventListener {
	protected Logger logger = LogManager.getLogger(this.getClass());
	
	
	private final CacheManager cacheManager;

	public EhCacheManagerEventListener(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	@Override
	public void init() throws CacheException {
		logger.info("init.....");
	}

	@Override
	public Status getStatus() {
		logger.info("getStatus.....");
		return null;
	}

	@Override
	public void dispose() throws CacheException {
		logger.info("dispose......");
	}

	@Override
	public void notifyCacheAdded(String cacheName) {
		logger.info("cacheAdded......." + cacheName);
	}

	@Override
	public void notifyCacheRemoved(String cacheName) {
		logger.info("cacheRemoved......" + cacheName);
	}

}
