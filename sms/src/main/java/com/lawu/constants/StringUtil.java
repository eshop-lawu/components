package com.lawu.constants;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtil {

    private static Logger logger = LoggerFactory.getLogger(StringUtil.class);

    /**
     * ISO-8859-1 转换 UTF-8编码
     *
     * @param isoString
     * @return
     */
    public static String getUtf8String(String isoString) {
        if (isoString == null || isoString.trim().length() == 0) {
            return "";
        }
        try {
            return new String(isoString.getBytes("iso-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("转换UTF-8异常：{}", e);
        }
        return isoString;
    }
}
