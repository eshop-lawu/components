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
    
    public static void setIdWorkerService(IdWorkerService idWorkerService) {
        IdWorkerHelper.idWorkerService = idWorkerService;
    }
    
    /**
     * 
     * @return
     * @author jiangxinjun
     * @createDate 2017年10月25日
     * @updateDate 2018年4月13日
     */
    public static String generate() {
        // 两次判断
        if (quene.isEmpty()) {
            synchronized (quene) {
                if (quene.isEmpty()) {
                    quene.addAll(idWorkerService.batchGenerate());
                }
            }
        }
        // 考虑并发
        String rtn = quene.poll();
        if (rtn != null) {
            return rtn;
        }
        return generate();
    }
}
