package com.lawu.jobsextend;

/**
 * @author Leach
 * @date 2017/11/16
 */
public class DefaultPageCircuitStrategy implements PageCircuitStrategy {

    private int circuitTimes;

    private int currentIndex;

    DefaultPageCircuitStrategy() {
        circuitTimes = 1;
        currentIndex = 0;
    }

    @Override
    public boolean hasNext() {
        return currentIndex < circuitTimes;
    }

    @Override
    public void next() {
        currentIndex++;
    }

    @Override
    public Object currentParam() {
        return null;
    }
}
