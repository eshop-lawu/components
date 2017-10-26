package com.lawu.idworker.client.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * 
 * @author jiangxinjun
 * @date 2017年10月24日
 */
@Service
public class SpringTool implements ApplicationContextAware {
    
    private SpringTool(){
        
    }
    
    private static ApplicationContext context = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return context;
    }

}