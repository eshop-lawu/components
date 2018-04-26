package com.lawu.utils;

import org.junit.Ignore;
import org.junit.Test;

import com.gexin.fastjson.JSONObject;
import com.lawu.utils.GtPush;

/**
 *  个推 推送工程类
 */
public class GtPushTest {

    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String TYPE = "type";
    public static final String RELATE_ID = "relateId";
    
    public static final String appkey = "80f80fVehg9LEUxJvAnLl7";
    public static final String masterSecret = "K3DZu2v3a38FYryMK9H8c9";
    public static final String appId = "fuAAF6k2Lh85oNyVLuWNC1";
	
    @Ignore
	@Test
    public void sendMessageToCid() {
		JSONObject contents = new JSONObject();
        contents.put(TITLE, "测试");
        contents.put(CONTENT, "测试测试");
        contents.put(RELATE_ID, 1);
        contents.put(TYPE, (byte)3);
    	GtPush gtPush = new GtPush();
    	String result = gtPush.sendMessageToCidCustoms(contents.toJSONString(), "545bdd6a97a20346064dd368c28697dc", "测试测试", "http://sdk.open.api.igexin.com/apiex.htm", appkey, masterSecret, appId);
    	System.out.println(result);
    }
}
