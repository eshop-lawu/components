package com.lawu.idworker.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "number-generation.id-worker")
public class IdWorkerConfig {
	
    /**
     * 派发器ID
     */
    private Long workerId;
    
    /**
     * 数据中心ID
     */
    private Long dataCenterId;
    
    /**
     * 批量生成编号数量
     */
    private Integer maximum;

    public Long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Long workerId) {
        this.workerId = workerId;
    }

    public Long getDataCenterId() {
        return dataCenterId;
    }

    public void setDataCenterId(Long dataCenterId) {
        this.dataCenterId = dataCenterId;
    }

    public Integer getMaximum() {
        return maximum;
    }

    public void setMaximum(Integer maximum) {
        this.maximum = maximum;
    }
    
}
