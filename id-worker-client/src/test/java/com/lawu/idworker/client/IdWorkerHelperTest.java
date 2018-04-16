package com.lawu.idworker.client;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lawu.idworker.client.util.IdWorkerHelper;

/**
 * 
 * @author jiangxinjun
 * @createDate 2018年4月16日
 * @updateDate 2018年4月16日
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
public class IdWorkerHelperTest {
    
    @Test
    public void sendMessage() {
        String orderNum = IdWorkerHelper.generate();
        Assert.assertNotNull(orderNum);
    }
}
