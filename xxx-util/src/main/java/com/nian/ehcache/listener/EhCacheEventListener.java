package com.nian.ehcache.listener;



import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;


public class EhCacheEventListener implements CacheEventListener {
	
	protected Logger logger = LogManager.getLogger(this.getClass());
	
	
	@Override  
	   public void notifyElementRemoved(Ehcache cache, Element element)  
	         throws CacheException {  
	      logger.info("removed");  
	   }  
	   
	   @Override  
	   public void notifyElementPut(Ehcache cache, Element element)  
	         throws CacheException {  
	      logger.info("put ["+ element.getKey() +"] in cache["+ cache.getName() +"]");
	   }  
	   
	   @Override  
	   public void notifyElementUpdated(Ehcache cache, Element element)  
	         throws CacheException {  
		   logger.info("update ["+ element.getKey() +"] in cache["+ cache.getName() +"]");
	   }  
	   
	   @Override  
	   public void notifyElementExpired(Ehcache cache, Element element) {  
	      logger.info("expired ["+ element.getKey() +"] in cache["+ cache.getName() +"]");
	   }  
	   
	   @Override  
	   public void notifyElementEvicted(Ehcache cache, Element element) {  
		  logger.info("evicted ["+ element.getKey() +"] in cache["+ cache.getName() +"]");
	    
	   }  
	   
	   @Override  
	   public void notifyRemoveAll(Ehcache cache) {  	     
	      logger.info("removeAllv cache["+ cache.getName() +"]");
	   }  
	   
	   @Override  
	   public void dispose() {  
		   logger.info("dispose cache]");
	   }  
	    
	   public Object clone() throws CloneNotSupportedException {  
	      throw new CloneNotSupportedException();  
	   }  

}
