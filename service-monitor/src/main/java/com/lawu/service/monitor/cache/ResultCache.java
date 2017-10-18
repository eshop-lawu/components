package com.lawu.service.monitor.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Leach
 * @date 2017/10/18
 */
public class ResultCache {

    private static Map<String, Integer> checkFailTimesRecords = new HashMap<>();

    public static int increaseFailTimes(String url) {
        Integer failTimes = checkFailTimesRecords.get(url);
        failTimes = failTimes == null ? 1 : failTimes + 1;
        checkFailTimesRecords.put(url, failTimes);
        return failTimes;
    }

    public static int clearFailTimes(String url) {
        Integer failTimes = checkFailTimesRecords.remove(url);
        return failTimes == null ? 0 : failTimes;
    }

    public static int getFailTimes(String url) {
        return checkFailTimesRecords.get(url);
    }
}
