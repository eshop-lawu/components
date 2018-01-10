package com.lawu.mybatis.service;

import com.lawu.mybatis.domain.TransactionRecordDO;

public interface AnnotationTransactionRecordService {
    
    void insert(TransactionRecordDO record);
    
    void update(TransactionRecordDO record);
    
}
