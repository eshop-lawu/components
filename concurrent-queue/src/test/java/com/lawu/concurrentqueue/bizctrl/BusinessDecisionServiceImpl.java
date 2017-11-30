package com.lawu.concurrentqueue.bizctrl;

import org.springframework.stereotype.Service;

import com.lawu.concurrentqueue.base.Result;

/**
 * @author Leach
 * @date 2017/11/29
 */
@Service
public class BusinessDecisionServiceImpl implements BusinessDecisionService<Result> {

    @Override
    public Integer queryInventory(Object id) {
        return 10;
    }

    @Override
    public Result sellOut() {
        Result rs = new Result();
        rs.setRet(2);
        return rs;
    }

    @Override
    public Result fail(BusinessExecuteException e) {
        Result rs = new Result();
        rs.setRet(e.getRet());
        return rs;
    }

}
