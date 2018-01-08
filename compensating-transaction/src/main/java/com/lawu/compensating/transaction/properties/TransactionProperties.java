package com.lawu.compensating.transaction.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 事务补偿属性类
 * 
 * @author jiangxinjun
 * @createDate 2018年1月8日
 * @updateDate 2018年1月8日
 */
@ConfigurationProperties(prefix = "lawu.compensating-transaction")
public class TransactionProperties {
    
    /**
     * MQ消息有效时间(单位:毫秒)
     */
    private long messageValidTime = 60 * 1000L;
    
    private TransactionJob job = new TransactionJob();

    public long getMessageValidTime() {
        return messageValidTime;
    }

    public void setMessageValidTime(long messageValidTime) {
        this.messageValidTime = messageValidTime;
    }
    
    public TransactionJob getJob() {
        return job;
    }

    public void setJob(TransactionJob job) {
        this.job = job;
    }

    public static class TransactionJob {
        
        /**
         * 间隔基数（次数）
         */
        private Long intervalBaseNumber;
        
        /**
         * 执行最大次数
         */
        private Long exectotalCount;
        
        /**
         * MQ消息有效时间(单位:毫秒)
         */
        private long messageValidTime = 60 * 1000L;

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

        public long getMessageValidTime() {
            return messageValidTime;
        }

        public void setMessageValidTime(long messageValidTime) {
            this.messageValidTime = messageValidTime;
        }
        
    }
}