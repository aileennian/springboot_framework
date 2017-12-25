package com.nian.mybatis;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInterceptor;
import com.nian.mybatis.aop.DataSourceMybatisPlugin;
import com.nian.mybatis.aop.DataSourceSpringAspect;
import com.nian.mybatis.multi.DataSourceRoutingProxy;
import com.nian.mybatis.properties.MyPageHelperProperties;
import com.nian.mybatis.properties.MybatisProperties;

/**
 * MyBatis基础配置
 *
 * @author liuzh
 * @since 2015-12-19 10:11
 */
public abstract class BaseMybatisConfigure implements TransactionManagementConfigurer {
	protected Logger logger = LogManager.getLogger(this.getClass());


	@Autowired
	protected MyPageHelperProperties pageHelperProperties;
	@Autowired
	protected PageHelper pageHelper;
	@Autowired
	protected MybatisProperties mybatisProperties;
	
	public SqlSessionFactory sqlSessionFactory(DataSourceRoutingProxy proxy,DataSourceMybatisPlugin dsPlugin) throws Exception {
		final SqlSessionFactoryBean bean = new SqlSessionFactoryBean();		
		//AbstractRoutingDataSource proxy = abstractRoutingDataSourceBean();
		//bean.setDataSource(proxy);
		bean.setDataSource(proxy);
		
		bean.setPlugins(getMybatisInteceptor(dsPlugin));
		
		bean.setTypeAliasesPackage(mybatisProperties.getTypeAliasesPackage());
		bean.setMapperLocations(mybatisProperties.resolveMapperLocations());
		
		
		
		return bean.getObject();

		// return null;
	}
	
	
	/**
	 *  默认用mybaits的插件做多数据库切换过滤
	 * @param proxy
	 * @return
	 * @throws Exception
	 */
	public SqlSessionFactory sqlSessionFactory(DataSourceRoutingProxy proxy) throws Exception {		
		return sqlSessionFactory(proxy,new DataSourceMybatisPlugin());
	}
	
	
	
	
	private Interceptor[] getMybatisInteceptor(DataSourceMybatisPlugin dsPlugin){
		PageInterceptor pageIpnterceptor = new PageInterceptor();
		pageIpnterceptor.setProperties(pageHelperProperties.getProperties());
		pageIpnterceptor.plugin(pageHelper);		
		
	
		List<Interceptor> interceptorList = new ArrayList<Interceptor>();
//		if (interceptors!=null){
//			for(int i=0;i<interceptors.length;i++){
//				interceptorList.add(interceptors[i]);
//			}
//		}
		
		
		interceptorList.add(pageIpnterceptor);
		if (dsPlugin!=null) interceptorList.add(dsPlugin);
		
		
		Interceptor[] result = new Interceptor[interceptorList.size()];
		for(int i=0;i<interceptorList.size();i++){
			result[i]=interceptorList.get(i);
		}		
		return result;
	}

	public PageHelper getPageHelperBean() {
		logger.info("注册MyBatis分页插件PageHelper");
		PageHelper pageHelper = new PageHelper();
		Properties p = new Properties();
		p.setProperty("offsetAsPageNum", pageHelperProperties.getOffsetAsPageNum());
		p.setProperty("rowBoundsWithCount", pageHelperProperties.getRowBoundsWithCount());
		p.setProperty("reasonable", pageHelperProperties.getReasonable());
		pageHelper.setProperties(p);

		this.pageHelper = pageHelper;
		return pageHelper;
	}

 

	@Bean
	public MybatisProperties getMybatisProperties(){
		return new MybatisProperties();
	}

	@Bean
	public MyPageHelperProperties getMyPageHelperProperties(){
		return new MyPageHelperProperties();
	}

	@Bean
	public  PageHelper getPageHelper(){
		return this.getPageHelperBean();
	}

 
	
	@Bean
	public DataSourceSpringAspect dataSourceSpringAspect(){
		return new DataSourceSpringAspect();
	}
	
    @Bean  
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {  
        return new SqlSessionTemplate(sqlSessionFactory);  
    } 
  

}
