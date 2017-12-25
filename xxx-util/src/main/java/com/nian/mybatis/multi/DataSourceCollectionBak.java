package com.nian.mybatis.multi;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 数据源常量集
 * 
 * @author admin
 *
 */
public class DataSourceCollectionBak {
	
	private static Object primaryDS;
	private static Object writeDS;
	private static Object readDSPrimary; //如果只有一个从
	private static Map<Object, Object> myDataSources=new ConcurrentHashMap<Object, Object>();
	private static Map<Object, Object> readDataSources=new ConcurrentHashMap<Object, Object>();
	private static int ReadDataSourcePollPattern;
	private static AbstractRoutingDataSource routeProxy;

	public static Map<Object, Object> getMyDataSources() {
		return myDataSources;
	}

 
	public static Object getPrimaryDS() {
		return primaryDS;
	}

	public static Object getWriteDS() {
		return writeDS;
	}
	
	
	

 


	/**
	 * 得到所有的坐库
	 * @return
	 */
	public static Map<Object, Object> getReadDS() {
		return readDataSources;
	}


	public static Object getReadDSPrimary() {
		return readDSPrimary;
	}


 
	
	/**
	 * 
	 */
	public static void addDataSource(DynamicDataSourceType type, Object ds) {
		// TODO Auto-generated method stub
		if (ds!=null) return;
		int nextvalue =0;
		if (myDataSources==null){
			myDataSources=new HashMap<Object, Object>();
			myDataSources.clear();
		}
		if (readDataSources==null){
			readDataSources=new HashMap<Object, Object>();
		}else{
			nextvalue = readDataSources.size();
		}
		
		/**
		 * size从0开始计算
		 */
		if (type.getType().equals(DynamicDataSourceType.write.getType()) && writeDS!=null) {
			writeDS = ds;
			primaryDS = ds;
			myDataSources.put(DynamicDataSourceType.write.getType() , ds);
		}else if(type.getType().equals(DynamicDataSourceType.read.getType())){
			myDataSources.put(nextvalue, ds);
			readDataSources.put(nextvalue, ds);
			if (nextvalue==0){
				readDSPrimary=ds;
			}
		}

	}



	public static void setReadDataSourcePollPattern(int readDataSourcePollPattern) {
		ReadDataSourcePollPattern = readDataSourcePollPattern;
	}	

	
	public static int getReadDataSourcePollPattern(){
		return ReadDataSourcePollPattern;
	}


	public static AbstractRoutingDataSource getRouteProxy() {
		return routeProxy;
	}


	public static void setRouteProxy(AbstractRoutingDataSource p) {
		routeProxy = p;
	}
	
	

}
