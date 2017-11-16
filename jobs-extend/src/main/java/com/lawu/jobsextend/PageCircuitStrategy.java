package com.lawu.jobsextend;

/**
 * 任务循环执行策略接口
 * @author Leach
 * @date 2017/11/16
 */
public interface PageCircuitStrategy<P> {

    /**
     * 实现类可以自定义属性
     * 通过构造方法初始化
     */

    /**
     * 是否有下个值
     * 每次任务主体执行前会调用该方法进行判断
     *
     * 只需实现，无需手动调用
     * @return
     */
    boolean hasNext();

    /**
     * 取到下个执行所需参数
     * 每次调用hasNex()方法，执行任务主体前调用该方法，
     *
     * 只需实现，无需手动调用
     */
    void next();

    /**
     * 获取当前循环的参数
     * 用于分页处理中手动调用获取所需参数
     *
     * @return
     */
    P currentParam();
}
