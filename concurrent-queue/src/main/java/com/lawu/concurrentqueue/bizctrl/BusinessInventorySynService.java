package com.lawu.concurrentqueue.bizctrl;

/**
 * @author Leach
 * @date 2017/11/28
 */
public interface BusinessInventorySynService {

    /**
     * 同步减库存
     *
     * @param businessDecisionService
     * @param businessKey
     * @param id
     * @return
     */
    InventoryResult decreaseInventory(BusinessDecisionService businessDecisionService, String businessKey, Object id);

    /**
     * 更新库存
     *
     * @param businessDecisionService
     * @param businessKey
     * @param id
     */
    void updateInventory(BusinessDecisionService businessDecisionService, String businessKey, Object id);

}
