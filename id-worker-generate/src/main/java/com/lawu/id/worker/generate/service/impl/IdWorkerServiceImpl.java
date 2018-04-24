package com.lawu.id.worker.generate.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lawu.id.worker.generate.IdWorker;
import com.lawu.id.worker.generate.config.IdWorkerConfig;
import com.lawu.id.worker.generate.service.IdWorkerService;

/**
 * 
 * @author jiangxinjun
 * @date 2017年10月24日
 */
public class IdWorkerServiceImpl implements IdWorkerService {
    
    @Autowired
    private IdWorkerConfig idWorkerConfig;
    
    @Autowired
    private IdWorker idWorker;
    
    public String generate() {
        return idWorker.generate();
    }
    
    public List<String> batchGenerate() {
        List<String> rtn = new ArrayList<>();
        for (int i = 0; i < idWorkerConfig.getMaximum(); i++) {
            rtn.add(generate());
        }
        return rtn;
    }
    
}
