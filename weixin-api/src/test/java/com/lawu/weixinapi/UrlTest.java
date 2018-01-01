package com.lawu.weixinapi;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.junit.Test;

/**
 * @author Leach
 * @date 2018/1/1
 */
public class UrlTest {

    @Test
    public void test() throws UnsupportedEncodingException {
        String firstUrl = "http://www.baidu.com?p1=test&p2=5";
        String encodeFirstUrl = URLEncoder.encode(firstUrl, "utf-8");
        String encodeSecondUrl = URLEncoder.encode("http://wx.edian.shop?url=" + encodeFirstUrl, "utf-8");
        System.out.println(encodeFirstUrl);
        System.out.println(encodeSecondUrl);

        String decodeSecondUrl = URLDecoder.decode(encodeSecondUrl, "utf-8");
        String firstSecondUrl = URLDecoder.decode("http%3A%2F%2Fwww.baidu.com%3Fp1%3Dtest%26p2%3D5", "utf-8");
        System.out.println(decodeSecondUrl);
        System.out.println(firstSecondUrl);


        System.out.println(URLDecoder.decode("http%253A%252F%252Fwx.edian.api%252Fmp%252Fwx%252Fredirect%253Furl%253Dhttp%253A%252F%252Fwww.baidu.com%253Fp1%253Dtest%2526p2%253D5", "utf-8"));
    }
}
