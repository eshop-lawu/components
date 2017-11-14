package com.lawu.jobsextend;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dangdang.ddframe.job.api.ShardingContext;

/**
 * @author Leach
 * @date 2017/11/14
 */
public abstract class AbstractCommonPageJob<T> implements BasePageJob<T> {

    private static final int PAGE_MAX_CYCLE_TIME = 10000;

    private Logger logger = LoggerFactory.getLogger(AbstractCommonPageJob.class);

    /**
     * 处理失败的数据条数
     */
    private ThreadLocal<Integer> failSize =  new ThreadLocal<>();


    /**
     * 每页数量，0表示不分页
     */
    private Integer pageSize;

    /**
     * 是否属于状态相关数据
     * 此属性关系到currentPage的计算，如查询出来的数据带有状态，而且该状态在处理完后需要做变更
     * 则currentPage从上次失败的lastFailPage开始算起，而不是每一页均需要加1
     * @return
     */
    abstract public boolean isStatusData();

    /**
     * 当分页处理失败时是否继续处理下一分页
     * @return
     */
    abstract public boolean continueWhenSinglePageFail();

    public boolean executeJob(ShardingContext shardingContext, PageExecuteStrategy pageExecuteStrategy) {

        boolean isAllSuccess = true;

        if (pageSize == null) {
            String jobParameter = shardingContext.getJobParameter();
            if (jobParameter != null) {
                pageSize = Integer.parseInt(jobParameter);
            } else {
                pageSize = 0;
            }
        }

        failSize.set(0);

        // 当前页面，仅对非状态数据有效
        int currentPage = -1;
        for (int i = 0; i < PAGE_MAX_CYCLE_TIME; i++) {

            // 数据查询偏移量
            int offset;
            if (!isStatusData()) {
                // 非状态数据，逐页查询
                currentPage++;
                offset = currentPage * pageSize;
            } else {
                // 状态数据，排除失败记录数后，永远查询第一页数据
                offset = failSize.get();
            }
            List<T> dataPage = queryPage(offset, pageSize);
            if (pageSize == 0 || dataPage == null || dataPage.isEmpty()) {
                logger.debug("------PageJob-break: {}------", shardingContext.getJobName());
                break;
            }
            logger.debug("------PageJob-execute: {}, currentPage: {}, currentSize: {}------", shardingContext.getJobName(), currentPage, dataPage.size());

            if (!pageExecuteStrategy.executePageConsiderFail(dataPage)) {
                isAllSuccess = false;
                break;
            }
        }

        return isAllSuccess;
    }

    public void plusFailSize(int count) {
        failSize.set(failSize.get() + count);
    }

    public Integer getPageSize() {
        return pageSize;
    }
}
