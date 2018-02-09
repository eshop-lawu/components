package com.lawu.compensating.transaction.mapper.extend;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

/**
 * 主事务表扩展Mapper
 * 
 * @author jiangxinjun
 * @createDate 2018年2月8日
 * @updateDate 2018年2月8日
 */
public interface TransactionRecordDOExtendMapper {

    /**
     * 根据时间点物理删除这个时间点一个月之前的事务记录
     * @param deleteRecordDate 时间点
     * @author jiangxinjun
     * @createDate 2018年2月8日
     * @updateDate 2018年2月8日
     */
    int deleteExpiredRecords(@Param("deleteRecordDate") Date deleteRecordDate);
}