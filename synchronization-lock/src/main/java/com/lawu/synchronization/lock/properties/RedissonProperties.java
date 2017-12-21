package com.lawu.synchronization.lock.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Redisson属性类
 * 
 * @author jiangxinjun
 * @createDate 2017年12月20日
 * @updateDate 2017年12月20日
 */
@ConfigurationProperties(prefix = "lawu.synchronization-lock.redisson")
public class RedissonProperties {
    
    /**
     * 地址
     */
    private String address;
    
    /**
     * 密码
     */
    private String password;
    
    /**
     * 连接池大小
     */
    private Integer connectionPoolSize;
    
    /**
     * 集群地址
     */
    private List<String> nodeAddresses;
    
    /**
     * 锁配置
     */
    private Lock lock = new Lock();

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getConnectionPoolSize() {
        return connectionPoolSize;
    }

    public void setConnectionPoolSize(Integer connectionPoolSize) {
        this.connectionPoolSize = connectionPoolSize;
    }
    
    public List<String> getNodeAddresses() {
        return nodeAddresses;
    }

    public void setNodeAddresses(List<String> nodeAddresses) {
        this.nodeAddresses = nodeAddresses;
    }

    public Lock getLock() {
        return lock;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }

    /**
     * Lock属性类
     * 
     * @author jiangxinjun
     * @createDate 2017年12月20日
     * @updateDate 2017年12月20日
     */
    public class Lock {
        
        /**
         * 获取锁的等待时间
         */
        private long waitTime;
        
        /**
         * 锁的释放时间
         */
        private long leaseTime;

        public long getWaitTime() {
            return waitTime;
        }

        public void setWaitTime(long waitTime) {
            this.waitTime = waitTime;
        }

        public long getLeaseTime() {
            return leaseTime;
        }

        public void setLeaseTime(long leaseTime) {
            this.leaseTime = leaseTime;
        }
    }
}