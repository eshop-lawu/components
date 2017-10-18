package com.lawu.service.monitor.handle.api;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lawu.service.monitor.ApiCheckBootApplication;
import com.lawu.service.monitor.config.CheckConfig;
import com.lawu.service.monitor.handle.api.HttpRequestHandle;

/**
 * @author Leach
 * @date 2017/10/17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApiCheckBootApplication.class)
public class GetHttpRequestHandleTest {
    @Autowired
    private CheckConfig checkConfig;

    @Autowired
    @Qualifier("getHttpRequestHandle")
    private HttpRequestHandle getHttpRequestHandle;

    @Ignore
    @Test
    public void checkBatch() {
        getHttpRequestHandle.execute(checkConfig.getRequests().get(0));
    }
}
