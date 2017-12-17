package com.huixiaoer.utils.cdn;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.context.support.WebApplicationObjectSupport;

public class WebContentGenerator extends WebApplicationObjectSupport {
	public static final String METHOD_GET = "GET";
	public static final String METHOD_HEAD = "HEAD";
	public static final String METHOD_POST = "POST";
	private static final String HEADER_PRAGMA = "Pragma";
	private static final String HEADER_EXPIRES = "Expires";
	private static final String HEADER_CACHE_CONTROL = "Cache-Control";
	private Set supportedMethods;
	private boolean requireSession;
	private boolean useExpiresHeader;
	private boolean useCacheControlHeader;
	private boolean useCacheControlNoStore;
	private boolean useCdnCache;
	private boolean useVarnishCache;
	private long cdnSeconds;
	private long varnishSeconds;
	protected int cacheSeconds;

	public WebContentGenerator() {
		this(true);
	}

	public WebContentGenerator(boolean restrictDefaultSupportedMethods) {
		this.requireSession = false;
		this.useExpiresHeader = true;
		this.useCacheControlHeader = true;
		this.useCacheControlNoStore = true;
		this.useCdnCache = true;
		this.useVarnishCache = true;
		this.cdnSeconds = -1L;
		this.varnishSeconds = -1L;
		this.cacheSeconds = -1;
		if (restrictDefaultSupportedMethods) {
			this.supportedMethods = new HashSet(4);
			this.supportedMethods.add("GET");
			this.supportedMethods.add("HEAD");
			this.supportedMethods.add("POST");
		}
	}

	@SuppressWarnings("unchecked")
	public WebContentGenerator(String[] supportedMethods) {
		this.requireSession = false;
		this.useExpiresHeader = true;
		this.useCacheControlHeader = true;
		this.useCacheControlNoStore = true;
		this.useCdnCache = false;
		this.useVarnishCache = false;
		this.cdnSeconds = -1L;
		this.varnishSeconds = -1L;
		this.cacheSeconds = -1;
		this.supportedMethods = new HashSet(Arrays.asList(supportedMethods));
	}

	public final void setSupportedMethods(String[] methods) {
		if (methods != null)
			this.supportedMethods = new HashSet(Arrays.asList(methods));
		else
			this.supportedMethods = null;
	}

	public final String[] getSupportedMethods() {
		return StringUtils.toStringArray(this.supportedMethods);
	}

	public final void setRequireSession(boolean requireSession) {
		this.requireSession = requireSession;
	}

	public final boolean isRequireSession() {
		return this.requireSession;
	}

	public final void setUseExpiresHeader(boolean useExpiresHeader) {
		this.useExpiresHeader = useExpiresHeader;
	}

	public final boolean isUseExpiresHeader() {
		return this.useExpiresHeader;
	}

	public final void setUseCacheControlHeader(boolean useCacheControlHeader) {
		this.useCacheControlHeader = useCacheControlHeader;
	}

	public final boolean isUseCacheControlHeader() {
		return this.useCacheControlHeader;
	}

	public final void setUseCacheControlNoStore(boolean useCacheControlNoStore) {
		this.useCacheControlNoStore = useCacheControlNoStore;
	}

	public final boolean isUseCacheControlNoStore() {
		return this.useCacheControlNoStore;
	}

	public void setUseCdnCache(boolean userCdnCache) {
		this.useCdnCache = userCdnCache;
	}

	public void setUseVarnishCache(boolean userVarnishCache) {
		this.useVarnishCache = userVarnishCache;
	}

	public boolean isUseCdnCache() {
		return this.useCdnCache;
	}

	public boolean isUseVarnishCache() {
		return this.useVarnishCache;
	}

	public void setCdnSeconds(long cdnSeconds) {
		this.cdnSeconds = cdnSeconds;
	}

	public long getCdnSeconds() {
		return this.cdnSeconds;
	}

	public void setVarnishSeconds(long varnishSeconds) {
		this.varnishSeconds = varnishSeconds;
	}

	public long getVarnishSeconds() {
		return this.varnishSeconds;
	}

	public final void setCacheSeconds(int seconds) {
		this.cacheSeconds = seconds;
	}

	public final int getCacheSeconds() {
		return this.cacheSeconds;
	}

	protected final void checkAndPrepare(HttpServletRequest request, HttpServletResponse response, boolean lastModified)
			throws ServletException {
		checkAndPrepare(request, response, -1L, -1L, lastModified);
	}

	protected final void checkAndPrepare(HttpServletRequest request, HttpServletResponse response, long cdnCacheSeconds,
			long varnishCacheSeconds, boolean lastModified) throws ServletException {
		String method = request.getMethod();
		if ((this.supportedMethods != null) && (!this.supportedMethods.contains(method)))
			throw new HttpRequestMethodNotSupportedException(method, StringUtils.toStringArray(this.supportedMethods));
		if ((this.requireSession) && (request.getSession(false) == null)) {
			throw new HttpSessionRequiredException("Pre-existing session required but none found");
		}
		applyCacheSeconds(response, cdnCacheSeconds, varnishCacheSeconds, lastModified);
	}

	protected final void preventCaching(HttpServletResponse response) {
		response.setHeader("Pragma", "no-cache");
		if (this.useExpiresHeader)
			response.setDateHeader("Expires", 1L);
		if (this.useCacheControlHeader) {
			response.setHeader("Cache-Control", "no-cache");
			if (this.useCacheControlNoStore)
				response.addHeader("Cache-Control", "no-store");
			if (this.useCdnCache)
				response.addHeader("Cache-Control", "max-age=0");
			if (this.useVarnishCache)
				response.addHeader("Cache-Control", "s-maxage=0");
		}
	}

	protected final void cacheForSeconds(HttpServletResponse response, long seconds) {
		cacheForSeconds(response, seconds, false);
	}

	protected final void cacheForSeconds(HttpServletResponse response, long seconds, boolean mustRevalidate) {
		if (this.useExpiresHeader)
			response.setDateHeader("Expires", System.currentTimeMillis() + seconds * 1000L);
		long now = System.currentTimeMillis();
		response.setDateHeader("Last-Modified", now - now % seconds);
		if (this.useCacheControlHeader) {
			String headerValue = "";
			if (mustRevalidate) {
				headerValue = new StringBuilder().append(headerValue).append("must-revalidate").toString();
			}
			if (this.useCdnCache) {
				if (headerValue.length() > 0)
					headerValue = new StringBuilder().append(headerValue).append(", max-age=").append(getCdnSeconds())
							.toString();
				else {
					headerValue = new StringBuilder().append(headerValue).append(" max-age=").append(getCdnSeconds())
							.toString();
				}
			}
			if (this.useVarnishCache) {
				if (headerValue.length() > 0)
					headerValue = new StringBuilder().append(headerValue).append(",s-maxage=")
							.append(getVarnishSeconds()).toString();
				else {
					headerValue = new StringBuilder().append(headerValue).append(" s-maxage=")
							.append(getVarnishSeconds()).toString();
				}
			}
			response.setHeader("Cache-Control", headerValue);
		}
	}

	protected final void applyCacheSeconds(HttpServletResponse response, long cdnSeconds, long varnishSeconds,
			boolean mustRevalidate) {
		long cacheSeconds = 0L;
		long nowMillis = System.currentTimeMillis();
		if ((varnishSeconds != 0L) && (cdnSeconds != 0L) && (varnishSeconds >= cdnSeconds)) {
			cacheSeconds = cdnSeconds;
		}
		if ((varnishSeconds != 0L) && (cdnSeconds != 0L) && (cdnSeconds > varnishSeconds)) {
			cacheSeconds = varnishSeconds;
		}
		if ((varnishSeconds != 0L) && (cdnSeconds == 0L)) {
			cacheSeconds = varnishSeconds;
		}
		if ((cdnSeconds != 0L) && (varnishSeconds == 0L)) {
			cacheSeconds = cdnSeconds;
		}
		response.setDateHeader("Expires", nowMillis + cacheSeconds * 1000L);
		if (cacheSeconds > 0L) {
			response.setDateHeader("Last-Modified", nowMillis - nowMillis % (cacheSeconds * 1000L));
		}
		StringBuilder cacheControl = new StringBuilder();
		Map cacheControlMap = new HashMap();
		if (cdnSeconds == 0L) {
			cacheControl.append("no-cache,no-store");
		}
		if (this.useCdnCache) {
			cacheControlMap.put("max-age", Long.valueOf(cdnSeconds));
		}
		if (this.useVarnishCache) {
			cacheControlMap.put("s-maxage", Long.valueOf(varnishSeconds));
		}
		if (cacheControlMap.size() > 0) {
			String tmp = cacheControlMap.toString();
			if (cacheControl.length() > 0) {
				cacheControl.append(",");
			}
			cacheControl.append(tmp.substring(1, tmp.length() - 1));
		}
		response.setHeader("Cache-Control", cacheControl.toString());

		response.setHeader("Pragma", "Pragma");
	}

//	public static void main(String[] args) {
//		Map map = new HashMap();
//		map.put("max-age", Integer.valueOf(0));
//		map.put("s-maxage", Integer.valueOf(0));
//		String tmp = map.toString();
//		System.out.println(tmp.substring(1, tmp.length() - 1));
//	}
}