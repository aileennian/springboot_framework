package com.huixiaoer.utils.cdn;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.LastModified;
import org.springframework.web.util.UrlPathHelper;

public class WebContentInterceptor extends WebContentGenerator implements HandlerInterceptor {
	private UrlPathHelper urlPathHelper;
	private Map<String,String> cacheMappings; //modify by nianxl from Map to Map<String,String>
	private PathMatcher pathMatcher;

	public WebContentInterceptor() {
		super(false);
		this.urlPathHelper = new UrlPathHelper();
		this.cacheMappings = new HashMap<String,String>();
		this.pathMatcher = new AntPathMatcher();
	}

	public void setAlwaysUseFullPath(boolean alwaysUseFullPath) {
		this.urlPathHelper.setAlwaysUseFullPath(alwaysUseFullPath);
	}

	public void setUrlDecode(boolean urlDecode) {
		this.urlPathHelper.setUrlDecode(urlDecode);
	}

	public void setUrlPathHelper(UrlPathHelper urlPathHelper) {
		Assert.notNull(urlPathHelper, "UrlPathHelper must not be null");
		this.urlPathHelper = urlPathHelper;
	}

	public void setCacheMappings(Properties cacheMappings) {
		this.cacheMappings.clear();
		String path;
		for (Enumeration<?> propNames = cacheMappings.propertyNames(); propNames.hasMoreElements(); this.cacheMappings
				.put(path, cacheMappings.getProperty(path)))
			path = (String) propNames.nextElement();
	}

	public void setPathMatcher(PathMatcher pathMatcher) {
		Assert.notNull(pathMatcher, "PathMatcher must not be null");
		this.pathMatcher = pathMatcher;
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws ServletException {
		String lookupPath = this.urlPathHelper.getLookupPathForRequest(request);
		if (this.logger.isDebugEnabled())
			this.logger.debug("Looking up cache seconds for [" + lookupPath + "]");
		String cacheSecondStr = lookupCacheSeconds(lookupPath);
		if (cacheSecondStr != null) {
			if (this.logger.isDebugEnabled())
				this.logger.debug("Applying " + cacheSecondStr + " cache seconds to [" + lookupPath + "]");
			String[] cacheSecondArry = StringUtils.split(cacheSecondStr, ",");
			long cdnSeconds = Long.parseLong(cacheSecondArry[0]);
			long varnishSeconds = Long.parseLong(cacheSecondArry[1]);
			checkAndPrepare(request, response, cdnSeconds, varnishSeconds, handler instanceof LastModified);
		} else {
			if (this.logger.isDebugEnabled())
				this.logger.debug("Applying default cache seconds to [" + lookupPath + "]");
			checkAndPrepare(request, response, handler instanceof LastModified);
		}
		return true;
	}

	protected String lookupCacheSeconds(String urlPath) {
		String cacheSeconds = (String) this.cacheMappings.get(urlPath);
		if (cacheSeconds == null) {
			cacheSeconds = "0,0";
			Iterator<?> i$ = this.cacheMappings.keySet().iterator();

			while (i$.hasNext()) {
				String registeredPath = (String) i$.next();
				if (this.pathMatcher.match(registeredPath, urlPath))
					cacheSeconds = (String) this.cacheMappings.get(registeredPath);
			}
		}
		return cacheSeconds;
	}

	public void postHandle(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse, Object obj,
			ModelAndView modelandview) throws Exception {
	}

	public void afterCompletion(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse,
			Object obj, Exception exception) throws Exception {
	}
}
