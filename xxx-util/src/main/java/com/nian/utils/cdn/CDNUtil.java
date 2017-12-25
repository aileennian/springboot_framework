package com.nian.utils.cdn;

import javax.servlet.http.HttpServletResponse;

public final class CDNUtil {
	public static void setCdnTime(long cdnSeconds, HttpServletResponse response) {
		long nowMillis = System.currentTimeMillis();
		response.setDateHeader("Expires", nowMillis + cdnSeconds * 1000L);
		response.setDateHeader("Last-Modified", nowMillis - nowMillis % (cdnSeconds * 1000L));
		StringBuilder cacheControl = new StringBuilder();
		if (cdnSeconds <= 0L)
			cacheControl.append("no-cache,no-store,max-age=0,s-maxage=0");
		else {
			cacheControl.append("max-age=").append(cdnSeconds);
		}
		response.setHeader("Cache-Control", cacheControl.toString());
		response.setHeader("Pragma", "Pragma");
	}
}