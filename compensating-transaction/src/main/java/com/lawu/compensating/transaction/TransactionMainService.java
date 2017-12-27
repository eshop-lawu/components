package com.lawu.compensating.transaction;


/**
 * 补偿性事务主逻辑接口
 *
 * @author Leach
 * @date 2017/3/28
 */
public interface TransactionMainService<R> {

    /**
     * 通知其它模块进行补偿性事务处理
     *
     */

	void sendNotice(Long relateId);
    /**
     * 补偿成功回调
     *
     * @param reply
     */
    void receiveCallback(R reply);

    /**
     * 检查需要补偿的数据
     *
     * @return
     */
    void check(Long count);
    
    /**
     * 获取当前类注解CompensatingTransactionMain的Topic的属性
     * 
     * @return
     * @author Sunny
     */
    String getTopic();
}
