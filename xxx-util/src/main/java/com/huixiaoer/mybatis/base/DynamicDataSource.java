package com.huixiaoer.mybatis.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.huixiaoer.mybatis.multi.DynamicDataSourceType;

/**
 * com.huixiaoer.mybatis.base.DataSource
 * @author admin
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DynamicDataSource {
	 public DynamicDataSourceType value() default DynamicDataSourceType.write;
}
