package com.lawu.jobsextend;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dangdang.ddframe.job.api.ShardingContext;

/**
 *
 * 用于单独模块、跨模块中需要汇总分页处理结果的定时任务
 *
 * @author Leach
 * @date 2017/11/14
 */
public abstract class AbstractPageResultJob<T, R> extends AbstractCommonPageJob<T> implements PageResultJob<T, R> {

    private Logger logger = LoggerFactory.getLogger(AbstractPageResultJob.class);

    @Override
    public boolean continueWhenSinglePageFail() {
        return false;
    }

    @Override
    public void execute(ShardingContext shardingContext) {

        logger.debug("------PageJob-start: {}, jobParameter: {}------", shardingContext.getJobName(), shardingContext.getJobParameter());

        PageCircuitStrategy pageCircuitStrategy = createPageCircuitStrategy();

        while (pageCircuitStrategy.hasNext()) {

            pageCircuitStrategy.next();

            List<R> pageResults = new ArrayList<>();

            boolean isAllSuccess = executeJob(shardingContext, new PageExecuteStrategy<T>() {
                @Override
                public boolean executePageConsiderFail(List<T> dataPage) {
                    try {
                        R result = executePage(dataPage);
                        pageResults.add(result);
                    } catch (Exception e) {
                        logger.error("Fail to deal page data", e);
                        return false;
                    }
                    return true;
                }
            });

            if (isAllSuccess) {
                executeSummary(pageResults);
            }
        }
        logger.debug("------PageJob-end: {}------", shardingContext.getJobName());
    }

}
