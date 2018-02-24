package com.lawu.concurrentqueue.bizctrl;

/**
 * @author Leach
 * @date 2017/11/30
 */
public class BusinessExecuteException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    private int ret;
    private String msg;

    public BusinessExecuteException(int ret, String msg) {
        this.ret = ret;
        this.msg = msg;
    }

    public BusinessExecuteException(Throwable cause, int ret, String msg) {
        super(cause);
        this.ret = ret;
        this.msg = msg;
    }

    public BusinessExecuteException(Throwable cause) {
        super(cause);
    }
    
    public BusinessExecuteException() {
        super();
    }

    public int getRet() {
        return ret;
    }

    public String getMsg() {
        return msg;
    }
}
