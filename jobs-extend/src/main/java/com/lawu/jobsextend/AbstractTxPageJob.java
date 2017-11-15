package com.lawu.jobsextend;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

/**
 * 用于单独模块中包含事务的定时任务，可以细粒化对每条数据自定义处理
 * @author Leach
 * @date 2017/11/6
 */
public abstract class AbstractTxPageJob<T> extends AbstractPageJob<T> {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void executePage(List<T> dataPage) throws JobsExtendPageException {
        super.executePage(dataPage);
    }

    @Override
    public List<T> subUntreatedList(List<T> dataPage, int pageFailIndex) {
        dataPage.remove(pageFailIndex);
        return dataPage;
    }
}
