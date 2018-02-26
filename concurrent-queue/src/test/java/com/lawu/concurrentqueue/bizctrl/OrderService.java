package com.lawu.concurrentqueue.bizctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.concurrentqueue.base.Result;
import com.lawu.concurrentqueue.bizctrl.annotation.BusinessInventoryCtrl;
import com.lawu.concurrentqueue.mapper.extend.SeckillActivityProductDOExtendMapper;

/**
 * @author Leach
 * @date 2017/11/30
 */
@Service
public class OrderService {
    
    @Autowired
    private SeckillActivityProductDOExtendMapper seckillActivityProductDOExtendMapper;
    
    @Transactional(rollbackFor = Exception.class)
    @SuppressWarnings("rawtypes")
    @BusinessInventoryCtrl(idParamIndex = 0, businessKey = "SECKILL_ACTIVITY_PRODUCT", using = BusinessDecisionServiceImpl.class, isLock = true)
    public Result createOrder(Long id) {
        Result rs = new Result();
        int affectedRows = seckillActivityProductDOExtendMapper.subtractInventory((long) id, 1);
        if (affectedRows != 0) {
            System.out.println("Create order: " + id);
            rs.setRet(1);
        } else {
            throw new BusinessExecuteException(2, "");
        }
        return rs;
    }
}
