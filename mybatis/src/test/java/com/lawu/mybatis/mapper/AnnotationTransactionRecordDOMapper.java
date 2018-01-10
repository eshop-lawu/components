package com.lawu.mybatis.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.lawu.mybatis.domain.TransactionRecordDO;

@Mapper
public interface AnnotationTransactionRecordDOMapper {
    
    @Insert("INSERT INTO transaction_record (relate_id, type, is_processed, times, gmt_modified, gmt_create) VALUES (#{relateId,jdbcType=BIGINT}, #{type,jdbcType=TINYINT}, #{isProcessed,jdbcType=BIT}, #{times,jdbcType=BIGINT}, #{gmtModified,jdbcType=TIMESTAMP}, #{gmtCreate,jdbcType=TIMESTAMP})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Long.class)
    void insert(TransactionRecordDO record);
    
    @Update("UPDATE transaction_record SET relate_id=#{relateId,jdbcType=BIGINT}, type=#{type,jdbcType=TINYINT}, is_processed=#{isProcessed,jdbcType=BIT}, times=#{times,jdbcType=BIGINT}, gmt_modified=#{gmtModified,jdbcType=TIMESTAMP}, gmt_create=#{gmtCreate,jdbcType=TIMESTAMP}")
    void update(TransactionRecordDO record);
    
}
