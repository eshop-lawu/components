package com.lawu.jobsextend;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dangdang.ddframe.job.api.ShardingContext;

/**
 *
 * 用于单独模块中不包含事务的定时任务，可以细粒化对每条数据自定义处理
 *
 * @author Leach
 * @date 2017/11/6
 */
public abstract class AbstractPageJob<T> extends AbstractCommonPageJob<T> implements PageJob<T> {

    private Logger logger = LoggerFactory.getLogger(AbstractWholePageJob.class);

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
    @Override
    public void execute(ShardingContext shardingContext) {

        logger.debug("------PageJob-start: {}, jobParameter: {}------", shardingContext.getJobName(), shardingContext.getJobParameter());

        PageCircuitStrategy pageCircuitStrategy = createPageCircuitStrategy();

        while (pageCircuitStrategy.hasNext()) {

            pageCircuitStrategy.next();

            executeJob(shardingContext, new PageExecuteStrategy<T>() {
                @Override
                public boolean executePageConsiderFail(List<T> dataPage) {
                    try {
                        executePage(dataPage);
                    } catch (JobsExtendPageException e) {
                        if (!continueWhenSinglePageFail()) {
                            return false;
                        }
                        logger.error("Fail to deal page data", e);

                        plusFailSize(1);

                        // 处理队列后面未处理的数据
                        dataPage = subUntreatedList(dataPage, e.getPageFailIndex());
                        executePageConsiderFail(dataPage);
                    }
                    return true;
                }
            });
        }
        logger.debug("------PageJob-end: {}------", shardingContext.getJobName());

    }

    /**
     * 分页处理失败时，需要截取的未处理队列
     * 默认实现为不开启事务时的处理方式：截取失败数据后面的数据，前面成功的不做处理
     * 该方法可覆盖
     * @param dataPage
     * @param pageFailIndex 分页中失败的数据索引
     * @return
     */
    protected List<T> subUntreatedList(List<T> dataPage, int pageFailIndex) {

        return dataPage.subList(pageFailIndex + 1, dataPage.size());
    }
}
