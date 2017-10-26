package com.lawu.idworker.client.util;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.lawu.idworker.client.service.IdWorkerService;

/**
 * @author Leach
 * @date 2017/10/24
 */
public class IdWorkerHelper {
    
    /**
     * 远程访问服务
     */
    private static IdWorkerService idWorkerService;
    
    /**
     * 用于缓存编号的的队列
     */
    private static Queue<String> quene = new ConcurrentLinkedQueue<>();
    
    private IdWorkerHelper() {
        
    }
    
    /**
     * 
     * @return
     * @author jiangxinjun
     * @date 2017年10月25日
     */
    public static String generate() {
        if (quene.isEmpty()) {
            quene.addAll(getIdWorkerService().batchGenerate());
        }
        // 考虑并发
        String rtn = quene.poll();
        if (rtn != null) {
            return rtn;
        }
        quene.addAll(getIdWorkerService().batchGenerate());
        return quene.poll();
    }

    public static IdWorkerService getIdWorkerService() {
        if (idWorkerService == null) {
            idWorkerService = SpringTool.getApplicationContext().getBean(IdWorkerService.class);
        }
        return idWorkerService;
    }

}
