package com.lawu.jobsextend;

import java.util.List;

/**
 * @author Leach
 * @date 2017/11/14
 */
public interface PageResultJob<T, R> extends BasePageJob<T> {

    /**
     * 针对每一页数据执行任务
     * @param dataPage 当前页数据
     * @return
     */
    R executePage(List<T> dataPage);

    /**
     * 汇总处理
     * @param pageResults 每一页数据处理的结果集合
     */
    void executeSummary(List<R> pageResults);
}
