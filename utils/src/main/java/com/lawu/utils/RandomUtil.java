package com.lawu.utils;

import java.util.Random;

/**
 * 生成随机字符串工具类
 *
 * @author meishuquan
 * @date 2017/3/22
 */
public class RandomUtil {

    private static final String STR_1 = "0123456789";
    private static final String STR_2 = "0123456789abcdefghijklmnopqrstuvwxyz";
    private static final String STR_3 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String STR_4 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String STR_5 = "abcdefghijklmnopqrstuvwxyz";
    private static final String STR_6 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String STR_7 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private RandomUtil() {
    }

    /**
     * 生成随机字符串
     *
     * @param type
     *            产生随机数类型：1--数字，2--数字+小写字母，3--数字+大写字母，4--数字+字母，5--小写字母，6--大写字母，7--字母
     * @param length
     *            产生随机数长度
     * @return String
     * @date 2017/3/22
     */
    public static String getRandomString(int type, int length) {
        String str;
        switch (type) {
        case 1:
            str = STR_1;
            break;
        case 2:
            str = STR_2;
            break;
        case 3:
            str = STR_3;
            break;
        case 4:
            str = STR_4;
            break;
        case 5:
            str = STR_5;
            break;
        case 6:
            str = STR_6;
            break;
        case 7:
            str = STR_7;
            break;
        default:
            return "";
        }
        if (length <= 0) {
            return "";
        }
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 构建文件上传文件名，系统统一文件名
     *
     * @param prefix
     *            后缀名
     * @return
     */
    public static final String buildFileName(String prefix) {
        return String.valueOf(System.currentTimeMillis()) + RandomUtil.getRandomString(1, 6) + prefix;
    }

    /**
     * 广告投放单号
     * 
     * @return
     */
    public static final String expandOrder() {
        return String.valueOf(System.currentTimeMillis()) + RandomUtil.getRandomString(1, 6);
    }

}
