package com.lawu.jobsextend;

import java.util.List;

/**
 * @author Leach
 * @date 2017/11/6
 */
public interface PageJob<T> extends BasePageJob<T> {

    /**
     * 针对每一页数据执行任务，防止资源一次性占用过多
     * @param dataPage 当前页数据
     * @throws JobsExtendPageException
     */
    void executePage(List<T> dataPage) throws JobsExtendPageException;
}
