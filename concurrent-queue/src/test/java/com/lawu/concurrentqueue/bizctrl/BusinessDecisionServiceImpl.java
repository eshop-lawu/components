package com.lawu.concurrentqueue.bizctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.concurrentqueue.base.Result;
import com.lawu.concurrentqueue.domain.SeckillActivityProductDO;
import com.lawu.concurrentqueue.mapper.SeckillActivityProductDOMapper;

/**
 * @author Leach
 * @date 2017/11/29
 */
@SuppressWarnings("rawtypes")
@Service
public class BusinessDecisionServiceImpl extends AbstractBusinessDecisionService<Result> {
    
    @Autowired
    private SeckillActivityProductDOMapper seckillActivityProductDOMapper;
    
    @Override
    public Integer queryInventory(Object id) {
        SeckillActivityProductDO seckillActivityProductDO = seckillActivityProductDOMapper.selectByPrimaryKey((Long) id);
        return seckillActivityProductDO.getLeftCount();
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
