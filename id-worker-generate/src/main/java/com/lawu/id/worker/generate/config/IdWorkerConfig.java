package com.lawu.id.worker.generate.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "lawu.id-worker-generate")
public class IdWorkerConfig {
	
    /**
     * 派发器ID
     */
    private long workerId = 0L;
    
    /**
     * 数据中心ID
     */
    private long dataCenterId = 0L;
    
    /**
     * 批量生成编号数量
     */
    private int maximum = 200;

    public long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(long workerId) {
        this.workerId = workerId;
    }

    public long getDataCenterId() {
        return dataCenterId;
    }

    public void setDataCenterId(long dataCenterId) {
        this.dataCenterId = dataCenterId;
    }

    public int getMaximum() {
        return maximum;
    }

    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }
    
}
