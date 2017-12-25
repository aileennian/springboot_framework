package com.nian.xxx.auth.config.mybatis;


import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nian.mybatis.BaseMybatisConfigure;

import tk.mybatis.spring.mapper.MapperScannerConfigurer;

//@Configuration
//TODO 注意，由于MapperScannerConfigurer执行的比较早，所以必须有下面的注解
//@AutoConfigureAfter(BaseMybatisConfigure.class)
public class MyBatisMapperScannerConfig {
//	
//	@Bean(name = "mapperScannerConfigurer")
//	public MapperScannerConfigurer mapperScannerConfigurer() {
//		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
//		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
//	     //指定mapper接口的路径 ,批量加上@Mapper ,也可以手动加上 ,省略此配置
//        //会触发 No MyBatis mapper was found in '[anddd7.springboot.controller, anddd7.springboot]' package.  警告
// 	     mapperScannerConfigurer.setBasePackage("com.huixiaoer.xxx.auth.mapper");	
//		return mapperScannerConfigurer;
//	}
}
