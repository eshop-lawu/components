package com.lawu.concurrentqueue.bizctrl;

import org.springframework.stereotype.Service;

import com.lawu.concurrentqueue.base.Result;
import com.lawu.concurrentqueue.bizctrl.annotation.BusinessInventoryCtrl;

/**
 * @author Leach
 * @date 2017/11/30
 */
@Service
public class OrderService {

    @BusinessInventoryCtrl(idParamIndex = 0, businessKey = "BusinessDecisionAspectTest", using = BusinessDecisionServiceImpl.class, isLock = true)
    public Result createOrder(int id) {
        System.out.println("Create order: " + id);
        Result rs = new Result();
        rs.setRet(1);
        return rs;
    }
}
