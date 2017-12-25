package com.nian.ehcache;

import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;




 /**
  * 基础类
  * @author admin
  *
  */
public class BaseKeyConstant {
	protected Logger logger = LogManager.getLogger(this.getClass());

	/**
	 * key前缀
	 */
	public static final String KEY_PREFIX = "EH:TPS";
	/**
	 * CacheName和ehCache.xml里配置的名称要保持一至，包括大小写
	 * @author nianxiaoling[14110310]
	 * @date 2015年10月29日 上午11:07:17
	 * 没cache.xml文件一致
	 */
	public static enum CacheName{
		defaultCache,		
		fifteenCache,
		halfHourCache,
		twoHourCache,
		oneMinuteCache,
	}
	
	
	

	
	/**
	 * 得到cache名称
	 * @author nianxiaoling[14110310]
	 * @date 2015年10月29日
	 * @param element
	 * @return
	 */
	public static String getCacheName(String element){
		if (element==null || element.trim().equals("")){
			return "";
		}
		for (CacheName s:CacheName.values()){
			if(element.toLowerCase().indexOf(s.name().toLowerCase())!=-1){
				return s.name();
			}
		}
		
		throw new RuntimeException("对应的缓存编号不存在！");		
	}
	
	
  

	/**
	 * 得到集合等非实体对象的Key key中的过期时间不能去掉。
	 * @param <T>
	 * 
	 * @param <T>
	 * @param abstractElement
	 *            :含有规则的名称
	 * @param bys
	 *            :条件：key为字段名，value为具体的值
	 * @param orders
	 *            ：排序：格式:orders:key为字段名，value为asc或desc
	 * @return
	 */
	public static <T> CacheElement getEhcacheKey(String abstractElement,
			Map<String, T> bys) {
		String factKey = getKeyPrefix(abstractElement);
		String where = getConditions(abstractElement, bys);

		if (where != null && !where.equals("")){
			factKey += "{by:" + where + "}";
		}
		
		String cacheName = getCacheName(factKey);
		
		CacheElement bo = new CacheElement();
		bo.setCache(cacheName);
		bo.setElement(factKey.toLowerCase());		
		return bo;
	}
	
		
	/**
	 * 不含任何条件的key的取法
	 * 
	 * @param abstractElement
	 * @param bys
	 * @return
	 */
	public static CacheElement getEhcacheKey(String abstractElement) {
		if (abstractElement.toLowerCase().indexOf("{by") > -1) {
			throw new RuntimeException("规则不符！原定元素中含有条件" + abstractElement);
		}
		return getEhcacheKey(abstractElement, null);
	}


	/**
	 * 得到实际key的某个条件的值
	 * 
	 * @author nianxiaoling[14110310]
	 * @date 2015年3月31日
	 * @param key
	 *            :实际的redis的eky
	 * @param whereKey
	 *            :条件参数
	 * @return
	 */
	public static String getConditionValue(String key, String whereKey) {
		String tmp = key.substring(key.indexOf("{by:") + 4);
		String where = tmp.substring(0, tmp.indexOf("}"));

		if (where != null && where != "") {
			String[] ary = where.split(",");
			for (String v : ary) {
				String by = v.substring(0, v.indexOf("=")).trim();
				String value = v.substring(v.indexOf("=") + 1);
				if (by.equalsIgnoreCase(whereKey)) {
					return value;
				}
			}
		}
		return null;
	}

	/**
	 * 生成条件，以','号分隔，前面无"by"
	 * 
	 * @param <T>
	 * @param abstractElement
	 * @param bys
	 * @return
	 */
	public static <T> String getConditions(String abstractElement,
			Map<String, T> bys) {
		String whererule = getConditionRule(abstractElement);
		if (bys == null || whererule == null || bys.size() == 0)
			return null;

		StringBuffer where = new StringBuffer("");
		whererule = whererule.replace(" ", "").toLowerCase();
		for (String field : bys.keySet()) {
			if (field != null
					&& whererule.indexOf(field.toLowerCase() + "=") > -1) {
				if (!where.toString().equals("")) {
					where.append(",");
				}
				String value = bys.get(field) == null ? "" : String.valueOf(
						bys.get(field)).trim();
				where.append(field + "=" + value);
			} else {
				throw new RuntimeException("条件不符！原定条件:" + whererule + ",传入参数:"
						+ bys.toString());
			}
		}

		int whereruleCount = getConditionCount(whererule);
		int wherecount = getConditionCount(where.toString());

		if (whereruleCount != wherecount) {
			 throw new
			 	RuntimeException("条件不符！原定条件:"+whererule+",传入参数:"+bys.toString());
		}

		return where.toString();
	}

	/**
	 * 得到条件个数
	 * 
	 * @param keyRule
	 * @return
	 */
	public static int getConditionCount(String where) {
		if (where == null)
			return 0;
		String[] rules = where.split(",");
		return rules.length;
	}
	
	
	protected static String createKey(String cacheName,String mainKey,String[] conditions){
		String key = cacheName+"."+mainKey;
		if(conditions!=null && conditions.length>0){
			key+="{by:";
			for(int i = 0 ; i < conditions.length ; i++){
				if(i == conditions.length-1){
					key+=conditions[i]+"=?";
				}else{
					key+=conditions[i]+"=?,";
				}
			}
			key+="}";
		}
		return key;
	}

	

	/**
	 * 得到条件
	 * 
	 * @author nianxiaoling[14110310]
	 * @date 2015年3月30日
	 * @param key
	 * @param paramKey
	 * @return
	 */
	public static String getWhere(String key, String paramKey) {
		return "";
	}

	/**
	 * 得到查询条件，全部小写,中间用逗 号隔开
	 * 
	 * @param key
	 */
	public static String getConditionRule(String abstractElement) {
		if (abstractElement == null)
			return null;
		abstractElement = abstractElement.toLowerCase().replaceAll(" ", "");
		String tag = "{by:";
		if (abstractElement != null && abstractElement.indexOf(tag) > -1) {
			abstractElement = abstractElement.substring(abstractElement.indexOf(tag));
			abstractElement = abstractElement
					.substring(0, abstractElement.indexOf("}") + 1);
			abstractElement = abstractElement.replaceAll("by:", "");
			String c = abstractElement.substring(1, abstractElement.length() - 1);
			return c;
		}
		return null;
	}

	
	
	/**
	 * 返回key的前缀
	 * 
	 * @param key
	 */
	public static String getKeyPrefix(String abstractElement) {
		if (abstractElement == null)
			return "";
		abstractElement = abstractElement.toLowerCase().replaceAll(" ", "");
		String tag = "{";
		if (abstractElement.indexOf("{") > -1) {
			abstractElement = abstractElement.substring(0, abstractElement.indexOf(tag));
		}
		return abstractElement.toLowerCase();
	}
	

}
