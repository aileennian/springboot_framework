package com.nian.utils.typeutil;

import java.util.Random;

/**
 * 随机生成6位验证码
 * @author zq
 *
 */
public class RandomCode {
	public synchronized static int randomCode() {
		int[] array = {0,1,2,3,4,5,6,7,8,9};
		Random rand = new Random();
		for (int i = 10; i > 1; i--) {
		    int index = rand.nextInt(i);
		    int tmp = array[index];
		    array[index] = array[i - 1];
		    array[i - 1] = tmp;
		}
		int num = 0;
		for(int i = 0; i < 6; i++){
		    num = num * 10 + array[i];
		}
		if (String.valueOf(num).length()!=6) {
			return randomCode();
		}
		return num;
	}
}
