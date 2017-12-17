package com.huixiaoer.utils;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

 


public class PropertiesUtil {
	   /** 记录日志的变量 */
	protected static Logger logger = LogManager.getLogger(PropertiesUtil.class);
	
    /** 类实例变量 */
    private static PropertiesUtil      instance = null;
    /** 系统的默认自定义properties */
    public static Map<String, String> config   = null;

    /** 私有构造函数 */
    private PropertiesUtil() {
    }

    static {
        config = PropertiesUtil.getInstance().getProperties("/util/util.properties");
    }

    /**
     * 获取实例变量
     * 
     * @return PropertiesUtil实例
     */
    public static PropertiesUtil getInstance() {
        if (instance == null) {
            instance = new PropertiesUtil();
        }
        return instance;
    }
    
    /**
     * 
     * @author zhangjinlong
     * @date 2015年10月1日
     * @param key
     * @return
     */
    public static String getValue(String key){
    	return config.get(key);
    }
    
    /**
     * 得到属性对应的值
     * @author zhangjinlong
     * @date 2015年4月24日
     * @param keyName
     * @return
     */
    public static String  getValueByKey(String keyName){
    	if (config!=null && config.containsKey(keyName)){
    		return config.get(keyName);
    	}else{
    		return null;
    	}
    }
    
    /**
     * 得到当前系统环境
     * @author zhangjinlong
     * @date 2015年6月3日
     * @return
     */
    public static String getEnvName(){
    	if (config!=null && config.containsKey("envName")){
    		return config.get("envName");
    	}else{
    		return null;
    	}
    }
    

    /**
     * 根据<code>Properties</code>文件名取得所有的键值
     * 
     * @param filename 类路径下的<code>Properties</code>文件名
     * @return
     */
    public Map<String, String> getProperties(String filename) {
        Map<String, String> map = new HashMap<String, String>();
        InputStream is = null;
        try {
            is = getClass().getResourceAsStream(filename);
            if (is == null) {
                return map;
            }
            Properties pro = new Properties();
            pro.load(is);
            Enumeration<?> e = pro.propertyNames();
            String key = null;
            String value = null;
            while (e.hasMoreElements()) {
                key = (String) e.nextElement();
                value = pro.getProperty(key);
                map.put(key, value == null ? "" : value.trim());
            }
        } catch (IOException ie) {
            logger.error(ie.getMessage(),ie);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ie) {
                   // logger.error(ie.getMessage());
                }
            }
        }
        return map;
    }

    /**
     * 根据<code>Properties</code>文件名取得所有的键值
     * 
     * @param filename 文件绝对路径<code>Properties</code>文件名
     * @return
     */
    public Map<String, String> getPropertiesByFilePath(String filename) {
        Map<String, String> map = new HashMap<String, String>();
        InputStream is = null;
        try {
            is = new FileInputStream(filename);
            Properties pro = new Properties();
            pro.load(is);
            Enumeration<?> e = pro.propertyNames();
            String key = null;
            String value = null;
            while (e.hasMoreElements()) {
                key = (String) e.nextElement();
                value = pro.getProperty(key);
                map.put(key, value == null ? "" : value.trim());
            }
        } catch (IOException ie) {
            logger.error(ie.getMessage(),ie);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ie) {
                   // logger.error(ie.getMessage(),ie);
                }
            }
        }
        return map;
    }
}
