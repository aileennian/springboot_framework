package com.huixiaoer.utils.typeutil;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by xianghan on 15/12/30.
 */
public class DoubleUtils {
    public static Double getDouble(Double n){
        if (n != null) {
            BigDecimal b = new BigDecimal(n);
            return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        return n;
    }

    public static String formatDouble(Object n){
        if (n != null) {
            DecimalFormat df=new DecimalFormat("#.00");
            return df.format(n);
        }
        return null;
    }

    public static String double2String(Double n){
        return formatDouble(getDouble(n));
    }
    /**
     * add by 周铭铢
     * @param str
     * @return
     */
    public static double getDouble(String str){
    	if(StringUtils.isEmpty(str))
    		return 0d;
    	return Double.parseDouble(str);
    }
    
    /**
     * add by 周铭铢
     * @param str
     * @return
     */
    public static double getDouble(Object obj){
    	return getDouble(obj.toString());
    }
    
    /**
     * 金额取整
     *
     * @author xianghan
     * @param doublePrice
     * @return
     */
	public static String filterDecimal(Double doublePrice) {
		if(doublePrice!=null){
			Integer intN=doublePrice.intValue();
			if(doublePrice>intN){
				return doublePrice.toString();
			}else{
				return intN.toString();
			}
		}
		return null;
	}
}
