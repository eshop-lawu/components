package com.lawu.compensating.transaction.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.compensating.transaction.TransactionStatusService;
import com.lawu.compensating.transaction.bo.TransactionRecordBO;
import com.lawu.compensating.transaction.domain.TransactionRecordDO;
import com.lawu.compensating.transaction.domain.TransactionRecordDOExample;
import com.lawu.compensating.transaction.mapper.TransactionRecordDOMapper;

/**
 * @author Leach
 * @date 2017/3/29
 */
public class TransactionStatusServiceImpl implements TransactionStatusService {

	@Autowired
	private TransactionRecordDOMapper transactionRecordDOMapper;

	@Transactional
	@Override
	public Long save(Long relateId, byte type) {
	    // 数据的唯一性由
		TransactionRecordDO transactionRecord = new TransactionRecordDO();
		transactionRecord.setIsProcessed(false);
		transactionRecord.setRelateId(relateId);
		transactionRecord.setType(type);
		transactionRecord.setGmtModified(new Date());
		transactionRecord.setGmtCreate(new Date());
		transactionRecord.setTimes(0L);
		transactionRecordDOMapper.insertSelective(transactionRecord);
		/*
        // 查找数据库是否有相同记录存在
        TransactionRecordDOExample example = new TransactionRecordDOExample();
        example.createCriteria().andTypeEqualTo(type).andRelateIdEqualTo(relateId);
        List<TransactionRecordDO> list = transactionRecordDOMapper.selectByExample(example);
        if (list == null || list.isEmpty()) {
            // 如果记录存在，保存一条新的记录
            transactionRecord.setIsProcessed(false);
            transactionRecord.setRelateId(relateId);
            transactionRecord.setType(type);
            transactionRecord.setGmtModified(new Date());
            transactionRecord.setGmtCreate(new Date());
            transactionRecord.setTimes(0L);
            transactionRecordDOMapper.insertSelective(transactionRecord);
        } else {
            // 如果记录存在，更新以前的旧记录
            transactionRecord.setId(list.get(0).getId());
            transactionRecord.setIsProcessed(false);
            transactionRecord.setTimes(0L);
            transactionRecord.setGmtModified(new Date());
            transactionRecordDOMapper.updateByPrimaryKeySelective(transactionRecord);
        }
        */
		return transactionRecord.getId();
	}

	@Transactional
	@Override
	public Long success(Long transactionId) {
		TransactionRecordDO transactionRecord = transactionRecordDOMapper.selectByPrimaryKey(transactionId);
		if (transactionRecord == null) {
			return null;
		}
		// 是否处理完成
		if (transactionRecord.getIsProcessed()) {
			return null;
		}
		transactionRecord.setIsProcessed(true);
		transactionRecord.setGmtModified(new Date());
		transactionRecordDOMapper.updateByPrimaryKey(transactionRecord);
		return transactionRecord.getRelateId();
	}

	@Override
	public List<TransactionRecordBO> selectNotDoneList(byte type) {
		TransactionRecordDOExample example = new TransactionRecordDOExample();
		example.createCriteria().andTypeEqualTo(type).andIsProcessedEqualTo(false);
		List<TransactionRecordDO> transactionRecordDOS = transactionRecordDOMapper.selectByExample(example);
		List<TransactionRecordBO> notDoneList = new ArrayList<>();
		for (int i = 0; i < transactionRecordDOS.size(); i++) {
			TransactionRecordDO transactionRecordDO = transactionRecordDOS.get(i);

			TransactionRecordBO transactionRecordBO = new TransactionRecordBO();
			transactionRecordBO.setId(transactionRecordDO.getId());
			transactionRecordBO.setRelateId(transactionRecordDO.getRelateId());
			transactionRecordBO.setTimes(transactionRecordDO.getTimes());
			notDoneList.add(transactionRecordBO);
		}
		return notDoneList;
	}

	@Transactional
	@Override
	public void updateTimes(Long transactionId, Long times) {
		TransactionRecordDO record = new TransactionRecordDO();
		record.setId(transactionId);
		record.setTimes(times);
		record.setGmtModified(new Date());
		transactionRecordDOMapper.updateByPrimaryKeySelective(record);
	}
}
