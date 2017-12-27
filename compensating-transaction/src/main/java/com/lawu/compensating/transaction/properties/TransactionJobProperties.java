package com.lawu.compensating.transaction.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 事务补偿定时任务属性类
 * 
 * @author jiangxinjun
 * @createDate 2017年12月20日
 * @updateDate 2017年12月20日
 */
@ConfigurationProperties(prefix = "lawu.compensating-transaction.job")
public class TransactionJobProperties {
    
    /**
     * 间隔基数（次数）
     */
    private Long intervalBaseNumber;
    
    /**
     * 执行最大次数
     */
    private Long exectotalCount;

    public Long getIntervalBaseNumber() {
        return intervalBaseNumber;
    }

    public void setIntervalBaseNumber(Long intervalBaseNumber) {
        this.intervalBaseNumber = intervalBaseNumber;
    }

    public Long getExectotalCount() {
        return exectotalCount;
    }

    public void setExectotalCount(Long exectotalCount) {
        this.exectotalCount = exectotalCount;
    }
    
}