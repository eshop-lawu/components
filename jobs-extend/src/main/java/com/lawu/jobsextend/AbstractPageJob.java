package com.lawu.jobsextend;

import java.util.List;

import com.dangdang.ddframe.job.api.ShardingContext;

/**
 * @author Leach
 * @date 2017/11/6
 */
public abstract class AbstractPageJob<T> implements PageJob<T> {

    /**
     * 每页数量，0表示不分页
     */
    private Integer pageSize;

    /**
     * 针对一条记录执行任务
     * @param entity
     */
    abstract public void executeSingle(T entity);

    @Override
    public void execute(ShardingContext shardingContext) {
        if (pageSize == null) {
            String jobParameter = shardingContext.getJobParameter();
            if (jobParameter != null) {
                pageSize = Integer.parseInt(jobParameter);
            } else {
                pageSize = 0;
            }
        }
        int currentPage = 0;
        while (true) {
            currentPage++;
            List<T> dataPage = queryPage(currentPage, pageSize);
            if (pageSize == 0 || dataPage == null || dataPage.isEmpty()) {
                break;
            }
            // 逐页处理，防止资源一次性占用过多
            executePage(dataPage);
        }
    }

    @Override
    public void executePage(List<T> dataPage) {
        for (T entity : dataPage) {
            executeSingle(entity);
        }
    }
}
