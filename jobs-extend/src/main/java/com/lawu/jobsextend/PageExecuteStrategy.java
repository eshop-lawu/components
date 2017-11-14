package com.lawu.jobsextend;

import java.util.List;

/**
 * @author Leach
 * @date 2017/11/14
 */
public interface PageExecuteStrategy<T> {

    /**
     * 整页数据处理策略，考虑失败情况
     * @param dataPage
     * @return 是否需要继续执行下一页
     */
    boolean executePageConsiderFail(List<T> dataPage);
}
