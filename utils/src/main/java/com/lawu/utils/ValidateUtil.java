package com.lawu.utils;

import java.util.regex.Pattern;

/**
 *
 * Created by zhangyong on 2017/3/27.
 */
public class ValidateUtil {

    /**
     * 正则表达式：验证身份证
     */
    public static final String VALIDATE_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";

    /**
     * 校验图片格式
     * @param jsonResult
     * @param prefix
     * @return
     */
    public static boolean validateImageFormat(String prefix) {
        if(prefix.indexOf("jpg") < 0 && prefix.indexOf("gif") < 0 && prefix.indexOf("png") < 0 && prefix.indexOf("bmp") < 0 && prefix.indexOf("jpeg") < 0){
            return false;
        }else{
            return true;
        }
    }

    /**
     * 校验身份证
     *
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(VALIDATE_ID_CARD, idCard);
    }

}
