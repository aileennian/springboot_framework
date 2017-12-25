package com.nian.xxx.spider.config.db;

import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;

import com.nian.mybatis.base.DynamicDataSource;
import com.nian.mybatis.multi.DataSourceContextHolder;
import com.nian.mybatis.multi.DynamicDataSourceType;

//InvocationHandler mybatis

//public class MybatisMapperInterceptor implements MethodInterceptor {
//@Component
//@Aspect
public class MybatisMapperInterceptor implements MethodBeforeAdvice, AfterReturningAdvice, ThrowsAdvice {
	protected Logger logger = LogManager.getLogger(this.getClass());

	
	@Override
	public void before(Method method, Object[] arg1, Object arg2) throws Throwable {
		if (method.isAnnotationPresent(DynamicDataSource.class)) {
			DynamicDataSource datasource = method.getAnnotation(DynamicDataSource.class);
			DataSourceContextHolder.putDataSourceType(datasource.value());
		} else {
			DataSourceContextHolder.putDataSourceType(DynamicDataSourceType.write);
		}
	}

	@Override
	public void afterReturning(Object arg0, Method arg1, Object[] arg2, Object arg3) throws Throwable {
		DataSourceContextHolder.clear();
	}

	// 抛出Exception之后被调用
	public void afterThrowing(Method method, Object[] args, Object target, Exception ex) throws Throwable {
		DataSourceContextHolder.putDataSourceType(DynamicDataSourceType.write);
	}

}
