package com.nian.xxx.web.config;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

/**
 * 有用的类 加载ajax路径 页面都用的这个 不要删
 *
 * @author zhu
 */
public class RichFreeMarkerView extends FreeMarkerView {

	/** 项目根路径 **/
	// private static final String ANOTHER_CONTEXT_PATH = "rootPath";
	/** 项目上下文 **/
	// private static final String CONTEXT_PATH_SIGN = "contextPath";
	/** 得到servlet请求路径 **/
	public static final String CONTEXT_PATH_NAVIMENU_SIGN = "contextPathNavi";
	public static final String CLIENT_USER_AGENT_TYPE = "clientUserAgentType";

	/**
	 * 在model中增加部署路径base，方便处理部署路径问题。
	 */
	protected void exposeHelpers(Map<String, Object> model,
			HttpServletRequest request) throws Exception {
		
		//
		String naviMenu = "";
		if (request.getAttribute(CONTEXT_PATH_NAVIMENU_SIGN) != null) {
			naviMenu = request.getAttribute(CONTEXT_PATH_NAVIMENU_SIGN)
					.toString();
		} else {
			String servletPath = request.getServletPath().toLowerCase();
			if (servletPath.contains("mianfei")) {
				naviMenu = "mianfei";
			} else if (servletPath.contains("fuyou")) {
				naviMenu = "fuyou";
			} else {
				naviMenu = "";
			}
		}
		
		//判断客户端是否是Pad
//		String userAgent = request.getHeader("User-Agent");
//		Pattern pattern = Pattern.compile("^.*iPad.*$");
//		Matcher matcher = pattern.matcher(userAgent);
//		Pattern pattern1 = Pattern.compile("^.*SNEBUY-APP.*$",
//				Pattern.CASE_INSENSITIVE);
//		Matcher matcher1 = pattern1.matcher(userAgent);
//		if (matcher.matches()) {
//			if (matcher1.matches()) {
//				model.put(CLIENT_USER_AGENT_TYPE, "Pad");
//			}
//		}
		model.put(CONTEXT_PATH_NAVIMENU_SIGN, naviMenu);
	}

}
