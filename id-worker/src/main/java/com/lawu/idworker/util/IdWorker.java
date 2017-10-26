package com.lawu.idworker.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author jiangxinjun
 * @date 2017年10月24日
 */
public class IdWorker {

    private static final  long TWEPOCH = 1288834974657L;
    private static final  long WORKER_ID_BITS = 5L;
    private static final  long DATACENTER_ID_BITS = 5L;
    private static final long MAX_WORKER_ID = -1L ^ (-1L << WORKER_ID_BITS);
    private static final long MAX_DATACENTER_ID = -1L ^ (-1L << DATACENTER_ID_BITS);
    private static final  long SEQUENCE_BITS = 12L;
    private static final  long WORKER_ID_SHIFT = SEQUENCE_BITS;
    private static final  long DATACENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    private static final  long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATACENTER_ID_BITS;
    private static final  long SEQUENCE_MASK = -1L ^ (-1L << SEQUENCE_BITS);

    private long workerId;
    private long datacenterId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;
    
    private int maximum = 200;
    
    public IdWorker(long workerId, long datacenterId, Integer maximum) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", MAX_WORKER_ID));
        }
        if (datacenterId > MAX_DATACENTER_ID || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", MAX_DATACENTER_ID));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
        if (maximum != null) {
            this.maximum = maximum;
        }
    }
    
    public IdWorker(long workerId, long datacenterId) {
        this(workerId, datacenterId, null);
    }
    
    public String generate() {
        return String.valueOf(nextId());
    }
    
    public List<String> batchGenerate() {
        List<String> rtn = new ArrayList<>();
        for (int i = 0; i < maximum; i++) {
            rtn.add(generate());
        }
        return rtn;
    }
    
    private synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - TWEPOCH) << TIMESTAMP_LEFT_SHIFT) | (datacenterId << DATACENTER_ID_SHIFT) | (workerId << WORKER_ID_SHIFT) | sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

}
