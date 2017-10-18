package com.lawu.service.monitor.handle;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lawu.service.monitor.ApiCheckBootApplication;

/**
 * @author Leach
 * @date 2017/10/17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApiCheckBootApplication.class)
public class HttpCheckHandleTest {

    @Autowired
    private HttpCheckHandle httpCheckHandle;

    @Ignore
    @Test
    public void checkBatch() {
        httpCheckHandle.checkBatch();
    }
}
