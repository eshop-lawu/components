package com.lawu.synchronization.lock;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.lawu.synchronization.lock.properties.RedissonProperties;

/**
 * Redisson自动配置装载类
 * 
 * @author jiangxinjun
 * @createDate 2017年12月20日
 * @updateDate 2017年12月20日
 */
@Configuration
@EnableConfigurationProperties({ RedissonProperties.class })
public class RedissonAutoConfiguration {
    
    @ConditionalOnProperty(name = {"lawu.synchronization-lock.redisson.enabled"}, havingValue="true", matchIfMissing = false)
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient(RedissonProperties properties) {
        Config config = new Config();
        if (StringUtils.isEmpty(properties.getAddress())) {
            ClusterServersConfig clusterServersConfig = config.useClusterServers();
            clusterServersConfig.setScanInterval(2000); // cluster state scan interval in milliseconds
            clusterServersConfig.addNodeAddress(properties.getNodeAddresses().toArray(new String[properties.getNodeAddresses().size()]));
            if (properties.getConnectionPoolSize() != null) {
                clusterServersConfig.setMasterConnectionPoolSize(properties.getConnectionPoolSize());
                clusterServersConfig.setSlaveConnectionPoolSize(properties.getConnectionPoolSize());
            }
        } else {
            // 只设置地址跟密码，其他配置默认
            SingleServerConfig singleServerConfig = config.useSingleServer();
            singleServerConfig.setAddress(properties.getAddress());
            if (properties.getConnectionPoolSize() != null) {
                singleServerConfig.setConnectionPoolSize(properties.getConnectionPoolSize());
            }
            if (!StringUtils.isEmpty(properties.getPassword())) {
                singleServerConfig.setPassword(properties.getPassword());
            }
        }
        return Redisson.create(config);
    }
}