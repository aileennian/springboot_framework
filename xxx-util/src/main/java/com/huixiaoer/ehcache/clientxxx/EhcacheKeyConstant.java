package com.huixiaoer.ehcache.clientxxx;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.huixiaoer.ehcache.BaseKeyConstant;
import com.huixiaoer.ehcache.CacheElement;


public class EhcacheKeyConstant extends BaseKeyConstant{
	/**
	 * key前缀
	 */
	public static final String KEY_PREFIX = "EH:TPS";
	
	//key专区	
	public final static String  ELEMENT_PCINDEX_INFOPUBLISH = KEY_PREFIX + CacheName.defaultCache.name() + ".infopublish{by:createtime=?}";
	///广告key
	public final static String ELEMENT_PCINDEX_INFOADV		=  KEY_PREFIX + createKey(CacheName.defaultCache.name(),"infoAdv",new String[]{"categoryId","placeNum"});
	///最新申请成功用户key
	public final static String ELEMENT_NEW_USER_APPLIED 	 =  KEY_PREFIX + createKey(CacheName.defaultCache.name(),"newApplyUser",null);
	
	public final static String  ELEMENT_TRY_ALL_PRODUCTCODES = KEY_PREFIX + CacheName.defaultCache.name() + ".tpsproductCodes";	
	public final static String  ELEMENT_REPORT_COUNT 		 =  KEY_PREFIX + CacheName.defaultCache.name() 		  + ".reportCoun{by:productCode=?,appCode=?}";
	public final static String  ELEMENT_CATEGORY_ID 		 =  KEY_PREFIX + CacheName.defaultCache.name() + ".Category_Pinyin{by:pingying=?,createtime=?}";

		
	
	/**
	 * 
	 * 这是在具体代码里的例子
	 * 公开信息
	 * @author nianxiaoling[14110310]
	 * @date 2015年10月29日
	 * @return
	 */
	public static CacheElement  getElementForInfoPublish(){
		Map<String, String> bys = new LinkedHashMap<String, String>();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String date = format.format(new Date());
		bys.put("createtime", date);	
		
		return getEhcacheKey(ELEMENT_PCINDEX_INFOPUBLISH, bys);
	}	
	
	/**
	 * 试用系统涉及到的试用品子码;jsonp中应用
	 * @author nianxiaoling[14110310]
	 * @date 2015年11月1日
	 * @return
	 */
	public static CacheElement  getElementOwnProductCodes(){
		Map<String, String> bys = new LinkedHashMap<String, String>();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String date = format.format(new Date());
		bys.put("createtime", date);
		
		return getEhcacheKey(ELEMENT_TRY_ALL_PRODUCTCODES);
	}
	
		
	
	
}
