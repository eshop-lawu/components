package com.lawu.concurrentqueue.bizctrl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.lawu.concurrentqueue.bizctrl.annotation.BusinessInventoryCtrl;

/**
 * 业务量控制切面
 * @author Leach
 * @date 2017/11/28
 */
public class BusinessDecisionAspect implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired
    private BusinessInventorySynService businessInventorySynService;

    public Object aroundMethod(ProceedingJoinPoint point) {

        MethodSignature signature = (MethodSignature)point.getSignature();
        Method targetMethod = signature.getMethod();
        BusinessInventoryCtrl businessInventoryCtrl = targetMethod.getAnnotation(BusinessInventoryCtrl.class);

        int idParamIndex = businessInventoryCtrl.idParamIndex();
        String businessKey = businessInventoryCtrl.businessKey();
        BusinessDecisionService businessDecisionService = applicationContext.getBean(businessInventoryCtrl.using());

        Object businessId = point.getArgs()[idParamIndex];

        InventoryResult inventoryResult = businessInventorySynService.decreaseInventory(businessDecisionService, businessKey, businessId);

        if (InventoryResult.EMPTY == inventoryResult) {
            return businessDecisionService.sellOut();
        }

        try {
            // 执行目标方法
            Object result = point.proceed();
            return result;
        } catch (Throwable e) {
            return businessDecisionService.fail();
        } finally {

            if (InventoryResult.LAST_ONE == inventoryResult) {
                businessInventorySynService.updateInventory(businessDecisionService, businessKey, businessId);
            }
        }
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
