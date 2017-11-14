package com.lawu.jobsextend;

import java.util.List;

import com.dangdang.ddframe.job.api.simple.SimpleJob;

/**
 * @author Leach
 * @date 2017/11/14
 */
public interface BasePageJob<T> extends SimpleJob {

    /**
     * 查询数据分页
     * 每次查询数据时加载第一页即可，另外，为了使得旧数据可以优先处理，查询时建议按时间或自增id正序排列
     * @param offset   数据偏移量
     * @param pageSize 每页数量
     * @return
     */
    List<T> queryPage(int offset, int pageSize);
}