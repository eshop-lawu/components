package com.lawu.compensating.transaction.service;

import java.util.Date;
import java.util.List;

import com.lawu.compensating.transaction.bo.TransactionRecordBO;

/**
 * 事务状态服务类
 * @author Leach
 * @date 2017/3/29
 */
public interface TransactionStatusService {

    /**
     * 保存事务状态信息
     * @param relateId
     * @param type
     * @return
     */
    Long save(Long relateId, byte type);

    /**
     * 事务处理成功
     *
     * @param transactionId
     * @return
     */
    Long success(Long transactionId);

    /**
     * 查出指定类型的所有未处理事务
     *
     * @param type
     * @return
     */
    List<TransactionRecordBO> selectNotDoneList(byte type, Long exectotalCount);
    
    /**
     * 更新事务记录的执行次数
     * 
     * @param transactionId	事务记录id
     * @param times 当前执行次数
     * @author Sunny
     */
    void updateTimes(Long transactionId, Long times);
    
    /**
     * 删除过期数据
     * 改为定时任务执行,因为每次消费都去执行,增加数据库的压力
     * @author jiangxinjun
     * @createDate 2018年2月8日
     * @updateDate 2018年2月8日
     */
    void deleteExpiredRecords();
    
    /**
     * 根据给定日期删除事务记录
     * @param deleteRecordDate
     * @return
     * @author jiangxinjun
     * @createDate 2018年2月8日
     * @updateDate 2018年2月8日
     */
    //int deleteExpiredRecords(Date deleteRecordDate);
}
