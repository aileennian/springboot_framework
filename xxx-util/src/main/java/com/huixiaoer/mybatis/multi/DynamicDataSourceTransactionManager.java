package com.huixiaoer.mybatis.multi;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * 
 * @author admin
 *
 */
public class DynamicDataSourceTransactionManager extends DataSourceTransactionManager {
	private static final long serialVersionUID = 1L;

	public DynamicDataSourceTransactionManager(DataSource dataSource) {
		super();
		setDataSource(dataSource);
		afterPropertiesSet();
	}

	/**
	 * 只读事务到读库，读写事务到写库
	 */
//	@Override
//	protected void doBegin(Object transaction, TransactionDefinition definition) {
//		// 根据事务可读性进行判断
//		boolean readOnly = definition.isReadOnly();
//		// 只读类型事务可以只用从库
//		if (readOnly) {
//			DataSourceContextHolder.putDataSource(DynamicDataSourceGlobal.read);
//		} else {
//			DataSourceContextHolder.putDataSource(DynamicDataSourceGlobal.write);
//		}
//		super.doBegin(transaction, definition);
//	}

	/**
	 * 清理本地线程的数据源（会被默认调用，调用时清除相应数据源）
	 */
	@Override
	protected void doCleanupAfterCompletion(Object transaction) {
		super.doCleanupAfterCompletion(transaction);
		DataSourceContextHolder.clear();
	}
}
