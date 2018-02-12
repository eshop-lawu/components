package com.lawu.compensating.transaction.service;


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
    void receiveCallback(R reply, long storeTimestamp);

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
    
    /**
     * 执行接收到的回复消息
     * 
     * @author jiangxinjun
     * @createDate 2018年2月11日
     * @updateDate 2018年2月11日
     */
    void executeCallback(R reply);
}
