package com.lawu.idworker.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.idworker.service.IdWorkerService;
import com.lawu.idworker.util.IdWorker;

/**
 * 
 * @author jiangxinjun
 * @date 2017年10月24日
 */
@Service
public class IdWorkerServiceImpl implements IdWorkerService {
    
    @Autowired
    private IdWorker idWorker;
    
    public String generate() {
        return idWorker.generate();
    }
    
    public List<String> batchGenerate() {
        return idWorker.batchGenerate();
    }
    
}
