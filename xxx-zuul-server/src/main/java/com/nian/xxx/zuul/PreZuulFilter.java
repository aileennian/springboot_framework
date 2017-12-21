package com.nian.xxx.zuul;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

//@Component
public class PreZuulFilter extends ZuulFilter {
	protected Logger logger = LogManager.getLogger(this.getClass());

	
	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;// 数字越大越靠后
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		HttpServletRequest servletRequest = RequestContext.getCurrentContext().getRequest();
		String host = servletRequest.getRemoteHost();
		logger.info("request host : " + host);
		return null;
	}

}
