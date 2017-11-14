package com.lawu.jobsextend.example;

import java.util.List;

import com.lawu.jobsextend.AbstractTxPageJob;

/**
 * @author Leach
 * @date 2017/11/14
 */
public class Job3 extends AbstractTxPageJob {
    @Override
    public void executeSingle(Object entity) {

    }

    @Override
    public List queryPage(int offset, int pageSize) {
        return null;
    }

    @Override
    public boolean isStatusData() {
        return false;
    }

    @Override
    public boolean continueWhenSinglePageFail() {
        return false;
    }
}
