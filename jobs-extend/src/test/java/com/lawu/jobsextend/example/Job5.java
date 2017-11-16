package com.lawu.jobsextend.example;

import java.util.List;

import com.lawu.jobsextend.AbstractPageResultJob;
import com.lawu.jobsextend.PageCircuitStrategy;

/**
 * @author Leach
 * @date 2017/11/16
 */
public class Job5 extends AbstractPageResultJob<String, Integer> {
    @Override
    public Integer executePage(List<String> dataPage) {
        return null;
    }

    @Override
    public void executeSummary(List<Integer> pageResults) {

    }

    @Override
    public List<String> queryPage(int offset, int pageSize) {

        // 获取当前参数
        Object param = getPageCircuitStrategy().currentParam();
        return null;
    }

    @Override
    public boolean isStatusData() {
        return false;
    }


    @Override
    public PageCircuitStrategy initPageCircuitStrategy() {
        return new ExamplePageCircuitStrategy();
    }

    class ExamplePageCircuitStrategy implements PageCircuitStrategy<String> {

        public ExamplePageCircuitStrategy() {
            // 初始化
        }

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public void next() {

        }

        @Override
        public String currentParam() {
            return null;
        }
    }
}
