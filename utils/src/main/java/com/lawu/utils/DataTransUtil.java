package com.lawu.utils;

import java.math.BigDecimal;

/**
 * <p>
 * Description: 数据转换工具类
 * </p>
 *
 * @author Yangqh
 * @date 2017年3月27日 下午8:03:20
 */
public class DataTransUtil {

    /**
     * Integer转byte
     *
     * @param i
     * @return
     */
    public static byte intToByte(Integer i) {
        int x = i;
        return (byte) x;
    }

    /**
     * byte转Integer
     *
     * @param b
     * @return
     */
    public static Integer byteToInt(byte b) {
        return b & 0xFF;
    }

    /**
     * 数字转换为指定小数位Double
     *
     * @param obj        数字类型对象
     * @param decimalNum 保留小数位数
     * @return
     */
    public static Double objectToDobule(Object obj, int decimalNum) {
        if (obj == null) {
            return 0.0;
        }
        BigDecimal decimal = new BigDecimal(obj.toString());
        return decimal.setScale(decimalNum, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

}
