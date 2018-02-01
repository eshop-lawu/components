package service.impl;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gexin.fastjson.JSONObject;
import com.lawu.push.service.GtPushService;

/**
 * @author zhangyong
 * @date 2018/2/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring*.xml"})
public class PushServiceImplTest {

    @Autowired
    @Qualifier("pushServiceImpl")
    private GtPushService pushService;


    @Test
    @Ignore
    public void pushMessageToSingle() {

        JSONObject json = new JSONObject();
        json.put("title", "测试新推送4");
        json.put("content", "测试新推4");
        json.put("type", "MESSAGE_TYPE_RECHARGE_BALANCE");
        String result = pushService.pushMessageToSingle("724f770ecc43d1c720dc8e3fef9ae163", "test", json.toString());
        Assert.assertEquals("ok", result);
    }

    @Test
    @Ignore
    public void pushMessageToAll() {
        JSONObject json = new JSONObject();
        json.put("title", "测试推送所有");
        json.put("content", "测试推送所有");
        json.put("type", "MESSAGE_TYPE_RECHARGE_BALANCE");
        String result = pushService.pushMessageToAll("测试推送所有", json.toString());
        Assert.assertEquals("ok", result);
    }
}
