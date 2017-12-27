package com.lawu.compensating.transaction.service;

/**
 * 
 * @author Sunny
 * @date 2017年5月18日
 */
public interface CacheService {

	/**
	 * 获取次数
	 * 
	 * @param type
	 * @return
	 * @author Sunny
	 */
	Long getCount(String type);

	/**
	 * 添加次数
	 * 
	 * @param type
	 * @author Sunny
	 */
	void addCount(String type);
}
