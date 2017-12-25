package com.nian.mybatis.multi;

public class DataSourceContextHolder {
    //private static final ThreadLocal<DataSourceType> local = new ThreadLocal<DataSourceType>(DataSourceType.class);

    private static final ThreadLocal<DynamicDataSourceType> holder = new ThreadLocal<DynamicDataSourceType>();
    
//    public static ThreadLocal<DataSourceType> getHolder() {
//        return holder;
//    }
  
    /**
     * 设置当前线程使用的数据源
     */
    public static void putDataSourceType(DynamicDataSourceType dataSource){
    	holder.set(dataSource);
    }

    /**
     * 设置当前线程使用的数据源
     */
    public static DynamicDataSourceType getDataSourceType(){
        return holder.get();
    }

    
    /**
     * 清空使用的数据源
     */
    public static void clear() {
    	holder.remove();
    }
    
    
//    public static void markWrite() {  
//        holder.set(DynamicDataSourceGlobal.write);  
//    }  
//      
//    public static void markRead() {  
//        holder.set(DynamicDataSourceGlobal.read);  
//    }  
//    
//    public static void reset() {  
//        holder.set(null);  
//    } 
//    
//    public static boolean isChoiceNone() {  
//        return null == holder.get();   
//    }  
//      
//    
//    public static boolean isChoiceWrite() {  
//        return DynamicDataSourceGlobal.write == holder.get();  
//    }  
//      
//    
//    public static boolean isChoiceRead() {  
//        return DynamicDataSourceGlobal.read == holder.get();  
//    }  
    
    
}
