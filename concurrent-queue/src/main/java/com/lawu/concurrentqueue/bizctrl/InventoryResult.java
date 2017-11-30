package com.lawu.concurrentqueue.bizctrl;

/**
 * @author Leach
 * @date 2017/11/29
 */
public enum InventoryResult {
    // 库存不足
    EMPTY,
    // 最后一件。尚可下单
    LAST_ONE,
    // 库存重复，不止一件
    ENOUGH
}
