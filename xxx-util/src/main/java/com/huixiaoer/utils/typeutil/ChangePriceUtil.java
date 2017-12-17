package com.huixiaoer.utils.typeutil;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 
 * @ClassName:  ChangePriceUtil   
 * @Description:  更改Integer 类型的金额为保留 两位小数的字符串金额
 * @author: tangfeng   
 * @date:   2016年5月9日 下午8:31:53   
 *
 */
public class ChangePriceUtil {
	
	public static String ChangePriceForInteger(Integer price){
		String priceStr = null;
		if(price==null || price==0){
			priceStr = "0.00";
		}else{
			double doubleValue = price.doubleValue();
			BigDecimal bd = new BigDecimal(doubleValue);
			BigDecimal divide = bd.divide(new BigDecimal(100));
		    bd = divide.setScale(2, RoundingMode.HALF_UP);
		    priceStr = bd.toString();
		}
		return priceStr;
	}
	
	public static Integer ChangePrice(Double price){
		Integer pric = 0;
		if(price!=null){
			BigDecimal bd = new BigDecimal(price*100);
			//BigDecimal multiply = bd.multiply(new BigDecimal(100));//精度会丢失
			pric = bd.intValue();
		}
		return pric;
	}

//	public static void main(String[] args) {
//		double aa = 3.9;
//		Integer aaa = ChangePrice(aa);
//		System.out.println(aaa);
//		String bb = DoubleUtils.formatDouble(aa);
//		System.out.println(bb);
//	}
}
