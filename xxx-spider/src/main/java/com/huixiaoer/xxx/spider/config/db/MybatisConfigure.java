package com.huixiaoer.xxx.spider.config.db;

import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;  
import org.apache.logging.log4j.Logger;  
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;
import com.huixiaoer.mybatis.BaseMybatisConfigure;
import com.huixiaoer.mybatis.aop.DataSourceMybatisPlugin;
import com.huixiaoer.mybatis.aop.DataSourceSpringAspect;
import com.huixiaoer.mybatis.multi.DataSourceRoutingProxy;
import com.huixiaoer.mybatis.multi.DynamicDataSourceTransactionManager;
import com.huixiaoer.mybatis.properties.DataSourceProperties;
import com.huixiaoer.mybatis.properties.Read1DataSourceProperties;


/**
 * 
 * @author admin
 *
 */
@Configuration
@EnableTransactionManagement
public class MybatisConfigure  extends BaseMybatisConfigure{
	protected Logger logger = LogManager.getLogger(this.getClass());	

    
	@Autowired
	@Qualifier("writeDS")
	private DataSource dataSource;
	
	@Autowired
	@Qualifier("readDS0")
	private DataSource readDataSource0;	
	
	@Autowired
    protected ApplicationContext context; 
	
	@Autowired
	@Qualifier("dynamicDataSource")
    protected DataSourceRoutingProxy routingProxy; 	
	
	@Bean("dynamicDataSource")
	@Primary
	public DataSourceRoutingProxy dataSourceRoutingProxyBean(){
		DataSourceRoutingProxy routeProxy = new DataSourceRoutingProxy();
		routeProxy.setWriteDataSource(dataSource);
		Map<Object, DataSource> readDS=new ConcurrentHashMap<Object, DataSource>();
		readDS.put(0, readDataSource0);
		
		routeProxy.setWriteDataSource(dataSource);
		routeProxy.setReadDataSourcePollPattern(0);
		routeProxy.setReadDataSources(readDS);
		
		return routeProxy;
	}
	
	
	@Bean(name = "writeDS")
	public DataSource setWriteDS(@Autowired DataSourceProperties dataSourceProperties) {
		DruidDataSource datasource = new DruidDataSource();

		datasource.setUrl(dataSourceProperties.getDbUrl());
		datasource.setUsername(dataSourceProperties.getUsername());
		datasource.setPassword(dataSourceProperties.getPassword());
		datasource.setDriverClassName(dataSourceProperties.getDriverClassName());

		// configuration
		datasource.setInitialSize(dataSourceProperties.getInitialSize());
		datasource.setMinIdle(dataSourceProperties.getMinIdle());
		datasource.setMaxActive(dataSourceProperties.getMaxActive());
		datasource.setMaxWait(dataSourceProperties.getMaxWait());
		datasource.setTimeBetweenEvictionRunsMillis(dataSourceProperties.getTimeBetweenEvictionRunsMillis());
		datasource.setMinEvictableIdleTimeMillis(dataSourceProperties.getMinEvictableIdleTimeMillis());
		datasource.setValidationQuery(dataSourceProperties.getValidationQuery());
		datasource.setTestWhileIdle(dataSourceProperties.isTestWhileIdle());
		datasource.setTestOnBorrow(dataSourceProperties.isTestOnBorrow());
		datasource.setTestOnReturn(dataSourceProperties.isTestOnReturn());
		datasource.setPoolPreparedStatements(dataSourceProperties.isPoolPreparedStatements());
		datasource.setMaxPoolPreparedStatementPerConnectionSize(
				dataSourceProperties.getMaxPoolPreparedStatementPerConnectionSize());
		try {
			datasource.setFilters(dataSourceProperties.getFilters());
		} catch (SQLException e) {
			logger.error("druid configuration initialization filter : {0}", e);
		}
		datasource.setConnectionProperties(dataSourceProperties.getConnectionProperties());
		
		//置入多数据源
		//DataSourceCollection.addDataSource(DynamicDataType.write,datasource);
		return datasource;
	}
	
	@Bean(name = "readDS0")
	public DataSource setReadDS0(
			@Autowired Read1DataSourceProperties dataSourceProperties
			) {
		DruidDataSource datasource = new DruidDataSource();

		datasource.setUrl(dataSourceProperties.getDbUrl());
		datasource.setUsername(dataSourceProperties.getUsername());
		datasource.setPassword(dataSourceProperties.getPassword());
		datasource.setDriverClassName(dataSourceProperties.getDriverClassName());

		// configuration
		datasource.setInitialSize(Integer.parseInt(dataSourceProperties.getInitialSize()));
		datasource.setMinIdle(dataSourceProperties.getMinIdle());
		datasource.setMaxActive(dataSourceProperties.getMaxActive());
		datasource.setMaxWait(dataSourceProperties.getMaxWait());
		datasource.setTimeBetweenEvictionRunsMillis(dataSourceProperties.getTimeBetweenEvictionRunsMillis());
		datasource.setMinEvictableIdleTimeMillis(dataSourceProperties.getMinEvictableIdleTimeMillis());
		datasource.setValidationQuery(dataSourceProperties.getValidationQuery());
		datasource.setTestWhileIdle(dataSourceProperties.isTestWhileIdle());
		datasource.setTestOnBorrow(dataSourceProperties.isTestOnBorrow());
		datasource.setTestOnReturn(dataSourceProperties.isTestOnReturn());
		datasource.setPoolPreparedStatements(dataSourceProperties.isPoolPreparedStatements());
		datasource.setMaxPoolPreparedStatementPerConnectionSize(
		datasource.getMaxPoolPreparedStatementPerConnectionSize());
		try {
			datasource.setFilters(dataSourceProperties.getFilters());
		} catch (SQLException e) {
			logger.error("druid configuration initialization filter : {0}", e);
		}
		datasource.setConnectionProperties(dataSourceProperties.getConnectionProperties());
		
		//DataSourceCollection.addDataSource(DynamicDataType.read,datasource);
		return datasource;
	}

	
	@Bean
	public SqlSessionFactory sqlSessionFactory() {
		try{
			
			return super.sqlSessionFactory(routingProxy,dataSourceMybatisPlugin());
			//return super.sqlSessionFactory(dataSourceRoutingProxyBean());
			//return super.sqlSessionFactory(dataSourceRoutingProxyBean(),null);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
		
    @Bean  
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {  
        return new SqlSessionTemplate(sqlSessionFactory);  
    }
	
	@Bean
	public DataSourceProperties setDataSourceProperties() {
		return new DataSourceProperties();
	}

	
	@Bean
	public Read1DataSourceProperties setRead1DataSourceProperties() {
		return new Read1DataSourceProperties();
	}

	@Bean
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		//AbstractRoutingDataSource proxy = SpringUtil.getBean("roundRobinDataSouceProxy");		
		//return new DynamicDataSourceTransactionManager(roundRobintDataSouceProxy);
		return new DynamicDataSourceTransactionManager(routingProxy.getWriteDataSource());
	}

	@Bean
	public DataSourceSpringAspect beanSourceSpringAspect(){
		return new DataSourceSpringAspect();
	}
 
//	@Bean
	public DataSourceMybatisPlugin dataSourceMybatisPlugin(){
		return new DataSourceMybatisPlugin();
	}
	
}
