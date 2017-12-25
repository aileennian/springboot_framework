package com.nian.mybatis.multi;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 
 * @author admin
 *
 */
public class DataSourceRoutingProxy extends AbstractRoutingDataSource {
	private DataSource writeDataSource;
	private Map<Object, DataSource> readDataSources;	
	private int readDataSourcePollPattern = 0; // 获取读数据源方式，0：随机，1：轮询
	private int readDsSize=0;
	private final Lock lock = new ReentrantLock();

	@Override
	public void afterPropertiesSet() {
		setDefaultTargetDataSource(writeDataSource);
		
		Map<Object, Object> targetDataSources = new HashMap<Object, Object> ();
		targetDataSources.put(DynamicDataSourceType.write.getType(), writeDataSource);
		int i=0;
	    for(DataSource item : readDataSources.values()){
	    	targetDataSources.put(i, item);
	    	i++;
        }
		setTargetDataSources(targetDataSources);
		readDsSize=readDataSources.size();		
		super.afterPropertiesSet();
	}

	@Override
	protected Object determineCurrentLookupKey() {
		AtomicLong counter = new AtomicLong(0);
		Long MAX_POOL = Long.MAX_VALUE;

		DynamicDataSourceType dynamicDataSourceType = DataSourceContextHolder.getDataSourceType();		
		if (dynamicDataSourceType == null || dynamicDataSourceType == DynamicDataSourceType.write
				|| dynamicDataSourceType == DynamicDataSourceType.writeForce
				|| readDsSize <= 0) {
			return DynamicDataSourceType.write.getType();
		}

		int index = 1;
		if (readDataSourcePollPattern == 1) {
			// 轮询方式
			long currValue = counter.incrementAndGet();
			if ((currValue + 1) >= MAX_POOL) {
				try {
					lock.lock();
					if ((currValue + 1) >= MAX_POOL) {
						counter.set(0);
					}
				} finally {
					lock.unlock();
				}
			}
			index = (int) (currValue % readDsSize);
		} else {
			// 随机方式
			index = ThreadLocalRandom.current().nextInt(0, readDsSize);
		}
		return index;
	}

  

	public Map<Object, DataSource> getReadDataSources() {
		return readDataSources;
	}

	public void setReadDataSources(Map<Object, DataSource> readDataSources) {
		this.readDataSources = readDataSources;
	}

	public int getReadDataSourcePollPattern() {
		return readDataSourcePollPattern;
	}

	public void setReadDataSourcePollPattern(int readDataSourcePollPattern) {
		this.readDataSourcePollPattern = readDataSourcePollPattern;
	}

	public DataSource getWriteDataSource() {
		return writeDataSource;
	}

	public void setWriteDataSource(DataSource writeDataSource) {
		this.writeDataSource = writeDataSource;
	}

	
	
}
