package com.lawu.weixinapi.base;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回码
 *
 * @author Leach
 * @date 2017/3/13
 */
public class ResultCode {

    // 公共代码
    public static final int SUCCESS = 1000;
    public static final int FAIL = 1001;
    private static Map<Integer, String> messageMap = new HashMap<>();

    // 初始化状态码与文字说明
    static {
    	
        // 公共代码 1xxx
        ResultCode.messageMap.put(SUCCESS, "success");
        ResultCode.messageMap.put(FAIL, "fail");


    }

    public static String get(int code) {
        return ResultCode.messageMap.get(code);
    }

}
