package com.lawu.service.monitor;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lawu.service.monitor.config.CheckConfig;
import com.lawu.service.monitor.config.api.HttpRequestConfig;
import com.lawu.service.monitor.config.notice.NoticeConfigs;

/**
 * @author Leach
 * @date 2017/10/17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApiCheckBootApplication.class)
public class CheckConfigTest {
    @Autowired
    private CheckConfig checkConfig;

    @Test
    public void getRequests() {
        List<HttpRequestConfig> requests = checkConfig.getRequests();
        Assert.assertNotNull(requests);
        Assert.assertFalse(requests.isEmpty());

        NoticeConfigs notices = checkConfig.getNotices();
        Assert.assertNotNull(notices);
    }
}
