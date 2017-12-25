package com.nian.mybatis.aop;

import java.lang.reflect.Method;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import com.nian.mybatis.base.DynamicDataSource;
import com.nian.mybatis.multi.DataSourceContextHolder;
import com.nian.mybatis.multi.DynamicDataSourceType;


//@Component
@Aspect
public class DataSourceSpringAspect {
	protected Logger logger = LogManager.getLogger(this.getClass());

	public DataSourceSpringAspect(){}

	@Pointcut("execution(* com..*.service.impl.*Service*.*(..))")
	public void setDatasourcePointCut(){}
	
	@Before(value="setDatasourcePointCut()")
	public void setDatasource(JoinPoint point) {
		Object target = point.getTarget();
		String methodName = point.getSignature().getName();
		//Class<?>[] clazz = target.getClass().getInterfaces();
		Class<?>[] parameterTypes = ((MethodSignature) point.getSignature()).getMethod().getParameterTypes();
		
		DynamicDataSourceType dataSourceType = null;
		try {
			//Method method = clazz[0].getMethod(methodName, parameterTypes);
			Method method = target.getClass().getMethod(methodName, parameterTypes);
			if (method != null && method.isAnnotationPresent(DynamicDataSource.class)) {
				DynamicDataSource data = method.getAnnotation(DynamicDataSource.class);
				if (data.value()==DynamicDataSourceType.write){
					dataSourceType = DynamicDataSourceType.writeForce;
					DataSourceContextHolder.putDataSourceType(dataSourceType);
				}
//				else{
//					dataSourceType = data.value();
//				}
			}
//			else{
//				dataSourceType = DynamicDataSourceType.write;
//			}
			
		} catch (Exception e) {
			logger.error(String.format("Choose DataSource error, method:%s, msg:%s", methodName, e.getMessage()));
		}
	}

	public void after(JoinPoint point) {
		DataSourceContextHolder.clear();
	}
}
