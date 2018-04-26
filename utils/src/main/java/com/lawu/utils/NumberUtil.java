package com.lawu.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Random;

/**
 * 数字工具类
 * 
 * @author Sunny
 * @date 2017年4月14日
 */
public class NumberUtil {

	/**
	 * 隐藏构造函数
	 */
	private NumberUtil() {
		throw new IllegalAccessError("Utility class");
	}

	/**
	 * 数字的一般格式 保留两位小数，没有留0
	 */
	private static final String PATTERN_GENERAL = "#0.00";

	/**
	 * 格式化BigDecimal为字符串
	 * 
	 * @param number
	 * @param pattern
	 *            格式
	 * @return
	 * @author Sunny
	 */
	public static String format(BigDecimal number, String pattern) {
		String rtn = null;

		if (number == null) {
			return rtn;
		}

		DecimalFormat df = new DecimalFormat(pattern);
		df.setRoundingMode(RoundingMode.HALF_UP);
		rtn = df.format(number);

		return rtn;
	}

	/**
	 * 格式化BigDecimal为字符串
	 * 
	 * @param number
	 * @return
	 * @author Sunny
	 */
	public static String format(BigDecimal number) {
		String rtn = null;

		if (number == null) {
			return rtn;
		}

		DecimalFormat df = new DecimalFormat(PATTERN_GENERAL);
		df.setRoundingMode(RoundingMode.HALF_UP);
		rtn = df.format(number);

		return rtn;
	}

	/**
	 * 随机指定范围内N个不重复的数
	 * 在初始化的无重复待选数组中随机产生一个数放入结果中，
	 * 将待选数组被随机到的数，用待选数组(len-1)下标对应的数替换
	 * 然后从len-2里随机产生下一个随机数，如此类推
	 * @param max  指定范围最大值
	 * @param min  指定范围最小值
	 * @param n  随机数个数
	 * @return int[] 随机数结果集
	 */
	public static Integer[] randomArray(int min,int max,int n){
		int len = max-min+1;

		if(max < min || n > len){
			return null;
		}

		//初始化给定范围的待选数组
		int[] source = new int[len];
		for (int i = min; i < min+len; i++){
			source[i-min] = i;
		}

		Integer[] result = new Integer[n];
		Random rd = new Random();
		int index = 0;
		for (int i = 0; i < result.length; i++) {
			//待选数组0到(len-2)随机一个下标
			index = Math.abs(rd.nextInt() % len--);
			//将随机到的数放入结果集
			result[i] = source[index];
			//将待选数组中被随机到的数，用待选数组(len-1)下标对应的数替换
			source[index] = source[len];
		}

		return result;
	}
}
