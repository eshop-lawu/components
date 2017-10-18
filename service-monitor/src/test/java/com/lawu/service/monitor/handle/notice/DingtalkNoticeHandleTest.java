package com.lawu.service.monitor.handle.notice;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lawu.service.monitor.ApiCheckBootApplication;
import com.lawu.service.monitor.config.CheckConfig;
import com.lawu.service.monitor.result.CheckResult;

/**
 * @author Leach
 * @date 2017/10/17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApiCheckBootApplication.class)
public class DingtalkNoticeHandleTest {

    @Autowired
    @Qualifier("dingtalkNoticeHandle")
    private NoticeHandle dingTalkNoticeHandle;
    @Autowired
    private CheckConfig checkConfig;

    @Ignore
    @Test
    public void send() {
        CheckResult checkResult = new CheckResult();
        checkResult.setResponseMsg("test");
        checkResult.setResponseCode(500);
        checkResult.setResponseTime(100);
        dingTalkNoticeHandle.send(checkResult, checkConfig.getRequests().get(0));
    }
}
