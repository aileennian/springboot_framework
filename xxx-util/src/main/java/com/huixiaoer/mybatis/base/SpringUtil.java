package com.huixiaoer.mybatis.base;

import java.util.Map;

import org.springframework.context.ApplicationContext;




public class SpringUtil {
	private static ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext context) {
		if (applicationContext==null){
			applicationContext = context;
		}
	}
	
	public static void setContext(ApplicationContext context) {
		applicationContext = context;
	}


	// 取得存储在静态变量中的ApplicationContext.
	public static ApplicationContext getApplicationContext() {
		checkApplicationContext();
		return applicationContext;
	}

	// 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		checkApplicationContext();
		return (T) applicationContext.getBean(name);
	}

	// 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	// 如果有多个Bean符合Class, 取出第一个.
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clazz) {
		checkApplicationContext();
		@SuppressWarnings("rawtypes")
		Map beanMaps = applicationContext.getBeansOfType(clazz);
		if (beanMaps != null && !beanMaps.isEmpty()) {
			return (T) beanMaps.values().iterator().next();
		} else {
			return null;
		}
	}

	private static void checkApplicationContext() {
		if (applicationContext == null) {
			throw new IllegalStateException("applicaitonContext未注入，请在Application中增加语句");
		}
	}

}
