package com.lawu.id.worker.generate;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lawu.id.worker.generate.service.IdWorkerService;

/**
 * IdWorker接口实现类单元测试
 * 
 * @author jiangxinjun
 * @createDate 2018年4月24日
 * @updateDate 2018年4月24日
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IdWorkerServiceImplTest {
    
    @Autowired
    private IdWorkerService idWorkerService;
    
    @Test
    public void generate() {
        String num = idWorkerService.generate();
        Assert.assertNotNull(num);
    }
    
    @Test
    public void batchGenerate() {
        List<String> nums = idWorkerService.batchGenerate();
        List<String> newnums = Lists.newArrayList();
        for (String num : nums) {
            Assert.assertNotNull(num);
            Assert.assertFalse(newnums.contains(num));
            newnums.add(num);
        }
    }
}
