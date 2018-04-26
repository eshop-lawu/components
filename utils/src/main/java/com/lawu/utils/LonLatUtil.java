package com.lawu.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author meishuquan
 * @date 2017/7/4.
 */
public class LonLatUtil {

    private static final String BAIDU_APP_KEY = "FyGGimVFBl1ZelsdbjExtQVeASp4xFo5";

    public static Map<String, String> getLonLat(String address) {
        try {
            // 将地址转换成utf-8的16进制
            address = URLEncoder.encode(address, "UTF-8");
            // 如果有代理，要设置代理，没代理可注释
            //	System.setProperty("http.proxyHost", "192.168.1.176");
            //	System.setProperty("http.proxyPort", "3209");
            URL resjson = new URL("http://api.map.baidu.com/geocoder/v2/?address=" + address + "&output=json&ak=" + BAIDU_APP_KEY);
            BufferedReader in = new BufferedReader(new InputStreamReader(resjson.openStream()));
            String res;
            StringBuilder sb = new StringBuilder("");
            while ((res = in.readLine()) != null) {
                sb.append(res.trim());
            }
            in.close();
            String str = sb.toString();
            //System.out.println("return json:" + str);
            if (!str.equals("")) {
                int lngStart = str.indexOf("lng\":");
                int lngEnd = str.indexOf(",\"lat");
                int latEnd = str.indexOf("},\"precise");
                if (lngStart > 0 && lngEnd > 0 && latEnd > 0) {
                    String lng = str.substring(lngStart + 5, lngEnd);
                    String lat = str.substring(lngEnd + 7, latEnd);
                    Map<String, String> map = new HashMap<>();
                    map.put("lng", lng);
                    map.put("lat", lat);
                    return map;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
