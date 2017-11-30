package com.lawu.concurrentqueue.bizctrl;

/**
 * @author Leach
 * @date 2017/11/29
 */
public abstract class AbstractBusinessInventorySynService implements BusinessInventorySynService {

    @Override
    public InventoryResult decreaseInventory(BusinessDecisionService businessDecisionService, String businessKey, Object id) {
        Integer leftAmount = getInventoryFromCache(businessKey, id);
        if (leftAmount == null || leftAmount < 0) {
            updateInventory(businessDecisionService, businessKey, id);
        }
        Integer inventoryAfter = decreaseInventoryToCache(businessKey, id);
        if (inventoryAfter == null || inventoryAfter < 0) {
            return InventoryResult.EMPTY;
        }

        if (inventoryAfter == 0) {
            return InventoryResult.LAST_ONE;
        }

        return InventoryResult.ENOUGH;
    }

    @Override
    public void updateInventory(BusinessDecisionService businessDecisionService, String businessKey, Object id) {
        setInventoryToCache(businessKey, id, businessDecisionService.queryInventory(id));
    }

    /**
     * 从缓存中获取剩余库存
     * @param businessKey
     * @param id
     * @return
     */
    abstract Integer getInventoryFromCache(String businessKey, Object id);

    /**
     * 更新缓存中的库存
     * @param businessKey
     * @param id
     * @param inventory
     */
    abstract void setInventoryToCache(String businessKey, Object id, Integer inventory);

    /**
     * 缓存中的库存量减一
     * @param businessKey
     * @param id
     * @return
     */
    abstract Integer decreaseInventoryToCache(String businessKey, Object id);
}
