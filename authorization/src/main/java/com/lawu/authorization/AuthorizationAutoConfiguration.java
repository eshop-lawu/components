package com.lawu.authorization;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.lawu.authorization.interceptor.AuthorizationInterceptor;
import com.lawu.authorization.manager.TokenCacheService;
import com.lawu.authorization.manager.impl.CacheTokenManager;

/**
 * Authorization自动配置类
 * 
 * @author jiangxinjun
 * @createDate 2018年1月31日
 * @updateDate 2018年1月31日
 */
@Configuration
@ConditionalOnProperty(value = "com.lawu.authorization.token-manager.enabled", havingValue = "true", matchIfMissing = false)
@ConditionalOnBean(TokenCacheService.class)
public class AuthorizationAutoConfiguration extends WebMvcConfigurerAdapter {
    
    @ConfigurationProperties(prefix = "com.lawu.authorization.token-manager")
    @Bean
    public CacheTokenManager tokenManager() {
        return new CacheTokenManager();
    }
    
    @ConfigurationProperties(prefix = "com.lawu.authorization.interceptor")
    @Bean
    public AuthorizationInterceptor authorizationInterceptor() {
        return new AuthorizationInterceptor();
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
