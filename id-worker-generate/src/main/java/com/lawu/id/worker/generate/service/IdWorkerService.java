package com.lawu.id.worker.generate.service;

import java.util.List;

/**
 * 
 * @author jiangxinjun
 * @date 2017年10月24日
 */
public interface IdWorkerService {
    
    String generate();
    
    List<String> batchGenerate();
    
}
