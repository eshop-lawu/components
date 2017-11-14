package com.lawu.jobsextend;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dangdang.ddframe.job.api.ShardingContext;

/**
 * 用于单独模块、跨模块的定时任务，可以每页数据进行自定义处理
 * @author Leach
 * @date 2017/11/14
 */
public abstract class AbstractWholePageJob<T> extends AbstractCommonPageJob<T> implements PageJob<T> {

    private Logger logger = LoggerFactory.getLogger(AbstractWholePageJob.class);

    @Override
    public void execute(ShardingContext shardingContext) {

        logger.debug("------PageJob-start: {}, jobParameter: {}------", shardingContext.getJobName(), shardingContext.getJobParameter());

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

                    // 移除坏数据后，重新处理该队列
                    dataPage.remove(e.getPageFailIndex());
                    executePageConsiderFail(dataPage);
                } catch (Exception e) {
                    if (!continueWhenSinglePageFail()) {
                        return false;
                    }
                    // 其他异常，如跨模块整页处理，不好取得失败的具体数据索引号，则整页标记为失败
                    plusFailSize(getPageSize());
                }
                return true;
            }
        });
        logger.debug("------PageJob-end: {}------", shardingContext.getJobName());

    }

}