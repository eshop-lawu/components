package com.lawu.jobsextend.example;

import java.util.List;

import com.lawu.jobsextend.AbstractPageJob;

/**
 * @author Leach
 * @date 2017/11/14
 */
public class Job2 extends AbstractPageJob<String> {
    @Override
    public void executeSingle(String entity) {

    }

    @Override
    public List<String> queryPage(int offset, int pageSize) {
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
