package com.lawu.compensating.transaction.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.compensating.transaction.TransactionStatusService;
import com.lawu.compensating.transaction.bo.TransactionRecordBO;
import com.lawu.compensating.transaction.domain.TransactionRecordDO;
import com.lawu.compensating.transaction.domain.TransactionRecordDOExample;
import com.lawu.compensating.transaction.mapper.TransactionRecordDOMapper;
import com.lawu.compensating.transaction.properties.TransactionProperties;

/**
 * @author Leach
 * @date 2017/3/29
 */
public class TransactionStatusServiceImpl implements TransactionStatusService {

	@Autowired
	private TransactionRecordDOMapper transactionRecordDOMapper;
	
    @Autowired
    private TransactionProperties transactionProperties;

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
		return transactionRecord.getId();
	}

	@Transactional
	@Override
	public Long success(Long transactionId) {
		TransactionRecordDO transactionRecord = transactionRecordDOMapper.selectByPrimaryKey(transactionId);
		if (transactionRecord == null) {
			throw new RuntimeException("transactionId is null");
		}
		// 是否处理完成
		if (transactionRecord.getIsProcessed()) {
			return null;
		}
		
		TransactionRecordDO transactionRecordUpdateDO = new TransactionRecordDO();
		transactionRecordUpdateDO.setId(transactionRecord.getId());
		transactionRecordUpdateDO.setIsProcessed(true);
		transactionRecordUpdateDO.setGmtModified(new Date());
		transactionRecordDOMapper.updateByPrimaryKeySelective(transactionRecordUpdateDO);
		
		// 删除指定时间之前已经处理的主事务消息
		TransactionRecordDOExample example = new TransactionRecordDOExample();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR,  - transactionProperties.getDeleteRecordTime());
		example.createCriteria().andIsProcessedEqualTo(true).andGmtCreateLessThanOrEqualTo(calendar.getTime());
		transactionRecordDOMapper.deleteByExample(example);
		
		return transactionRecord.getRelateId();
	}

	@Override
	public List<TransactionRecordBO> selectNotDoneList(byte type, Long exectotalCount) {
		TransactionRecordDOExample example = new TransactionRecordDOExample();
		example.createCriteria().andTypeEqualTo(type).andIsProcessedEqualTo(false).andTimesLessThan(exectotalCount);
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
