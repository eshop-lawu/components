package com.lawu.concurrentqueue.bizctrl;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.lawu.concurrentqueue.bizctrl.annotation.BusinessInventoryCtrl;
import com.lawu.synchronization.lock.service.LockService;

/**
 * 业务量控制切面
 * @author Leach
 * @date 2017/11/28
 */
public class BusinessDecisionAspect implements ApplicationContextAware {

    /**
     * 业务并发锁
     */
    public static final String LOCK_KEY = "BUSINESS_DECISION_LOCK_";
    
    private ApplicationContext applicationContext;
    
    @Autowired
    private BusinessInventorySynService businessInventorySynService;
    
    @Autowired
    private LockService lockService;

    public Object aroundMethod(ProceedingJoinPoint point) {

        MethodSignature signature = (MethodSignature)point.getSignature();
        Method targetMethod = signature.getMethod();
        BusinessInventoryCtrl businessInventoryCtrl = targetMethod.getAnnotation(BusinessInventoryCtrl.class);

        int idParamIndex = businessInventoryCtrl.idParamIndex();
        String businessKey = businessInventoryCtrl.businessKey();
        boolean isLock = businessInventoryCtrl.isLock();
        BusinessDecisionService businessDecisionService = applicationContext.getBean(businessInventoryCtrl.using());

        Object businessId = point.getArgs()[idParamIndex];

        // 缓存库存减一
        boolean isSuccess = businessInventorySynService.decreaseInventory(businessDecisionService, businessKey, businessId);

        if (!isSuccess) {
            return businessDecisionService.sellOut();
        }
        
        String lockKey = LOCK_KEY.concat(businessKey).concat("_").concat(businessId.toString());
        
        if (isLock) {
            boolean lock = lockService.tryLock(1000, 5000, lockKey);
            if (!lock) {
                return businessDecisionService.fail(new BusinessExecuteException());
            }
        }
        
        try {
            // 执行目标方法
            return point.proceed();
        } catch (BusinessExecuteException e) {
            // 异常时缓存库存加一
            businessInventorySynService.increaseInventory(businessDecisionService, businessKey, businessId);
            return businessDecisionService.fail(e);
        } catch (Throwable e) {
            // 异常时缓存库存加一
            businessInventorySynService.increaseInventory(businessDecisionService, businessKey, businessId);
            return businessDecisionService.fail(new BusinessExecuteException(e));
        } finally {
            if (isLock) {
                lockService.unLock(lockKey);
            }
        }
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
