package com.huixiaoer.mybatis.aop;

import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.huixiaoer.mybatis.multi.DataSourceContextHolder;
import com.huixiaoer.mybatis.multi.DynamicDataSourceType;


/**
 * 暂不用
 * @author admin
 *
 */
//@Component
@Intercepts({
@Signature(type = Executor.class, method = "update", args = {
        MappedStatement.class, Object.class }),
@Signature(type = Executor.class, method = "query", args = {
        MappedStatement.class, Object.class, RowBounds.class,
        ResultHandler.class }) })
public class DataSourceMybatisPlugin implements Interceptor
	{
	protected Logger logger = LogManager.getLogger(this.getClass());

    private static final String REGEX = ".*insert\\u0020.*|.*delete\\u0020.*|.*update\\u0020.*.*|.*create\\u0020.*";

    private static final Map<String, DynamicDataSourceType> cacheMap = new ConcurrentHashMap<>();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
    	DynamicDataSourceType dynamicDataSourceType  = null;    	
    	if (DataSourceContextHolder.getDataSourceType()==DynamicDataSourceType.writeForce){
    		return invocation.proceed();
    	}
    	
        boolean synchronizationActive = TransactionSynchronizationManager.isSynchronizationActive();
        if(!synchronizationActive) {
            Object[] objects = invocation.getArgs();
            MappedStatement ms = (MappedStatement) objects[0];
            if((dynamicDataSourceType  = cacheMap.get(ms.getId())) == null) {
                //读方法
                if(ms.getSqlCommandType().equals(SqlCommandType.SELECT)) {
                    //!selectKey 为自增id查询主键(SELECT LAST_INSERT_ID() )方法，使用主库
                    if(ms.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)) {
                    	dynamicDataSourceType  = DynamicDataSourceType.write;
                    } else {
                        BoundSql boundSql = ms.getSqlSource().getBoundSql(objects[1]);
                        String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replaceAll("[\\t\\n\\r]", " ");
                        if(sql.matches(REGEX)) {
                        	dynamicDataSourceType  = DynamicDataSourceType.write;
                        } else {
                        	dynamicDataSourceType  = DynamicDataSourceType.read;
                        }
                    }
                }else{
                	dynamicDataSourceType  = DynamicDataSourceType.write;
                }
                logger.info("设置方法[{}] use [{}] Strategy, SqlCommandType [{}]..", ms.getId(), dynamicDataSourceType .name(), ms.getSqlCommandType().name());
                cacheMap.put(ms.getId(), dynamicDataSourceType );
            }
        }
        else{
        	/**
        	 * TODO:如果存在事务，则需要指定数据据访问。除非用链式事务（分布式事tl。读库不接收事务管理
        	 */
        	dynamicDataSourceType=DynamicDataSourceType.write;
        }
        DataSourceContextHolder.putDataSourceType(dynamicDataSourceType );
        return invocation.proceed();
    }	

    //@Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

   // @Override
    public void setProperties(Properties properties) {
        //
    }
 

}
