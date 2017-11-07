package com.lawu.jobsextend;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

/**
 * 分页事务定时任务
 * @author Leach
 * @date 2017/11/6
 */
public abstract class AbstractTxPageJob<T> extends AbstractPageJob<T> {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void executePage(List<T> dataPage) {
        super.executePage(dataPage);
    }
}
