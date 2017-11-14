package com.lawu.jobsextend.example;

import java.util.List;

import com.lawu.jobsextend.AbstractWholePageJob;
import com.lawu.jobsextend.JobsExtendPageException;

/**
 * @author Leach
 * @date 2017/11/14
 */
public class Job1 extends AbstractWholePageJob<String> {
    @Override
    public void executePage(List<String> dataPage) throws JobsExtendPageException {

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
