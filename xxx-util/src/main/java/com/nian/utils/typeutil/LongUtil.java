package com.nian.utils.typeutil;
/**
 * @author 周铭铢
 * @time 2016年3月28日 上午11:35:08
 */
public class LongUtil {
	
	public static long toLong(Long src){
		if(src==null)
			return 0l;
		return src.longValue();
	}
	/**
	 * 判断两个Long是否相等
	 * @author 周铭株
	 * at 2016年5月6日 下午12:01:46
	 * @param l1
	 * @param l2
	 * @return
	 */
	public static boolean isEq(Long l1,Long l2){
		return toLong(l1)==toLong(l2);
	}
	
	public static Long String2Long(String str){
		try {
			Long l=null==str?null:Long.valueOf(str);
			return l;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
