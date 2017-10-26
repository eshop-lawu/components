package com.lawu.idworker.client.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Leach
 * @date 2017/10/11
 */
@FeignClient(value = "id-worker", path = "idWorker/")
public interface IdWorkerService {
    
    /**
     * 自动生成18位编号
     * @return
     * @author jiangxinjun
     * @date 2017年10月24日
     */
    @RequestMapping(value = "generate", method = RequestMethod.GET)
    String generate();
    
    /**
     * 批量生成自动生成单个18位编号
     * @return
     * @author jiangxinjun
     * @date 2017年10月24日
     */
    @RequestMapping(value = "batchGenerate", method = RequestMethod.GET)
    List<String> batchGenerate();
}
