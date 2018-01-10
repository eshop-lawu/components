package com.lawu.mybatis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.mybatis.domain.TransactionRecordDO;
import com.lawu.mybatis.mapper.AnnotationTransactionRecordDOMapper;
import com.lawu.mybatis.service.AnnotationTransactionRecordService;

@Service
public class AnnotationTransactionRecordServiceImpl implements AnnotationTransactionRecordService {

    @Autowired
    private AnnotationTransactionRecordDOMapper annotationTransactionRecordDOMapper;
    
    @Transactional
    @Override
    public void insert(TransactionRecordDO record) {
        annotationTransactionRecordDOMapper.insert(record);
        throw new RuntimeException("insert fail");
    }

    @Transactional
    @Override
    public void update(TransactionRecordDO record) {
        annotationTransactionRecordDOMapper.update(record);
    }

}
