package com.lawu.concurrentqueue.bizctrl;

/**
 * 实际业务逻辑处理服务接口
 * @author Leach
 * @date 2017/11/28
 */
public interface BusinessDecisionService<T> {

    /**
     * 查询抢购/秒杀类型业务剩余库存量
     *
     * @retur
     */
    Integer queryInventory(Object id);

    /**
     * 获取同步对象
     * @param id
     * @return
     */
    Object getSynObj(Object id);

    /**
     * 已抢完
     *
     * @return
     */
    T sellOut();

    /**
     * 失败
     * @return
     */
    T fail(BusinessExecuteException e);

}
