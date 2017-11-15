package com.lawu.jobsextend;

/**
 * @author Leach
 * @date 2017/11/13
 */
public class JobsExtendPageException extends Exception {

    /**
     * 分页中处理失败的具体数据索引号
     */
    private int pageFailIndex;

    protected JobsExtendPageException(Throwable cause, int pageFailIndex) {
        super(cause);
        this.pageFailIndex = pageFailIndex;
    }

    public int getPageFailIndex() {
        return pageFailIndex;
    }
}
