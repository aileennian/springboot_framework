package com.huixiaoer.xxx.spider.config.db;


import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.huixiaoer.mybatis.BaseMybatisConfigure;

import tk.mybatis.spring.mapper.MapperScannerConfigurer;

@Configuration
//TODO 注意，由于MapperScannerConfigurer执行的比较早，所以必须有下面的注解
@AutoConfigureAfter(BaseMybatisConfigure.class)
public class MyBatisMapperScannerConfig {
   	
	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
		mapperScannerConfigurer.setBasePackage("com.huixiaoer.xxx.auth.mapper");	
		
		return mapperScannerConfigurer;
	}
}
