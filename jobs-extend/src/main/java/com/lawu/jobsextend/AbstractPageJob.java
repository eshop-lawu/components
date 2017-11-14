package com.lawu.jobsextend;

import java.util.List;

/**
 *
 * 用于单独模块中不包含事务的定时任务，可以细粒化对每条数据自定义处理
 *
 * @author Leach
 * @date 2017/11/6
 */
public abstract class AbstractPageJob<T> extends AbstractWholePageJob<T> {

    /**
     * 针对一条记录执行任务
     * @param entity
     */
    abstract public void executeSingle(T entity);

    @Override
    public void executePage(List<T> dataPage) throws JobsExtendPageException {
        for (int i = 0 ; i < dataPage.size(); i++) {
            T entity = dataPage.get(i);
            try {
                executeSingle(entity);
            } catch (Exception e) {
                throw new JobsExtendPageException(e, i);
            }
        }
    }
}
