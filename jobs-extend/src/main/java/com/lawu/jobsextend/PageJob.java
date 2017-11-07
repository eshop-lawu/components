package com.lawu.jobsextend;

import java.util.List;

import com.dangdang.ddframe.job.api.simple.SimpleJob;

/**
 * @author Leach
 * @date 2017/11/6
 */
public interface PageJob<T> extends SimpleJob {

    /**
     * 查询数据分页
     * @param currentPage 当前页面（从1开始）
     * @param pageSize 每页数量
     * @return
     */
    List<T> queryPage(int currentPage, int pageSize);

    /**
     * 针对每一页数据执行任务
     * @param dataPage 当前页数据
     */
    void executePage(List<T> dataPage);
}
