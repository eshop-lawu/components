package com.lawu.idworker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.id.worker.generate.service.IdWorkerService;

/**
 * 
 * @author jiangxinjun
 * @date 2017年10月24日
 */
@RestController
@RequestMapping(value = "idWorker/")
public class IdWorkerController {
    
    @Autowired
    private IdWorkerService idWorkerService;
    
    /**
     * 自动生成单个18位编号
     * @return
     * @author jiangxinjun
     * @date 2017年10月24日
     */
    @RequestMapping(value = "generate", method = RequestMethod.GET)
    public String generate() {
        return idWorkerService.generate();
    }
    
    /**
     * 批量生成自动生成单个18位编号
     * @return
     * @author jiangxinjun
     * @date 2017年10月24日
     */
    @RequestMapping(value = "batchGenerate", method = RequestMethod.GET)
    public List<String> batchGenerate() {
        return idWorkerService.batchGenerate();
    }
}
