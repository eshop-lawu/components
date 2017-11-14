package com.lawu.jobsextend.example;

import java.util.List;

import com.lawu.jobsextend.AbstractPageResultJob;

/**
 * @author Leach
 * @date 2017/11/14
 */
public class Job4 extends AbstractPageResultJob<String, Integer> {
    @Override
    public Integer executePage(List<String> dataPage) {
        return null;
    }

    @Override
    public void executeSummary(List<Integer> pageResults) {

    }

    @Override
    public List<String> queryPage(int offset, int pageSize) {
        return null;
    }

    @Override
    public boolean isStatusData() {
        return false;
    }
}
